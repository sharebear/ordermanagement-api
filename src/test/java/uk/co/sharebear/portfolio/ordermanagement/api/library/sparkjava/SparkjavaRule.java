package uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava;

import org.junit.rules.ExternalResource;

import java.util.Random;

import spark.Spark;

public class SparkjavaRule extends ExternalResource {
  private final int port;

  public SparkjavaRule() {
    this.port = new Random().ints(1, 1024, 65536).sum();
  }

  @Override
  protected void before() throws Throwable {
    super.before();
    Spark.port(port);
  }

  @Override
  protected void after() {
    super.after();

    // Note, the code behind this call is asynchronous so the server doesn't stop immediately
    Spark.stop();
  }

  public String url(String path) {
    final String slash = path.startsWith("/") ? "" : "/";
    return "http://localhost:" + port + slash + path;
  }
}
