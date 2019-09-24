package org.folio.circulation.resources;

import static org.folio.circulation.support.Result.succeeded;

import org.folio.circulation.domain.Location;
import org.folio.circulation.rules.Drools;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonArray;
import org.folio.circulation.support.Result;

import java.util.concurrent.CompletableFuture;

/**
 * The circulation rules engine calculates the request policy based on
 * item type, request type, patron type and shelving location.
 */
public class RequestCirculationRulesEngineResource extends AbstractCirculationRulesEngineResource {

  public RequestCirculationRulesEngineResource(String applyPath, String applyAllPath, HttpClient client) {
    super(applyPath, applyAllPath, client);
  }

  @Override
  protected CompletableFuture<Result<String>> getPolicyId(MultiMap params, Drools drools, Location location) {
    return CompletableFuture.completedFuture(succeeded(drools.requestPolicy(params, location)));
  }

  @Override
  protected String getPolicyIdKey() {
    return "requestPolicyId";
  }

  @Override
  protected CompletableFuture<Result<JsonArray>> getPolicies(MultiMap params, Drools drools, Location location) {
    return CompletableFuture.completedFuture(succeeded(drools.requestPolicies(params, location)));
  }
}
