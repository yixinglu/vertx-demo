package io.yee.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.api.generator.WebApiServiceGen;

@WebApiServiceGen
public interface SayService {

  String ADDRESS = "service.say";

  void say(String name, OperationRequest context, Handler<AsyncResult<OperationResponse>> handler);
}
