package org.folio.circulation.domain.anonymization.strategy;

import java.util.concurrent.CompletableFuture;
import org.folio.circulation.domain.anonymization.LoanAnonymizationRecords;
import org.folio.circulation.support.Result;

public interface LoanAnonymizationStrategy {

  CompletableFuture<Result<LoanAnonymizationRecords>> anonymizeLoans(LoanAnonymizationRecords records);

}
