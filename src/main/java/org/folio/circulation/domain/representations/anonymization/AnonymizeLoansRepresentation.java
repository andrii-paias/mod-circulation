
package org.folio.circulation.domain.representations.anonymization;

import static org.folio.circulation.support.Result.failed;

import io.vertx.core.json.Json;
import org.folio.circulation.domain.anonymization.LoanAnonymizationRecords;
import org.folio.circulation.support.OkJsonResponseResult;
import org.folio.circulation.support.ResponseWritableResult;
import org.folio.circulation.support.Result;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class AnonymizeLoansRepresentation {

  private AnonymizeLoansRepresentation() {
  }

  public static ResponseWritableResult<JsonObject> from(Result<LoanAnonymizationRecords> records) {
    return records.map(AnonymizeLoansRepresentation::mapToJson)
      .orElse(failed(records.cause()));
  }

  private static ResponseWritableResult<JsonObject> mapToJson(LoanAnonymizationRecords records) {
    AnonymizeStorageLoansResponse response = new AnonymizeStorageLoansResponse();
    response.withAnonymizedLoans(records.getAnonymizedLoans());
    response.withNotAnonymizedLoans(records.getNotAnonymizedLoans());
    return new OkJsonResponseResult(JsonObject.mapFrom(response));


//    JsonObject responseBody = new JsonObject();
//    responseBody.put("anonymizedLoans", new JsonArray(records.getAnonymizedLoans()));
//    responseBody.put("notAnonymizedLoans", new JsonArray(records.getNotAnonymizedLoans()));
//    return new OkJsonResponseResult(responseBody);
  }

}
