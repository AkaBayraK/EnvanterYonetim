package com.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
 * 
 * "guid": "661078d7-2c7e-4981-b538-210742cc3aee",
    "errorCode": "Inventory-not-found",
    "message": "Inventory with search not foundInventoryEntity [id=null, warehouse=WarehouseEntity [id=9, name=null, address=null, regionName=null, cityName=null], product=ProductEntity [id=19, name=null, category=CategoryEntity [id=null, name=null], piece=null, minPiece=null], inputDate=null, outDate=null, piece=null]",
    "statusCode": 404,
    "statusName": "NOT_FOUND",
    "path": "/api/inventory/search",
    "method": "GET",
    "timestamp": [
        2023,
        7,
        26,
        18,
        29,
        52,
        895122000
    ]
} 
 * 
 * */

@Data
@Getter
@Setter
@Entity
@Table(name = "API_EXCEPTION_TBL")
public class ApiExceptionEntity extends BaseEntity {
	
    private static final long serialVersionUID = 829792240087779678L;
    
	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "GUID")
	private String guid;
	@Column(name = "ERROR_CODE")
    private String errorCode;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "STATUS_CODE")
    private Integer statusCode;
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Column(name = "PATH")
    private String path;
    @Column(name = "METHOD")
    private String method;
    @Column(name = "TIMESTAMP")
    private Date timestamp;
    
	@SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return ApiExceptionEntity.class;
	}
	
    public ApiExceptionEntity() {

	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ApiExceptionEntity [guid=" + guid + ", errorCode=" + errorCode + ", message=" + message
				+ ", statusCode=" + statusCode + ", statusName=" + statusName + ", path=" + path + ", method=" + method
				+ ", timestamp=" + timestamp + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiExceptionEntity other = (ApiExceptionEntity) obj;
		return Objects.equals(errorCode, other.errorCode) && Objects.equals(guid, other.guid)
				&& Objects.equals(message, other.message) && Objects.equals(method, other.method)
				&& Objects.equals(path, other.path) && Objects.equals(statusCode, other.statusCode)
				&& Objects.equals(statusName, other.statusName) && Objects.equals(timestamp, other.timestamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(errorCode, guid, message, method, path, statusCode, statusName, timestamp);
		return result;
	}
	
}