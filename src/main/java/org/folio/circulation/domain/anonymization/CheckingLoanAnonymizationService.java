package org.folio.circulation.domain.anonymization;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import com.google.inject.internal.util.Lists;
import com.google.inject.internal.util.Maps;
import com.google.inject.internal.util.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Collectors;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.folio.circulation.domain.AccountRepository;
import org.folio.circulation.domain.Loan;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.domain.anonymization.checks.AnonymizationChecker;
import org.folio.circulation.domain.anonymization.checks.FeesAndFinesClosedAnonymizationChecker;
import org.folio.circulation.domain.representations.anonymization.Error;
import org.folio.circulation.domain.representations.anonymization.NotAnonymizedLoans;
import org.folio.circulation.domain.representations.anonymization.Parameter;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.Result;

/**
 * Validates loan eligibility for anonymization. By default a loan can only be anonymized if it's closed and there are no open fees
 * and fines associated with it.
 */
public class CheckingLoanAnonymizationService extends DefaultLoanAnonymizationService {

  private static final List<AnonymizationChecker> checkers = Lists.newArrayList
      (new FeesAndFinesClosedAnonymizationChecker());
  private final AccountRepository accountRepository;
  private static final String canBeAnonymizedKey = "ok";

  CheckingLoanAnonymizationService(Clients clients) {
    super(clients);
    accountRepository = new AccountRepository(clients);
  }

  @Override
  protected CompletableFuture<Result<Collection<Loan>>> populateLoanInformation(Result<MultipleRecords<Loan>> records) {
    return records.after(accountRepository::findOpenAccountsForLoans)
      .thenCompose(super::populateLoanInformation);
  }

  @Override
  protected CompletableFuture<Result<LoanAnonymizationRecords>> segregateLoans(Result<LoanAnonymizationRecords> records) {

    return CompletableFuture.completedFuture(records.map(r -> {
      HashSetValuedHashMap<String, String> multiMap = new HashSetValuedHashMap();

      for (Loan loan : r.getInputLoans()) {
        for (AnonymizationChecker checker : checkers) {
          if (!checker.canBeAnonymized(loan)) {
            multiMap.put(checker.getReason(), loan.getId());
          }else{
            multiMap.put(canBeAnonymizedKey, loan.getId());
          }
        }
      }
      Collection<String> loansToAnonymize = multiMap.remove(canBeAnonymizedKey);
      NotAnonymizedLoans nal = new NotAnonymizedLoans();
      List<Error> errors = multiMap.keySet().stream().map(k ->
          new Error().withMessage(k)
              .withParameters(
                  Collections.singletonList(
                      new Parameter().withKey("loanIds")
                          .withValue(StringUtils.join(multiMap.get(k)))))
      ).collect(Collectors.toList());

      return r.withAnonymizedLoans(loansToAnonymize)
        .withNotAnonymizedLoans(nal.withErrors(errors)
        .withTotalRecords(multiMap.values().size()));
    }));

  }


}
