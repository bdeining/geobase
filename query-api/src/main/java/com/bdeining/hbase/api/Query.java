package com.bdeining.hbase.api;

import net.opengis.csw.v_3_0.RecordType;

public interface Query {

    RecordType getRecordById(String id);

}
