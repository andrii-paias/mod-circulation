package org.folio.circulation.domain.anonymization.strategy;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.folio.circulation.domain.AnonymizeStorageLoansRepository;
import org.folio.circulation.domain.Loan;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.domain.anonymization.LoanAnonymizationRecords;
import org.folio.circulation.domain.anonymization.LoansFinder;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DefaultLoanAnonymizationStrategy implements LoanAnonymizationStrategy {

  protected final Logger log = LoggerFactory.getLogger(MethodHandles.lookup()
      .lookupClass());


  private final LoansFinder loansFinder;
  private LoanAnonymizationStrategy loanAnonymizationStrategy;

  private final AnonymizeStorageLoansRepository anonymizeStorageLoansRepository;

  DefaultLoanAnonymizationStrategy(Clients clients) {

    anonymizeStorageLoansRepository = new AnonymizeStorageLoansRepository(clients);
    loansFinder = new LoansFinder(clients);
  }


  @Override
  public CompletableFuture<Result<LoanAnonymizationRecords>> anonymizeLoans(LoanAnonymizationRecords records) {

    log.info("Anonymizing loans for userId: {} in tenant {}", records.getUserId(), records.getTenant());

    if (Objects.nonNull(records.getConfig())) {
      log.info("****EXPERIMENTAL FEATURE*****");
//      return CompletableFuture.completedFuture(Result.failed(new RecordNotFoundFailure("hello", "world")));
    }


    return findLoansToAnonymize(records)
        .thenCompose(this::populateLoanInformation)
        .thenApply(r -> r.map(records::withInputLoans))
        .thenCompose(this::segregateLoansByAnonymizationEligibility)
        .thenCompose(r -> r.after(anonymizeStorageLoansRepository::postAnonymizeStorageLoans));
  }

  protected CompletableFuture<Result<Collection<Loan>>> populateLoanInformation(Result<MultipleRecords<Loan>> records) {
    return completedFuture(records.map(MultipleRecords::getRecords));
  }

  protected CompletableFuture<Result<LoanAnonymizationRecords>> segregateLoansByAnonymizationEligibility(Result<LoanAnonymizationRecords> records) {
    return completedFuture(records);
  }


  protected CompletableFuture<Result<MultipleRecords<Loan>>> findLoansToAnonymize(LoanAnonymizationRecords records) {
    return loansFinder.findLoansToAnonymize(records);
  }
}
