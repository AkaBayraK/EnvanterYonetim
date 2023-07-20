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

/**
 * Ürünlerin hangi depolarda tutulduğu belirtilmelidir. 
 */
@Data
@Entity
@Table(name = "DEPO_URUN_TBL", schema = "envanterDB" )
public class DepoUrunEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -4151019205584999748L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "DEPO_ID", nullable = false)
    private String depoId;
    
    @Column(name = "URUN_ID", nullable = false)
    private Long urunId;
 
    @Column(name = "ADET", nullable = false)
    private BigDecimal adet;
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return DepoUrunEntity.class;
	}
	
    public DepoUrunEntity() {
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
		DepoUrunEntity other = (DepoUrunEntity) obj;
		return Objects.equals(adet, other.adet) && Objects.equals(depoId, other.depoId) && Objects.equals(id, other.id)
				&& Objects.equals(urunId, other.urunId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(adet, depoId, id, urunId);
	}

	@Override
	public String toString() {
		return "UrunDepoEntity [id=" + id + ", urunId=" + urunId + ", depoId=" + depoId + ", adet=" + adet + "]";
	}


}
