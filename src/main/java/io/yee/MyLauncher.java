package io.yee;

import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;

public class MyLauncher extends Launcher {

  public static void main(String[] args) {
    new MyLauncher().dispatch(args);
  }

  @Override
  public void beforeStartingVertx(VertxOptions options) {
    super.beforeStartingVertx(options);
  }
}
