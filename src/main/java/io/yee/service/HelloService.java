package io.yee.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.api.generator.WebApiServiceGen;
import io.yee.service.impl.HelloServiceImpl;

@WebApiServiceGen
public interface HelloService {

  String SERVICE_NAME = "service.hello";

  static HelloService create(Handler<AsyncResult<HelloService>> resultHandler) {
    return new HelloServiceImpl(resultHandler);
  }

  void hello(String name, OperationRequest context,
    Handler<AsyncResult<OperationResponse>> resultHandler);

  void add(JsonObject body, OperationRequest context,
    Handler<AsyncResult<OperationResponse>> handler);
}
