package src.main.java.com.bdeining.hbase.rest;

import com.blade.Blade;

public class RestEndpoint {

  private static GetCapabilitesHandler getCapabilitesHandler = new GetCapabilitesHandler();

  public static void main(String[] args) {
    Blade.of()
        .get(
            "/",
            ctx -> {
              String service = ctx.query("service");
              String version = ctx.query("version");
              String request = ctx.query("request");

              switch (request) {
                case "get":
                  getCapabilitesHandler.handle(ctx);
                  break;
              }
            })
        .start();
  }
}
