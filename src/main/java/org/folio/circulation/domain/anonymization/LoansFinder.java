package org.folio.circulation.domain.anonymization;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.StringUtils;
import org.folio.circulation.domain.Loan;
import org.folio.circulation.domain.LoanRepository;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.CqlQuery;
import org.folio.circulation.support.Result;

public class LoansFinder {

  private final LoanRepository loanRepository;

  public LoansFinder(Clients clients) {
    loanRepository = new LoanRepository(clients);
  }


  public CompletableFuture<Result<MultipleRecords<Loan>>> findLoansToAnonymize(LoanAnonymizationRecords records) {

    if (StringUtils.isNotEmpty(records.getUserId())) {
      return loanRepository.findClosedLoansForUser(records.getUserId());
    }




    loanRepository.findLoansTESTNOTARRAY(getQuery(records));






    return closedLoansForUser;
  }

  private Result<CqlQuery> getQuery(LoanAnonymizationRecords records) {
    if (StringUtils.isNotEmpty(records.getUserId())) {
      return CqlQuery.exactMatch("userId", records.getUserId()).map(a-> a.toString());
    }

    loanRepository.findLoans(CqlQuery.exactMatch("userId", records.getUserId()));






    return null;
  }
























}
