package org.folio.circulation.support;

import java.net.MalformedURLException;

import org.folio.circulation.support.http.client.OkapiHttpClient;
import org.folio.circulation.support.http.server.WebContext;

import io.vertx.core.http.HttpClient;

public class Clients {
  private final CollectionResourceClient requestsStorageClient;
  private final CollectionResourceClient cancellationReasonStorageClient;
  private final CollectionResourceClient itemsStorageClient;
  private final CollectionResourceClient holdingsStorageClient;
  private final CollectionResourceClient instancesStorageClient;
  private final CollectionResourceClient usersStorageClient;
  private final CollectionResourceClient loansStorageClient;
  private final CollectionResourceClient locationsStorageClient;
  private final CollectionResourceClient institutionsStorageClient;
  private final CollectionResourceClient campusesStorageClient;
  private final CollectionResourceClient librariesStorageClient;
  private final CollectionResourceClient materialTypesStorageClient;
  private final CollectionResourceClient loanTypesStorageClient;
  private final CollectionResourceClient proxiesForClient;
  private final CollectionResourceClient loanPoliciesStorageClient;
  private final CollectionResourceClient fixedDueDateSchedulesStorageClient;
  private final CirculationRulesClient circulationLoanRulesClient;
  private final CirculationRulesClient circulationRequestRulesClient;
  private final CirculationRulesClient circulationNoticeRulesClient;
  private final CollectionResourceClient circulationRulesStorageClient;
  private final CollectionResourceClient requestPoliciesStorageClient;
  private final CollectionResourceClient servicePointsStorageClient;
  private final CollectionResourceClient calendarStorageClient;
  private final CollectionResourceClient patronGroupsStorageClient;
  private final CollectionResourceClient patronNoticePolicesStorageClient;
  private final CollectionResourceClient patronNoticeClient;
  private final CollectionResourceClient configurationStorageClient;
  private final CollectionResourceClient scheduledNoticesStorageClient;
  private final CollectionResourceClient accountsStorageClient;

  public static Clients create(WebContext context, HttpClient httpClient) {
    return new Clients(context.createHttpClient(httpClient), context);
  }

  private Clients(OkapiHttpClient client, WebContext context) {
    try {
      requestsStorageClient = createRequestsStorageClient(client, context);
      cancellationReasonStorageClient = createCancellationReasonStorageClient(client, context);
      itemsStorageClient = createItemsStorageClient(client, context);
      holdingsStorageClient = createHoldingsStorageClient(client, context);
      instancesStorageClient = createInstanceStorageClient(client, context);
      usersStorageClient = createUsersStorageClient(client, context);
      loansStorageClient = createLoansStorageClient(client, context);
      locationsStorageClient = createLocationsStorageClient(client, context);
      institutionsStorageClient = createInstitutionsStorageClient(client, context);
      campusesStorageClient = createCampusesStorageClient(client, context);
      librariesStorageClient = createLibrariesStorageClient(client, context);
      materialTypesStorageClient = createMaterialTypesStorageClient(client, context);
      loanTypesStorageClient = createLoanTypesStorageClient(client, context);
      proxiesForClient = createProxyUsersStorageClient(client, context);
      circulationLoanRulesClient = createCirculationLoanRulesClient(client, context);
      circulationRequestRulesClient = createCirculationRequestRulesClient(client, context);
      circulationNoticeRulesClient = createCirculationNoticeRulesClient(client, context);
      circulationRulesStorageClient = createCirculationRulesStorageClient(client, context);
      loanPoliciesStorageClient = createLoanPoliciesStorageClient(client, context);
      requestPoliciesStorageClient = createRequestPoliciesStorageClient(client, context);
      fixedDueDateSchedulesStorageClient = createFixedDueDateSchedulesStorageClient(client, context);
      servicePointsStorageClient = createServicePointsStorageClient(client, context);
      patronGroupsStorageClient = createPatronGroupsStorageClient(client, context);
      calendarStorageClient = createCalendarStorageClient(client, context);
      patronNoticePolicesStorageClient = createPatronNoticePolicesStorageClient(client, context);
      patronNoticeClient = createPatronNoticeClient(client, context);
      configurationStorageClient = createConfigurationStorageClient(client, context);
      scheduledNoticesStorageClient = createScheduledNoticesStorageClient(client, context);
      accountsStorageClient = createAccountsStorageClient(client,context);
    }
    catch(MalformedURLException e) {
      throw new InvalidOkapiLocationException(context.getOkapiLocation(), e);
    }
  }

  public CollectionResourceClient requestsStorage() {
    return requestsStorageClient;
  }

  public CollectionResourceClient cancellationReasonStorage() {
    return cancellationReasonStorageClient;
  }

  public CollectionResourceClient requestPoliciesStorage() { return requestPoliciesStorageClient; }

  public CollectionResourceClient itemsStorage() {
    return itemsStorageClient;
  }

  public CollectionResourceClient holdingsStorage() {
    return holdingsStorageClient;
  }

  public CollectionResourceClient instancesStorage() {
    return instancesStorageClient;
  }

  public CollectionResourceClient usersStorage() {
    return usersStorageClient;
  }

  public CollectionResourceClient loansStorage() {
    return loansStorageClient;
  }

  public CollectionResourceClient locationsStorage() {
    return locationsStorageClient;
  }

