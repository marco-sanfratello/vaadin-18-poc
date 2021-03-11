package com.example.application.views.person.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Person {
    private String id;
    private Boolean dormant;
    private Title title;
    private Grade grade;
    private String loginName;
    private String password;
    private String calendarPassword;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String citizenship;
    private String personAddress;
    private String initials;
    private String addressPrivate;
    private String emailPrivate;
    private String emailBusiness;
    private String emailSignatureBusiness;
    private String telePrivate;
    private String teleBusiness;
    private String mobilePrivate;
    private String mobileBusiness;
    private String faxPrivate;
    private String faxBusiness;
    private String comment;
    private String commentPrivate;
    private String commentBusiness;

    private String emComment;
    private String emCoName1;
    private String emCoRelationship1;
    private String emCoPhoneNumber1;

    private String emCoName2;
    private String emCoRelationship2;
    private String emCoPhoneNumber2;

    private String emCoName3;
    private String emCoRelationship3;
    private String emCoPhoneNumber3;

    private Set<Company> companies = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getDormant() {
        return this.dormant;
    }

    public void setDormant(Boolean dormant) {
        this.dormant = dormant;
    }

    public Title getTitle() {
        return this.title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCalendarPassword() {
        return this.calendarPassword;
    }

    public void setCalendarPassword(String calendarPassword) {
        this.calendarPassword = calendarPassword;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getCitizenship() {
        return this.citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getPersonAddress() {
        return this.personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getInitials() {
        return this.initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getAddressPrivate() {
        return this.addressPrivate;
    }

    public void setAddressPrivate(String addressPrivate) {
        this.addressPrivate = addressPrivate;
    }

    public String getEmailPrivate() {
        return this.emailPrivate;
    }

    public void setEmailPrivate(String emailPrivate) {
        this.emailPrivate = emailPrivate;
    }

    public String getEmailBusiness() {
        return this.emailBusiness;
    }

    public void setEmailBusiness(String emailBusiness) {
        this.emailBusiness = emailBusiness;
    }

    public String getEmailSignatureBusiness() {
        return emailSignatureBusiness;
    }

    public void setEmailSignatureBusiness(String emailSignatureBusiness) {
        this.emailSignatureBusiness = emailSignatureBusiness;
    }

    public String getTelePrivate() {
        return this.telePrivate;
    }

    public void setTelePrivate(String telePrivate) {
        this.telePrivate = telePrivate;
    }

    public String getTeleBusiness() {
        return this.teleBusiness;
    }

    public void setTeleBusiness(String teleBusiness) {
        this.teleBusiness = teleBusiness;
    }

    public String getMobilePrivate() {
        return this.mobilePrivate;
    }

    public void setMobilePrivate(String mobilePrivate) {
        this.mobilePrivate = mobilePrivate;
    }

    public String getMobileBusiness() {
        return this.mobileBusiness;
    }

    public void setMobileBusiness(String mobileBusiness) {
        this.mobileBusiness = mobileBusiness;
    }

    public String getFaxPrivate() {
        return this.faxPrivate;
    }

    public void setFaxPrivate(String faxPrivate) {
        this.faxPrivate = faxPrivate;
    }

    public String getFaxBusiness() {
        return this.faxBusiness;
    }

    public void setFaxBusiness(String faxBusiness) {
        this.faxBusiness = faxBusiness;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentPrivate() {
        return this.commentPrivate;
    }

    public void setCommentPrivate(String commentPrivate) {
        this.commentPrivate = commentPrivate;
    }

    public String getCommentBusiness() {
        return this.commentBusiness;
    }

    public void setCommentBusiness(String commentBusiness) {
        this.commentBusiness = commentBusiness;
    }

    public String getEmComment() {
        return this.emComment;
    }

    public void setEmComment(String emComment) {
        this.emComment = emComment;
    }

    public String getEmCoName1() {
        return this.emCoName1;
    }

    public void setEmCoName1(String emCoName1) {
        this.emCoName1 = emCoName1;
    }

    public String getEmCoRelationship1() {
        return this.emCoRelationship1;
    }

    public void setEmCoRelationship1(String emCoRelationship1) {
        this.emCoRelationship1 = emCoRelationship1;
    }

    public String getEmCoPhoneNumber1() {
        return this.emCoPhoneNumber1;
    }

    public void setEmCoPhoneNumber1(String emCoPhoneNumber1) {
        this.emCoPhoneNumber1 = emCoPhoneNumber1;
    }

    public String getEmCoName2() {
        return this.emCoName2;
    }

    public void setEmCoName2(String emCoName2) {
        this.emCoName2 = emCoName2;
    }

    public String getEmCoRelationship2() {
        return this.emCoRelationship2;
    }

    public void setEmCoRelationship2(String emCoRelationship2) {
        this.emCoRelationship2 = emCoRelationship2;
    }

    public String getEmCoPhoneNumber2() {
        return this.emCoPhoneNumber2;
    }

    public void setEmCoPhoneNumber2(String emCoPhoneNumber2) {
        this.emCoPhoneNumber2 = emCoPhoneNumber2;
    }

    public String getEmCoName3() {
        return this.emCoName3;
    }

    public void setEmCoName3(String emCoName3) {
        this.emCoName3 = emCoName3;
    }

    public String getEmCoRelationship3() {
        return this.emCoRelationship3;
    }

    public void setEmCoRelationship3(String emCoRelationship3) {
        this.emCoRelationship3 = emCoRelationship3;
    }

    public String getEmCoPhoneNumber3() {
        return this.emCoPhoneNumber3;
    }

    public void setEmCoPhoneNumber3(String emCoPhoneNumber3) {
        this.emCoPhoneNumber3 = emCoPhoneNumber3;
    }

    public Set<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", dormant=" + dormant +
                ", title=" + title +
                ", grade=" + grade +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", calendarPassword='" + calendarPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", personAddress='" + personAddress + '\'' +
                ", initials='" + initials + '\'' +
                ", addressPrivate='" + addressPrivate + '\'' +
                ", emailPrivate='" + emailPrivate + '\'' +
                ", emailBusiness='" + emailBusiness + '\'' +
                ", emailSignatureBusiness='" + emailSignatureBusiness + '\'' +
                ", telePrivate='" + telePrivate + '\'' +
                ", teleBusiness='" + teleBusiness + '\'' +
                ", mobilePrivate='" + mobilePrivate + '\'' +
                ", mobileBusiness='" + mobileBusiness + '\'' +
                ", faxPrivate='" + faxPrivate + '\'' +
                ", faxBusiness='" + faxBusiness + '\'' +
                ", comment='" + comment + '\'' +
                ", commentPrivate='" + commentPrivate + '\'' +
                ", commentBusiness='" + commentBusiness + '\'' +
                ", emComment='" + emComment + '\'' +
                ", emCoName1='" + emCoName1 + '\'' +
                ", emCoRelationship1='" + emCoRelationship1 + '\'' +
                ", emCoPhoneNumber1='" + emCoPhoneNumber1 + '\'' +
                ", emCoName2='" + emCoName2 + '\'' +
                ", emCoRelationship2='" + emCoRelationship2 + '\'' +
                ", emCoPhoneNumber2='" + emCoPhoneNumber2 + '\'' +
                ", emCoName3='" + emCoName3 + '\'' +
                ", emCoRelationship3='" + emCoRelationship3 + '\'' +
                ", emCoPhoneNumber3='" + emCoPhoneNumber3 + '\'' +
                ", companies=" + companies +
                '}';
    }

    public boolean contains(String filter) {
        return (firstName + lastName + companies.stream().map(Company::getShortName).collect(Collectors.joining())).toLowerCase().contains(filter);
    }

    public enum Title {
        MR("Mr."), MRS("Mrs.");

        private final String caption;

        Title(String caption) {
            this.caption = caption;
        }

        public String getCaption() {
            return this.caption;
        }
    }

    public enum Grade {
        NONGRADE(""), PROFESSOR("Prof."), DOCTOR("Dr.");

        private final String caption;

        Grade(String caption) {
            this.caption = caption;
        }

        public String getCaption() {
            return this.caption;
        }
    }
}
