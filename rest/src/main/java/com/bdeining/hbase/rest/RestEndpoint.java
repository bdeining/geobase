package com.bdeining.hbase.rest;

import com.blade.Blade;

public class RestEndpoint {

  private static GetCapabilitesHandler getCapabilitesHandler = new GetCapabilitesHandler();
  private static GetRecordsHandler getRecordsHandler = new GetRecordsHandler();

  // service=CSW&version=2.0.2&


  public static void main(String[] args) {
    Blade.of()
        .get(
            "/",
            ctx -> {
              String service = ctx.query("service");
              String version = ctx.query("version");
              String request = ctx.query("request");

              switch (request) {
                case "GetCapabilities":
                  getCapabilitesHandler.handle(ctx);
                  break;
                case "GetRecords":
                  getRecordsHandler.handle(ctx);
                  break;
              }
            })
        .start();
  }
}
