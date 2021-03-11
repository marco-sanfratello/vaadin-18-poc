package com.example.application.views.person.model;

import java.util.LinkedList;
import java.util.List;

public class Company {
    private Boolean disabled;
    private Boolean commercialOperator;
    private String fullName;
    // mandatory
    private String shortName;
    private String streetAndHousenumber;
    private String mailbox;
    private String town;
    private String zipcode;
    private String country;
    private String streetAndHousenumberAcc;
    private String taxNumber;
    private String aocNumber;
    private String townAcc;
    private String zipcodeAcc;
    private String mailboxAcc;
    private String countryAcc;
    private String department;
    private String phoneNumber;
    private String phoneNumberOperations;
    private String phoneNumberSales;
    private String emailGeneral;
    private String emailOperations;
    private String emailSales;
    private String emailAcc;
    private String comment;
    private Boolean creditAble;
    private Boolean debitEntry;
    private Boolean creditCardAcc;
    private Boolean invoiceAcc;
    private Boolean debitAuthorization;
    private Boolean authorizationAccountingViaCreditCard;
    private String accountingDetails;
    private String ibanBic;
    private String kindOfCreditCard;
    private String cardOwner;
    private String creditCardNumber;
    private String creditCardValid;
    private String creditCardCcv;
    private String customerNumber;
    private Boolean vatFree;
    private List<String> qCodes = new LinkedList<>();

