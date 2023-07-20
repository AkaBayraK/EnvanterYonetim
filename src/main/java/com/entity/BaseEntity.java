package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable{

    private static final long serialVersionUID = 7214433176459979236L;
    
    // datanın durumu
    @Transient
    public static final String YES = "E";
    @Transient
    public static final String NO = "H";
    
    // datanın işlem türü
    @Transient
    public static final String CREATE = "CREATE";
    @Transient
    public static final String UPDATE = "UPDATE";
    
    // data işlem kullanıcı zaman bilgileri  aslında bu alanlar zorunlu olması gerekiyor ama test
    @Column(name="GMT")
    private Date gmt;

    @Column(name="CREATE_GMT")
    private Date createGmt;

    @Version
    @Column(name="VERSION")
    private Integer version = 1;

    @Column(name="USR_ID")
    private Long usrId;

    @Column(name="CREATE_USR_ID")
    private Long createUsrId;

    @Column(name="STATUS")
    @ColumnDefault("H")
    private String status;

    @Column(name=" COMMENT_", length = 512)
    private String comment;
    
    
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