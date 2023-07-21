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
@Table(name = "URUN_TBL", schema = "envanterDB" )
public class UrunEntity  extends BaseEntity {
	
	private static final long serialVersionUID = 5050574907801563099L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "ADI", nullable = false)
    private String adi;
    
	@ManyToOne(targetEntity=KategoriEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="KATEGORI_ID",referencedColumnName="ID", nullable = false)
	private KategoriEntity kategori;
    
    @Column(name = "ADET", nullable = false)
    private BigDecimal adet;
    
    /**
     * Ürün çıkarma işlemi olduğunda ürünün min adet sayısını belirler ve warning olarak bildirilmesini sağlar
     */
    @Column(name = "MIN_ADET", nullable = false)
    private BigDecimal minAdet;
 
	@SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return UrunEntity.class;
	}
	
    public UrunEntity() {
    	this.setKategori(new KategoriEntity());
	}
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrunEntity other = (UrunEntity) obj;
		return Objects.equals(adet, other.adet) && Objects.equals(adi, other.adi) && Objects.equals(id, other.id)
				&& Objects.equals(kategori, other.kategori) && Objects.equals(minAdet, other.minAdet);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adet, adi, id, kategori, minAdet);
		return result;
	}

	@Override
	public String toString() {
		return "UrunEntity [id=" + id + ", adi=" + adi + ", kategori=" + kategori + ", adet=" + adet + ", minAdet="
				+ minAdet + "]";
	}

}
