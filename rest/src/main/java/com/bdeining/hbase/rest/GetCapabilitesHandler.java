package com.bdeining.hbase.rest;

import com.blade.mvc.RouteContext;
import com.blade.mvc.handler.RouteHandler;
import com.google.gson.Gson;

public class GetCapabilitesHandler implements RouteHandler {

  // curl http://localhost:8000/?service=CSW&version=2.0.2&request=GetCapabilities
  private static Gson GSON = new Gson();

  @Override
  public void handle(RouteContext routeContext) {}
}
