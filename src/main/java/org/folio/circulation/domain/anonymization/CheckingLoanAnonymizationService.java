package org.folio.circulation.domain.anonymization;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toSet;

import com.google.inject.internal.util.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.folio.circulation.domain.AccountRepository;
import org.folio.circulation.domain.Loan;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.domain.anonymization.checks.AnonymizationChecker;
import org.folio.circulation.domain.anonymization.checks.FeesAndFinesClosedAnonymizationChecker;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.Result;

/**
 * Validates loan eligibility for anonymization. By default a loan
 * can only be anonymized if it's closed and there are no open fees
 * and fines associated with it.
 */
public class CheckingLoanAnonymizationService extends DefaultLoanAnonymizationService {

  private static final List<AnonymizationChecker> checkers =
      Lists.newArrayList(new FeesAndFinesClosedAnonymizationChecker());

  private final AccountRepository accountRepository;


  private final LoansFinder loansFinder;

  CheckingLoanAnonymizationService(Clients clients) {
    super(clients);
    accountRepository = new AccountRepository(clients);
    loansFinder = new LoansFinder(clients);
  }

  @Override
  protected CompletableFuture<Result<MultipleRecords<Loan>>> findLoansToAnonymize(LoanAnonymizationRecords records) {
    return loansFinder.findLoansToAnonymize(records);
  }



  @Override
  protected CompletableFuture<Result<Collection<Loan>>> populateLoanInformation(Result<MultipleRecords<Loan>> records) {
    return records.after(accountRepository::findOpenAccountsForLoans)
      .thenCompose(super::populateLoanInformation);
  }

  @Override
  protected CompletableFuture<Result<LoanAnonymizationRecords>> segregateLoansByAnonymizationEligibility(
      Result<LoanAnonymizationRecords> records) {

    return completedFuture(records.map(r -> {
      Map<Boolean, Set<String>> sortedMap = r.getInputLoans().stream()
        .collect(partitioningBy(this::applyChecks, mapping(Loan::getId, toSet())));
      return r.withAnonymizedLoans(sortedMap.get(TRUE))
        .withNotAnonymizedLoans(sortedMap.get(FALSE));
    }));

  }

  private boolean applyChecks(Loan loan) {
    return checkers.stream().allMatch(c -> c.canBeAnonymized(loan));
  }

}
