package org.folio.circulation.domain.anonymization.config;

public class AnonymizationConfig {

  private final TenantLoanAnonymizationSettings tenantSettings;

  public AnonymizationConfig(TenantLoanAnonymizationSettings tenantSettings) {
    this.tenantSettings = tenantSettings;
  }

  public TenantLoanAnonymizationSettings getTenantSettings() {
    return tenantSettings;
  }


}
