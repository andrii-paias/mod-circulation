package org.folio.circulation.resources;

import java.util.concurrent.CompletableFuture;

import org.folio.circulation.domain.ConfigurationRepository;
import org.folio.circulation.domain.anonymization.LoanAnonymization;
import org.folio.circulation.domain.anonymization.LoanAnonymizationRecords;
import org.folio.circulation.domain.anonymization.LoanAnonymizationService;
import org.folio.circulation.domain.anonymization.config.TenantLoanAnonymizationSettings;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.Result;
import org.folio.circulation.support.RouteRegistration;
import org.folio.circulation.support.http.server.WebContext;

import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Perform automatic loan anonymization based on tenant settings for loan history.
 * This process is intended to run in short intervals.
 *
 */
public class ScheduledAnonymizationResource extends Resource {

  public ScheduledAnonymizationResource(HttpClient client) {
    super(client);
  }

  @Override
  public void register(Router router) {
    RouteRegistration routeRegistration = new RouteRegistration(
      "/circulation/scheduled-anonymize-processing", router);
    routeRegistration.create(this::scheduledAnonymizeLoans);
  }

  private void scheduledAnonymizeLoans(RoutingContext routingContext) {
    final Clients clients = Clients.create(new WebContext(routingContext), client);
    ConfigurationRepository configurationRepository = new ConfigurationRepository(clients);

    CompletableFuture<Result<TenantLoanAnonymizationSettings>> resultCompletableFuture = configurationRepository.lookupConfigurationPeriod();
    LoanAnonymizationService loanAnonymizationService = LoanAnonymization.newLoanAnonymizationService(clients);

    resultCompletableFuture.thenApply(r -> r.map(LoanAnonymizationRecords::new))
      .thenCompose(r -> r.after(loanAnonymizationService::anonymizeLoans)
      .thenAccept(a -> routingContext.response().setStatusCode(204).end()));
  }
}
