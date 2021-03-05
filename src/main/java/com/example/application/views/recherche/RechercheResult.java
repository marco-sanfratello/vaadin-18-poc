package com.example.application.views.recherche;

public class RechercheResult {
    private String id;
    private Type type;
    private String caption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isPersistent() {
        return getId() != null;
    }

    public boolean isTransient() {
        return getId() == null;
    }

    public enum Type {
        Airport, Handler, Hotel, LocalContact, NationalContact, RentalCar, CateringCompany
    }
}