  public CollectionResourceClient institutionsStorage() {
    return institutionsStorageClient;
  }

  public CollectionResourceClient campusesStorage() {
    return campusesStorageClient;
  }

  public CollectionResourceClient librariesStorage() {
    return librariesStorageClient;
  }

  public CollectionResourceClient materialTypesStorage() {
    return materialTypesStorageClient;
  }

  public CollectionResourceClient loanTypesStorage() {
    return loanTypesStorageClient;
  }

  public CollectionResourceClient loanPoliciesStorage() {
    return loanPoliciesStorageClient;
  }

  public CollectionResourceClient fixedDueDateSchedules() {
    return fixedDueDateSchedulesStorageClient;
  }

  public CollectionResourceClient servicePointsStorage() {
    return servicePointsStorageClient;
  }

  public CollectionResourceClient patronGroupsStorage() {
    return patronGroupsStorageClient;
  }

  public CollectionResourceClient calendarStorageClient() {
    return calendarStorageClient;
  }

  public CollectionResourceClient configurationStorageClient() {
    return configurationStorageClient;
  }

  public CollectionResourceClient userProxies() {
    return proxiesForClient;
  }

  public CirculationRulesClient circulationLoanRules() {
    return circulationLoanRulesClient;
  }

  public CirculationRulesClient circulationRequestRules(){
    return circulationRequestRulesClient;
  }

  public CirculationRulesClient circulationNoticeRules(){
    return circulationNoticeRulesClient;
  }

  public CollectionResourceClient circulationRulesStorage() {
    return circulationRulesStorageClient;
  }

  public CollectionResourceClient patronNoticePolicesStorageClient() {
    return patronNoticePolicesStorageClient;
  }

  public CollectionResourceClient patronNoticeClient() {
    return patronNoticeClient;
  }

  public CollectionResourceClient scheduledNoticesStorageClient() {
    return scheduledNoticesStorageClient;
  }

  public CollectionResourceClient accountsStorageClient() {
    return accountsStorageClient;
  }

  private static CollectionResourceClient getCollectionResourceClient(
    OkapiHttpClient client,
    WebContext context,
    String path)
    throws MalformedURLException {

    return new CollectionResourceClient(client, context.getOkapiBasedUrl(path));
  }

  private static CirculationRulesClient createCirculationLoanRulesClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context, "/circulation/rules/loan-policy");
  }

  private static CirculationRulesClient createCirculationRequestRulesClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context, "/circulation/rules/request-policy");
  }

  private static CirculationRulesClient createCirculationNoticeRulesClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context, "/circulation/rules/notice-policy");
  }

  private static CollectionResourceClient createRequestsStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/request-storage/requests");
  }

  private static CollectionResourceClient createCancellationReasonStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/cancellation-reason-storage/cancellation-reasons");
  }

  private static CollectionResourceClient createItemsStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/item-storage/items");
  }

  private static CollectionResourceClient createHoldingsStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return new CollectionResourceClient(
      client, context.getOkapiBasedUrl("/holdings-storage/holdings"));
  }

  private static CollectionResourceClient createInstanceStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return new CollectionResourceClient(
      client, context.getOkapiBasedUrl("/instance-storage/instances"));
  }

  private static CollectionResourceClient createUsersStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/users");
  }

  private static CollectionResourceClient createLoansStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/loan-storage/loans");
  }

  private static CollectionResourceClient createLocationsStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/locations");
  }

  private static CollectionResourceClient createInstitutionsStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/location-units/institutions");
  }

  private static CollectionResourceClient createCampusesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/location-units/campuses");
  }

  private static CollectionResourceClient createLibrariesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/location-units/libraries");
  }

  private CollectionResourceClient createProxyUsersStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/proxiesfor");
  }

  private CollectionResourceClient createMaterialTypesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/material-types");
  }

  private CollectionResourceClient createLoanTypesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/loan-types");
  }

  private CollectionResourceClient createLoanPoliciesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/loan-policy-storage/loan-policies");
  }

  private CollectionResourceClient createRequestPoliciesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/request-policy-storage/request-policies");
  }

  private CollectionResourceClient createFixedDueDateSchedulesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/fixed-due-date-schedule-storage/fixed-due-date-schedules");
  }


  private CollectionResourceClient createCirculationRulesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/circulation-rules-storage");
  }

  private CollectionResourceClient createServicePointsStorageClient(
      OkapiHttpClient client,
      WebContext context)
      throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/service-points");
  }

  private CollectionResourceClient createPatronGroupsStorageClient(
      OkapiHttpClient client,
      WebContext context)
      throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/groups");
  }

  private CollectionResourceClient createCalendarStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/calendar/periods");
  }

  private CollectionResourceClient createPatronNoticePolicesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {
    return getCollectionResourceClient(client, context,
      "/patron-notice-policy-storage/patron-notice-policies");
  }

  private CollectionResourceClient createPatronNoticeClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/patron-notice");
  }


  private CollectionResourceClient createConfigurationStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/configurations/entries");
  }

  private CollectionResourceClient createScheduledNoticesStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/scheduled-notice-storage/scheduled-notices");
  }
  private CollectionResourceClient createAccountsStorageClient(
    OkapiHttpClient client,
    WebContext context)
    throws MalformedURLException {
    return getCollectionResourceClient(client, context, "/accounts");
  }
}
