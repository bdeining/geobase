package src.main.java.com.bdeining.hbase.rest;

import com.blade.Blade;
import com.blade.mvc.RouteContext;
import java.util.Arrays;
import java.util.List;

public class RestEndpoint {

  private static final String CSW_SERVICE = "CSW";

  private static final List<String> SUPPORTED_VERSION = Arrays.asList("2.0.2");

  private static GetCapabilitesHandler getCapabilitesHandler = new GetCapabilitesHandler();
<<<<<<< Updated upstream

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
=======

  private static GetRecordsHandler getRecordsHandler = new GetRecordsHandler();

  private static void validateService(String service) throws BadRequestException {
    if (!CSW_SERVICE.equals(service)) {
      throw new BadRequestException();
    }
  }

  private static void validateVersion(String version) throws BadRequestException {
    if (!SUPPORTED_VERSION.contains(version)) {
      throw new BadRequestException();
    }
  }

  private static void handleRequest(RouteContext ctx) {
    String service = ctx.query("service");
    String version = ctx.query("version");
    String request = ctx.query("request");

    try {
      validateService(service);
      validateVersion(version);
    } catch (BadRequestException e) {
      ctx.status(500);
      return;
    }

    switch (request) {
      case "GetCapabilities":
        getCapabilitesHandler.handle(ctx);
        break;
      case "GetRecords":
        getRecordsHandler.handle(ctx);
        break;
    }
  }

  public static void main(String[] args) {
    Blade.of().get("/", RestEndpoint::handleRequest).start();
>>>>>>> Stashed changes
  }
}
