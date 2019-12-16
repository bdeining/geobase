package com.bdeining.hbase.rest;

import com.bdeining.geobase.parser.Record;
import com.bdeining.geobase.parser.SaxParser;
import com.blade.mvc.RouteContext;
import com.blade.mvc.handler.RouteHandler;
import com.google.gson.Gson;
import java.util.HashMap;

public class GetRecordsHandler implements RouteHandler {

  private static Gson GSON = new Gson();

  @Override
  public void handle(RouteContext routeContext) {
    Record record = new Record();
    SaxParser saxParser = new SaxParser(record, new HashMap<>());
  }
}
