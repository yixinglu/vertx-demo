package io.yee;

import io.reactivex.Completable;
import io.vertx.core.DeploymentOptions;
import io.vertx.reactivex.core.AbstractVerticle;
import io.yee.http.HttpRestVerticle;
import io.yee.service.HelloVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public Completable rxStart() {
    DeploymentOptions options = new DeploymentOptions().setConfig(config());
    return vertx
      .rxDeployVerticle(HelloVerticle.class.getName(), options)
      .ignoreElement()
      .andThen(vertx.rxDeployVerticle(HttpRestVerticle.class.getName(), options))
      .ignoreElement();
  }
}
