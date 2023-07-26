package com.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Ürünlerin hangi depolarda tutulduğu belirtilmelidir. 
 */
@Data
@Entity
@Table(name = "WAREHOUSE_PRODUCT_TBL")
public class WarehouseProductEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -7253645657251637119L;

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
 
    @Column(name = "PIECE", nullable = false)
    private BigDecimal piece;
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return WarehouseProductEntity.class;
	}
	
    public WarehouseProductEntity() {}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WarehouseProductEntity [id=" + id + ", warehouse=" + warehouse + ", product=" + product + ", piece=" + piece + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WarehouseProductEntity other = (WarehouseProductEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(piece, other.piece)
				&& Objects.equals(product, other.product) && Objects.equals(warehouse, other.warehouse);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, piece, product, warehouse);
		return result;
	}

}