package io.yee.service.impl;

import static java.util.Objects.isNull;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.reactivex.SingleHelper;
import io.yee.service.HelloService;

public class HelloServiceImpl implements HelloService {

  public HelloServiceImpl(Handler<AsyncResult<HelloService>> resultHandler) {
    Single.just(this).subscribe(SingleHelper.toObserver(resultHandler));
  }

  @Override
  public void hello(String name, OperationRequest context,
    Handler<AsyncResult<OperationResponse>> resultHandler) {
    String token = context.getHeaders().get("token");
    if (isNull(token) || !"test-info".equalsIgnoreCase(token)) {
      resultHandler.handle(Future.failedFuture("token is null"));
      return;
    }

    Single.just("hello")
      .map(msg -> {
        if (isNull(name)) {
          return msg;
        }
        return msg + " " + name;
      })
      .map(msg -> new JsonObject().put("msg", msg))
      .map(OperationResponse::completedWithJson)
      .subscribe(SingleHelper.toObserver(resultHandler));
  }

  @Override
  public void add(JsonObject body, OperationRequest context,
    Handler<AsyncResult<OperationResponse>> handler) {
    String name = body.getString("name");
    Single.just(name).map(String::length)
      .map(length -> new JsonObject().put("success", true).put("length", length))
      .map(OperationResponse::completedWithJson)
      .subscribe(SingleHelper.toObserver(handler));
  }
}
