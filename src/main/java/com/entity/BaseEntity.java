package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable{

    private static final long serialVersionUID = 7214433176459979236L;
    
    @Transient
    private List<String> errorMessages = new ArrayList<>();

    @Transient
    private List<String> warningMessages = new ArrayList<>();
    
    //@JsonIgnore
    public abstract Long getId();
    //@JsonIgnore
    public abstract void setId(Long id);
    
    public BaseEntity(){
        super();
    }
    
    public List<String> getErrorMessages() {
        if (errorMessages == null) {
            errorMessages = new ArrayList<>();
        }
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    public List<String> getWarningMessages() {
        if (warningMessages == null) {
        	warningMessages = new ArrayList<>();
        }
        return warningMessages;
    }
    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }
    public boolean anyError() {
        return this.errorMessages != null && !this.errorMessages.isEmpty();
    }
    public boolean anyWarning() {
        return this.warningMessages != null && !this.warningMessages.isEmpty();
    }

}