    public String getCaption() {
        return getShortName();
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getCommercialOperator() {
        return this.commercialOperator;
    }

    public void setCommercialOperator(Boolean commercialOperator) {
        this.commercialOperator = commercialOperator;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStreetAndHousenumber() {
        return this.streetAndHousenumber;
    }

    public void setStreetAndHousenumber(String streetAndHousenumber) {
        this.streetAndHousenumber = streetAndHousenumber;
    }

    public String getMailbox() {
        return this.mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetAndHousenumberAcc() {
        return this.streetAndHousenumberAcc;
    }

    public void setStreetAndHousenumberAcc(String streetAndHousenumberAcc) {
        this.streetAndHousenumberAcc = streetAndHousenumberAcc;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getAocNumber() {
        return this.aocNumber;
    }

    public void setAocNumber(String aocNumber) {
        this.aocNumber = aocNumber;
    }

    public String getTownAcc() {
        return this.townAcc;
    }

    public void setTownAcc(String townAcc) {
        this.townAcc = townAcc;
    }

    public String getZipcodeAcc() {
        return this.zipcodeAcc;
    }

    public void setZipcodeAcc(String zipcodeAcc) {
        this.zipcodeAcc = zipcodeAcc;
    }

    public String getMailboxAcc() {
        return this.mailboxAcc;
    }

    public void setMailboxAcc(String mailboxAcc) {
        this.mailboxAcc = mailboxAcc;
    }

    public String getCountryAcc() {
        return this.countryAcc;
    }

    public void setCountryAcc(String countryAcc) {
        this.countryAcc = countryAcc;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberOperations() {
        return this.phoneNumberOperations;
    }

    public void setPhoneNumberOperations(String phoneNumberOperations) {
        this.phoneNumberOperations = phoneNumberOperations;
    }

    public String getPhoneNumberSales() {
        return this.phoneNumberSales;
    }

    public void setPhoneNumberSales(String phoneNumberSales) {
        this.phoneNumberSales = phoneNumberSales;
    }

    public String getEmailGeneral() {
        return this.emailGeneral;
    }

    public void setEmailGeneral(String emailGeneral) {
        this.emailGeneral = emailGeneral;
    }

    public String getEmailOperations() {
        return this.emailOperations;
    }

    public void setEmailOperations(String emailOperations) {
        this.emailOperations = emailOperations;
    }

    public String getEmailSales() {
        return this.emailSales;
    }

    public void setEmailSales(String emailSales) {
        this.emailSales = emailSales;
    }

    public String getEmailAcc() {
        return this.emailAcc;
    }

    public void setEmailAcc(String emailAcc) {
        this.emailAcc = emailAcc;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getCreditAble() {
        return this.creditAble;
    }

    public void setCreditAble(Boolean creditAble) {
        this.creditAble = creditAble;
    }

    public Boolean getDebitEntry() {
        return this.debitEntry;
    }

    public void setDebitEntry(Boolean debitEntry) {
        this.debitEntry = debitEntry;
    }

    public Boolean getCreditCardAcc() {
        return this.creditCardAcc;
    }

    public void setCreditCardAcc(Boolean creditCardAcc) {
        this.creditCardAcc = creditCardAcc;
    }

    public Boolean getInvoiceAcc() {
        return this.invoiceAcc;
    }

    public void setInvoiceAcc(Boolean invoiceAcc) {
        this.invoiceAcc = invoiceAcc;
    }

    public Boolean getDebitAuthorization() {
        return this.debitAuthorization;
    }

    public void setDebitAuthorization(Boolean debitAuthorization) {
        this.debitAuthorization = debitAuthorization;
    }

    public Boolean getAuthorizationAccountingViaCreditCard() {
        return this.authorizationAccountingViaCreditCard;
    }

    public void setAuthorizationAccountingViaCreditCard(Boolean authorizationAccountingViaCreditCard) {
        this.authorizationAccountingViaCreditCard = authorizationAccountingViaCreditCard;
    }

    public String getAccountingDetails() {
        return this.accountingDetails;
    }

    public void setAccountingDetails(String accountingDetails) {
        this.accountingDetails = accountingDetails;
    }

    public String getIbanBic() {
        return this.ibanBic;
    }

    public void setIbanBic(String ibanBic) {
        this.ibanBic = ibanBic;
    }

    public String getKindOfCreditCard() {
        return this.kindOfCreditCard;
    }

    public void setKindOfCreditCard(String kindOfCreditCard) {
        this.kindOfCreditCard = kindOfCreditCard;
    }

    public String getCardOwner() {
        return this.cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardValid() {
        return this.creditCardValid;
    }

    public void setCreditCardValid(String creditCardValid) {
        this.creditCardValid = creditCardValid;
    }

    public String getCreditCardCcv() {
        return this.creditCardCcv;
    }

    public void setCreditCardCcv(String creditCardCcv) {
        this.creditCardCcv = creditCardCcv;
    }

    public String getCustomerNumber() {
        return this.customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Boolean getVatFree() {
        return this.vatFree;
    }

    public void setVatFree(Boolean vatFree) {
        this.vatFree = vatFree;
    }

    public List<String> getQCodes() {
        return this.qCodes;
    }

    public void setQCodes(List<String> qCodes) {
        this.qCodes = qCodes;
    }

    public String toString() {
        return "Company(disabled=" + this.getDisabled() + ", commercialOperator=" + this.getCommercialOperator() + ", fullName=" + this.getFullName() + ", shortName=" + this.getShortName() + ", streetAndHousenumber=" + this.getStreetAndHousenumber() + ", mailbox=" + this.getMailbox() + ", town=" + this.getTown() + ", zipcode=" + this.getZipcode() + ", country=" + this.getCountry() + ", streetAndHousenumberAcc=" + this.getStreetAndHousenumberAcc() + ", taxNumber=" + this.getTaxNumber() + ", aocNumber=" + this.getAocNumber() + ", townAcc=" + this.getTownAcc() + ", zipcodeAcc=" + this.getZipcodeAcc() + ", mailboxAcc=" + this.getMailboxAcc() + ", countryAcc=" + this.getCountryAcc() + ", department=" + this.getDepartment() + ", phoneNumber=" + this.getPhoneNumber() + ", phoneNumberOperations=" + this.getPhoneNumberOperations() + ", phoneNumberSales=" + this.getPhoneNumberSales() + ", emailGeneral=" + this.getEmailGeneral() + ", emailOperations=" + this.getEmailOperations() + ", emailSales=" + this.getEmailSales() + ", emailAcc=" + this.getEmailAcc() + ", comment=" + this.getComment() + ", creditAble=" + this.getCreditAble() + ", debitEntry=" + this.getDebitEntry() + ", creditCardAcc=" + this.getCreditCardAcc() + ", invoiceAcc=" + this.getInvoiceAcc() + ", debitAuthorization=" + this.getDebitAuthorization() + ", authorizationAccountingViaCreditCard=" + this.getAuthorizationAccountingViaCreditCard() + ", accountingDetails=" + this.getAccountingDetails() + ", ibanBic=" + this.getIbanBic() + ", kindOfCreditCard=" + this.getKindOfCreditCard() + ", cardOwner=" + this.getCardOwner() + ", creditCardNumber=" + this.getCreditCardNumber() + ", creditCardValid=" + this.getCreditCardValid() + ", creditCardCcv=" + this.getCreditCardCcv() + ", customerNumber=" + this.getCustomerNumber() + ", vatFree=" + this.getVatFree() + ", qCodes=" + this.getQCodes() + ")";
    }
}
