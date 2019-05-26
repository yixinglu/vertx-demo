package io.yee.http;

import io.reactivex.Completable;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;

public class HttpRestVerticle extends AbstractVerticle {

  @Override
  public Completable rxStart() {
    return OpenAPI3RouterFactory.rxCreate(vertx, "src/main/config/openapi.yaml")
      .map(factory -> {
        factory.mountServicesFromExtensions().addGlobalHandler(this::AddToken);
        return Router.router(vertx).mountSubRouter("/api", factory.getRouter());
      })
      .flatMap(router -> {
        HttpServerOptions options = new HttpServerOptions().setHost("localhost").setPort(8080);
        return vertx.createHttpServer(options).requestHandler(router).rxListen();
      })
      .ignoreElement();
  }

  private void AddToken(RoutingContext context) {
    context.request().headers().add("token", "test-info");
    context.next();
  }
}
