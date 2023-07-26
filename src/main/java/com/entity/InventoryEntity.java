package com.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Envanterde bulunacak ürünler için ürün ekleme, çıkarma, silme ve düzenleme işlemlerinin yapılması.
 * 
 */
@Data
@Entity
@Table(name = "INVENTORY_TBL")
@NamedQueries({
@NamedQuery(name="InventoryEntity.findInventoryProductAll", query="SELECT x FROM InventoryEntity x WHERE x.product.id=?1"),
})
public class InventoryEntity  extends BaseEntity {
		
	private static final long serialVersionUID = -7234877787824421367L;

	public final static String FIND_INVENTORY_PRODUCT_ALL	=	"InventoryEntity.findInventoryProductAll";

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
	@ManyToOne(targetEntity=WarehouseEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="WAREHOUSE_ID",referencedColumnName="ID", nullable = false)
	private WarehouseEntity warehouse;
    
	@ManyToOne(targetEntity=ProductEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="PRODUCT_ID",referencedColumnName="ID", nullable = false)
	private ProductEntity product;
  
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
		return InventoryEntity.class;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public InventoryEntity() {
    	this.setProduct(new ProductEntity());
    	this.setWarehouse(new WarehouseEntity());
	}
    
	public InventoryHstryEntity createAutomaticHistory(){
		InventoryHstryEntity result = new InventoryHstryEntity();
		 
		 result.setInventoryId(this.getId());
		 
		 result.setProductId(this.getProduct().getId());
		 result.setWarehouseId(this.getWarehouse().getId());
		 result.setInputDate(this.getInputDate());
		 result.setOutDate(this.getOutDate());
		 result.setPiece(this.getPiece());
		
		 return result;
	}

	@Override
	public String toString() {
		return "InventoryEntity [id=" + id + ", warehouse=" + warehouse + ", product=" + product + ", inputDate=" + inputDate
				+ ", outDate=" + outDate + ", piece=" + piece + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryEntity other = (InventoryEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(inputDate, other.inputDate)
				&& Objects.equals(outDate, other.outDate) && Objects.equals(piece, other.piece)
				&& Objects.equals(product, other.product) && Objects.equals(warehouse, other.warehouse);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, inputDate, outDate, piece, product, warehouse);
		return result;
	}



}
