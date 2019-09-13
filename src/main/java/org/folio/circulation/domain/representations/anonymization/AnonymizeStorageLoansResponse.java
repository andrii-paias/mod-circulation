
package org.folio.circulation.domain.representations.anonymization;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Response schema for anonymize loans request
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder( {
    "anonymizedLoans",
    "notAnonymizedLoans"
})
public class AnonymizeStorageLoansResponse {

    /**
     * Successfully anonymized loan ids
     * 
     */
    @JsonProperty("anonymizedLoans")
    @JsonPropertyDescription("Successfully anonymized loan ids")
    private List<String> anonymizedLoans = new ArrayList<String>();
    /**
     * A set of errors
     * 
     */
    @JsonProperty("notAnonymizedLoans")
    @JsonPropertyDescription("A set of errors")
    private NotAnonymizedLoans notAnonymizedLoans;

    /**
     * Successfully anonymized loan ids
     * 
     */
    @JsonProperty("anonymizedLoans")
    public List<String> getAnonymizedLoans() {
        return anonymizedLoans;
    }

    /**
     * Successfully anonymized loan ids
     * 
     */
    @JsonProperty("anonymizedLoans")
    public void setAnonymizedLoans(List<String> anonymizedLoans) {
        this.anonymizedLoans = anonymizedLoans;
    }

    public AnonymizeStorageLoansResponse withAnonymizedLoans(List<String> anonymizedLoans) {
        this.anonymizedLoans = anonymizedLoans;
        return this;
    }

    /**
     * A set of errors
     * 
     */
    @JsonProperty("notAnonymizedLoans")
    public NotAnonymizedLoans getNotAnonymizedLoans() {
        return notAnonymizedLoans;
    }

    /**
     * A set of errors
     * 
     */
    @JsonProperty("notAnonymizedLoans")
    public void setNotAnonymizedLoans(NotAnonymizedLoans notAnonymizedLoans) {
        this.notAnonymizedLoans = notAnonymizedLoans;
    }

    public AnonymizeStorageLoansResponse withNotAnonymizedLoans(NotAnonymizedLoans notAnonymizedLoans) {
        this.notAnonymizedLoans = notAnonymizedLoans;
        return this;
    }

}
