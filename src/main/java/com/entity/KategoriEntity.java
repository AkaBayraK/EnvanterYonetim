package com.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Kategori tablosu olmalıdır ve her ürünün bir kategorisi olması zorunludur. 
 */
@Data
@Entity
@Table(name = "KATEGORI_TBL", schema = "envanterDB" )
public class KategoriEntity extends BaseEntity {
	
	private static final long serialVersionUID = -5247777248796270880L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "ADI", nullable = false)
    private String adi;
 
	@SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return KategoriEntity.class;
	}
	
    public KategoriEntity() {
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
		KategoriEntity other = (KategoriEntity) obj;
		return Objects.equals(adi, other.adi) && Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(adi, id);
	}

	@Override
	public String toString() {
		return "KategoriEntity [id=" + id + ", adi=" + adi + "]";
	}


}
