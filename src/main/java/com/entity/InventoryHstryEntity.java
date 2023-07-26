package com.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * (Envanterde) Bu işlemler yapılırken bunların bir history tablosunda kaydının tutulması gerekmektedir. 
 */
@Data
@Entity
@Table(name = "INVENTORY_HSTRY_TBL")
public class InventoryHstryEntity  extends BaseEntity {
		
	private static final long serialVersionUID = -6025897969017658050L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "INVENTORY_ID", nullable = false)
    private Long inventoryId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "WAREHOUSE_ID", nullable = false)
    private Long warehouseId;
  
    /* Eğer giriş tarihi dolu ise envantere ürün giri olduğu belirtir*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "INPUT_DATE", nullable = false)
    private Date inputDate;

    /* Eğer çıkış tarihi dolu ise o üründen ve depodan ürün çıkışı olduğu belirlenir.*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "OUT_DATE", nullable = false)
    private Date outDate;
    
    @Column(name = "PIECE", nullable = false)
    private BigDecimal piece;
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return InventoryHstryEntity.class;
	}
	
    public InventoryHstryEntity() {}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "InventoryHstryEntity [id=" + id + ", inventoryId=" + inventoryId + ", productId=" + productId
				+ ", warehouseId=" + warehouseId + ", inputDate=" + inputDate + ", outDate=" + outDate + ", piece=" + piece + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryHstryEntity other = (InventoryHstryEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(inputDate, other.inputDate)
				&& Objects.equals(inventoryId, other.inventoryId) && Objects.equals(outDate, other.outDate)
				&& Objects.equals(piece, other.piece) && Objects.equals(productId, other.productId)
				&& Objects.equals(warehouseId, other.warehouseId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, inputDate, inventoryId, outDate, piece, productId, warehouseId);
		return result;
	}   

}