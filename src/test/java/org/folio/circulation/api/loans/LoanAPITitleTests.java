package org.folio.circulation.api.loans;

import io.vertx.core.json.JsonObject;
import org.folio.circulation.api.support.APITests;
import org.folio.circulation.api.support.builders.HoldingBuilder;
import org.folio.circulation.api.support.builders.LoanBuilder;
import org.folio.circulation.api.support.fixtures.InstanceExamples;
import org.folio.circulation.api.support.fixtures.ItemExamples;
import org.folio.circulation.support.http.client.IndividualResource;
import org.folio.circulation.support.http.client.Response;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.folio.circulation.api.support.JsonCollectionAssistant.getRecordById;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class LoanAPITitleTests extends APITests {

  @Test
  public void titleIsFromInstanceWhenHoldingAndInstanceAreAvailable()
    throws InterruptedException,
    ExecutionException,
    TimeoutException,
    MalformedURLException {

    UUID instanceId = instancesClient.create(
      InstanceExamples.basedUponSmallAngryPlanet()).getId();

    UUID holdingId = holdingsClient.create(
      new HoldingBuilder()
        .forInstance(instanceId)
        .create())
      .getId();

    UUID itemId = itemsClient.create(
      ItemExamples.basedUponSmallAngryPlanet()
        .forHolding(holdingId))
      .getId();

    UUID loanId = UUID.randomUUID();

    IndividualResource response = loansClient.create(new LoanBuilder()
      .withId(loanId)
      .withItemId(itemId));

    JsonObject createdLoan = response.getJson();

    assertThat("has item title",
      createdLoan.getJsonObject("item").containsKey("title"), is(true));

    assertThat("title is taken from instance",
      createdLoan.getJsonObject("item").getString("title"),
      is("The Long Way to a Small, Angry Planet"));

    Response fetchedLoanResponse = loansClient.getById(loanId);

    assertThat(fetchedLoanResponse.getStatusCode(), is(200));

    JsonObject fetchedLoan = fetchedLoanResponse.getJson();

    assertThat("has item title",
      fetchedLoan.getJsonObject("item").containsKey("title"), is(true));

    assertThat("title is taken from instance",
      fetchedLoan.getJsonObject("item").getString("title"),
      is("The Long Way to a Small, Angry Planet"));
  }

  @Test
  public void noTitleWhenInstanceNotFound()
    throws InterruptedException,
    ExecutionException,
    TimeoutException,
    MalformedURLException {

    UUID instanceId = instancesClient.create(
      InstanceExamples.basedUponSmallAngryPlanet()).getId();

    UUID holdingId = holdingsClient.create(
      new HoldingBuilder()
        .forInstance(instanceId)
        .create())
      .getId();

    UUID itemId = itemsClient.create(
      ItemExamples.basedUponSmallAngryPlanet()
        .forHolding(holdingId))
      .getId();

    instancesClient.delete(instanceId);

    UUID loanId = UUID.randomUUID();

    IndividualResource response = loansClient.create(new LoanBuilder()
      .withId(loanId)
      .withItemId(itemId));

    JsonObject createdLoan = response.getJson();

    assertThat("has no title",
      createdLoan.getJsonObject("item").containsKey("title"), is(false));

    Response fetchedLoanResponse = loansClient.getById(loanId);

    assertThat(fetchedLoanResponse.getStatusCode(), is(200));

    JsonObject fetchedLoan = fetchedLoanResponse.getJson();

    assertThat("has no title",
      fetchedLoan.getJsonObject("item").containsKey("title"), is(false));
  }

  @Test
  public void titlesComeFromMultipleInstancesForMultipleLoans()
    throws InterruptedException,
    MalformedURLException,
    TimeoutException,
    ExecutionException {

    UUID firstInstanceId = instancesClient.create(
      InstanceExamples.basedUponSmallAngryPlanet()).getId();

    UUID firstHoldingId = holdingsClient.create(
      new HoldingBuilder()
        .forInstance(firstInstanceId)
        .create())
      .getId();

    UUID firstItemId = itemsClient.create(
      ItemExamples.basedUponSmallAngryPlanet()
        .forHolding(firstHoldingId))
      .getId();

    UUID secondInstanceId = instancesClient.create(
      InstanceExamples.basedUponTemeraire()).getId();

    UUID secondHoldingId = holdingsClient.create(
      new HoldingBuilder()
        .forInstance(secondInstanceId)
        .create())
      .getId();

    UUID secondItemId = itemsClient.create(
      ItemExamples.basedUponTemeraire()
        .forHolding(secondHoldingId))
      .getId();

    UUID firstLoanId = loansClient.create(new LoanBuilder()
      .withItemId(firstItemId)).getId();

    UUID secondLoanId = loansClient.create(new LoanBuilder()
      .withItemId(secondItemId)).getId();

    List<JsonObject> fetchedLoansResponse = loansClient.getAll();

    JsonObject firstFetchedLoan = getRecordById(
      fetchedLoansResponse, firstLoanId).get();

    JsonObject secondFetchedLoan = getRecordById(
      fetchedLoansResponse, secondLoanId).get();

    assertThat("has item title",
      firstFetchedLoan.getJsonObject("item").containsKey("title"), is(true));

    assertThat("title is taken from instance",
      firstFetchedLoan.getJsonObject("item").getString("title"),
      is("The Long Way to a Small, Angry Planet"));

    assertThat("has item title",
      secondFetchedLoan.getJsonObject("item").containsKey("title"), is(true));

    assertThat("title is taken from instance",
      secondFetchedLoan.getJsonObject("item").getString("title"),
      is("Temeraire"));
  }

  @Test
  public void noTitlesForMultipleLoansWhenHoldingOrInstanceNotFound()
    throws InterruptedException,
    MalformedURLException,
    TimeoutException,
    ExecutionException {

    UUID firstInstanceId = instancesClient.create(
      InstanceExamples.basedUponSmallAngryPlanet()).getId();

    UUID firstHoldingId = holdingsClient.create(
      new HoldingBuilder()
        .forInstance(firstInstanceId)
        .create())
      .getId();

    UUID firstItemId = itemsClient.create(
      ItemExamples.basedUponSmallAngryPlanet()
        .forHolding(firstHoldingId))
      .getId();

    UUID secondInstanceId = instancesClient.create(
      InstanceExamples.basedUponTemeraire()).getId();

    UUID secondHoldingId = holdingsClient.create(
      new HoldingBuilder()
        .forInstance(secondInstanceId)
        .create())
      .getId();

    UUID secondItemId = itemsClient.create(
      ItemExamples.basedUponTemeraire()
        .forHolding(secondHoldingId))
      .getId();

    UUID firstLoanId = loansClient.create(new LoanBuilder()
      .withItemId(firstItemId)).getId();

    UUID secondLoanId = loansClient.create(new LoanBuilder()
      .withItemId(secondItemId)).getId();

    //Delete instance or holding
    instancesClient.delete(firstInstanceId);

    holdingsClient.delete(secondHoldingId);

    List<JsonObject> fetchedLoansResponse = loansClient.getAll();

    JsonObject firstFetchedLoan = getRecordById(
      fetchedLoansResponse, firstLoanId).get();

    JsonObject secondFetchedLoan = getRecordById(
      fetchedLoansResponse, secondLoanId).get();

    assertThat("has no title",
      firstFetchedLoan.getJsonObject("item").containsKey("title"), is(false));

    assertThat("has no title",
      secondFetchedLoan.getJsonObject("item").containsKey("title"), is(false));
  }
}
