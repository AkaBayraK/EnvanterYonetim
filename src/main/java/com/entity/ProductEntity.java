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
 * Her ürünün id ‘sinin, adının, kategorisinin ve ürün sayısının olması zorunludur. 
 */
@Data
@Entity
@Table(name = "PRODUCT_TBL")
public class ProductEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -7351519811931093265L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "NAME", nullable = false)
    private String name;
    
	@ManyToOne(targetEntity=CategoryEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="CATEGORY_ID",referencedColumnName="ID", nullable = false)
	private CategoryEntity category;
    
    @Column(name = "PIECE", nullable = false)
    private BigDecimal piece;
    
    /**
     * Ürün çıkarma işlemi olduğunda ürünün min adet sayısını belirler ve warning olarak bildirilmesini sağlar
     */
    @Column(name = "MIN_PIECE", nullable = false)
    private BigDecimal minPiece;
 
	@SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return ProductEntity.class;
	}
	
    public ProductEntity() {
    	this.setCategory(new CategoryEntity());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "ProductEntity [id=" + id + ", name=" + name + ", category=" + category + ", piece=" + piece
				+ ", minPiece=" + minPiece + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductEntity other = (ProductEntity) obj;
		return Objects.equals(category, other.category) && Objects.equals(id, other.id)
				&& Objects.equals(minPiece, other.minPiece) && Objects.equals(name, other.name)
				&& Objects.equals(piece, other.piece);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(category, id, minPiece, name, piece);
		return result;
	}

}
