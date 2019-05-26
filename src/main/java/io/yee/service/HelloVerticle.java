package io.yee.service;

import io.reactivex.Completable;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.impl.AsyncResultCompletable;
import io.vertx.serviceproxy.ServiceBinder;

public class HelloVerticle extends AbstractVerticle {

  @Override
  public Completable rxStart() {
    Future<Void> future = Future.future();
    HelloService.create(ar -> {
      if (ar.failed()) {
        future.fail(ar.cause());
      } else {
        new ServiceBinder(vertx.getDelegate())
          .setAddress(HelloService.SERVICE_NAME)
          .register(HelloService.class, ar.result());
        future.complete();
      }
    });

    return new AsyncResultCompletable(ar -> {
      future.setHandler(ar);
    });
  }
}
