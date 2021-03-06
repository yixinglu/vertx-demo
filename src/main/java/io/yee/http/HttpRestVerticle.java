package io.yee.http;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;

public class HttpRestVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpRestVerticle.class);

  @Override
  public Completable rxStart() {
    return Single.zip(helloRouter(), sayRouter(),
      (helloRouter, sayRouter) -> {
        Router router = Router.router(vertx);
        router.mountSubRouter("/api", helloRouter);
        router.mountSubRouter("/api", sayRouter);
        router.errorHandler(400, rc -> {
          if (rc.failure() instanceof ValidationException) {
            LOGGER.error(rc.failure());
          }
        });
        return router;
      })
      .flatMap(router -> {
        HttpServerOptions options = new HttpServerOptions().setHost("localhost").setPort(8080);
        return vertx.createHttpServer(options).requestHandler(router).rxListen();
      })
      .flatMap(server -> {
        HttpServerOptions options = new HttpServerOptions().setHost("localhost").setPort(8090);
        Router router = Router.router(vertx);
        router.get("/api/hello").handler(rc -> {
          rc.response().end("hello");
        });
        router.get("/api/:num").handler(rc -> {
          String num = rc.request().getParam("num");
          int param = Integer.parseInt(num);
          rc.response().end("num: " + param);
        });
        return vertx.createHttpServer(options).requestHandler(router).rxListen();
      })
      .ignoreElement();
  }

  public Single<Router> helloRouter() {
    return OpenAPI3RouterFactory.rxCreate(vertx, "hello.yaml")
      .map(factory -> {
        factory.mountServicesFromExtensions().addGlobalHandler(this::AddToken);
        return factory.getRouter();
      });
  }

  public Single<Router> sayRouter() {
    return OpenAPI3RouterFactory.rxCreate(vertx, "say.yaml")
      .map(factory -> {
        factory.mountServicesFromExtensions();
        return factory.getRouter();
      });
  }

  private void AddToken(RoutingContext context) {
    context.request().headers().add("token", "test-info");
    context.next();
  }
}
