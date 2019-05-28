package io.yee.service.impl;

import static java.util.Objects.nonNull;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.reactivex.SingleHelper;
import io.yee.service.SayService;

public class SayServiceImpl implements SayService {

  public SayServiceImpl() {
  }

  @Override
  public void say(String name, OperationRequest context,
    Handler<AsyncResult<OperationResponse>> handler) {
    Single.just(nonNull(name) ? name : " anonymous!")
      .map(msg -> "say " + msg)
      .map(msg -> new JsonObject().put("success", true).put("msg", msg))
      .map(OperationResponse::completedWithJson)
      .subscribe(SingleHelper.toObserver(handler));
  }
}
