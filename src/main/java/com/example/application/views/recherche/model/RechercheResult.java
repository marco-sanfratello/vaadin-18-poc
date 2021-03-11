package com.example.application.views.recherche.model;

public class RechercheResult {
    private String id;
    private Type type;
    private String caption;

    private boolean isPersistent;
    private boolean isTransient;

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
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean aTransient) {
        isTransient = aTransient;
    }

    @Override
    public String toString() {
        return "RechercheResult{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", caption='" + caption + '\'' +
                '}';
    }

    public enum Type {
        Airport, Handler, Hotel, LocalContact, NationalContact, RentalCar, CateringCompany
    }
}
