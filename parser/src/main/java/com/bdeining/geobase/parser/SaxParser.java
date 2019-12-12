package com.bdeining.geobase.parser;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;

public class SaxParser extends DefaultHandler {

    private static final Map<String, String> MAPPINGS =
            new ImmutableMap.Builder<String, String>()
                    .put("/csw:transaction/csw:insert/csw:record/dc:title", "title")
                    .put("/csw:transaction/csw:insert/csw:record/dc:created", "created")
                    .put("/csw:transaction/csw:insert/csw:record/ows:boundingbox/ows:lowercorner", "bblc")
                    .put("/csw:transaction/csw:insert/csw:record/ows:boundingbox/ows:uppercorner", "bbuc")
                    .build();

    private Record record;

    /** Used to track our current location in the input XML */
    protected StringBuilder currentPath;

    /** Used to store the value contained in the current XML element */
    private StringBuilder valueBuffer;

    private Map<String, String> additionalParsing;

    public SaxParser(Record record, Map<String, String> additionalParsing) {
        setCurrentPath(new StringBuilder());
        setValueBuffer(new StringBuilder());
        this.record = record;
        this.additionalParsing = additionalParsing;
    }

    /**
     * Updates the current path upon starting a new element
     *
     * @param uri The namespace URI - if one exists - of the element being started
     * @param localName The element name without it's namespace prefix (if parser is namespace aware)
     * @param qName The qualified name of the element being started
     * @param attributes Any attributes belonging to the element
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentPath.append("/").append(qName.toLowerCase());

        if (attributes != null && attributes.getLength() > 0) {
            String currentPathStr = currentPath.toString();
            for (int i = 0; i < attributes.getLength(); i++) {
                String attributeName = attributes.getQName(i);
                String attributeValue = attributes.getValue(i);
                if (StringUtils.isNotBlank(attributeName) && attributeValue != null) {
                    handleValue(currentPathStr + "#" + attributeName, attributeValue);
                }
            }
        }
    }

    /**
     * Stores the parsed characters in the valueBuffer for consumption when the element ends
     *
     * @param ch An array containing the parsed characters
     * @param start The start index of the parsed characters
     * @param length The number of characters parsed
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        valueBuffer.append(ch, start, length);
    }

    /**
     * Updates the current path upon ending an element, adds the element's value to the {@link }
     *
     * @param uri The namespace URI - if one exists - of the element being ended
     * @param localName The element name without it's namespace prefix (if parser is namespace aware)
     * @param qName The qualified name of the element being ended
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        String value = valueBuffer.toString().trim();
        handleValue(currentPath.toString(), value);
        valueBuffer.setLength(0);
        currentPath.setLength(currentPath.lastIndexOf("/"));
    }

    /**
     * Adds a given input value to the handler's {@link Record} based on the path's attribute
     * mappings. If an extension of this class requires alternative parsing behavior, this method
     * should be overridden, using {@code super} to access base functionality.
     *
     * @param path Path to current value in input XML
     * @param value Value from input XML
     */
    public void handleValue(String path, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }

        String attributeName = MAPPINGS.get(path.toLowerCase());
        if (attributeName != null) {

            switch (attributeName) {
                case "title":
                    record.setTitle(value);
                    break;
                case "created":
                    record.setCreated(0L);
                    break;
                case "bbuc":
                case "bblc":
                    additionalParsing.put(attributeName, value);
                    break;
            }
        }
    }

    /**
     * Returns this handler's associated {@link Record}
     *
     * @return The metacard associated with this handler
     */
    protected Record getRecord() {
        return this.record;
    }

    /**
     * Returns the path to the current location in the XML document being parsed
     *
     * @return The current XML path
     */
    protected StringBuilder getCurrentPath() {
        return currentPath;
    }

    /**
     * Sets the path to the current location in the XML document (should be used with care)
     *
     * @param currentPath The path to set
     */
    protected void setCurrentPath(StringBuilder currentPath) {
        this.currentPath = currentPath;
    }

    /**
     * Returns the buffer being used to store the XML value parsed from the document
     *
     * @return The buffer this handler uses for value storage
     */
    protected StringBuilder getValueBuffer() {
        return valueBuffer;
    }

    /**
     * Sets the handler's valueBuffer to use the provided {@link StringBuilder}
     *
     * @param valueBuffer The buffer to have the handler use
     */
    protected void setValueBuffer(StringBuilder valueBuffer) {
        this.valueBuffer = valueBuffer;
    }
}
