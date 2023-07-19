package com.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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
    
    @Column(name = "KATEGORI_ID", nullable = false)
    private BigDecimal kategoriId;
    
    @Column(name = "ADET", nullable = false)
    private BigDecimal adet;
 
	@SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return UrunEntity.class;
	}
	
    public UrunEntity() {
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrunEntity other = (UrunEntity) obj;
		return Objects.equals(adet, other.adet) && Objects.equals(adi, other.adi) && Objects.equals(id, other.id)
				&& Objects.equals(kategoriId, other.kategoriId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(adet, adi, id, kategoriId);
	}

	@Override
	public String toString() {
		return "UrunEntity [id=" + id + ", adi=" + adi + ", kategoriId=" + kategoriId + ", adet=" + adet + "]";
	}

}
