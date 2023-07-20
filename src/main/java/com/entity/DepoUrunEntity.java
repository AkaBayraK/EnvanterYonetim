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
@Table(name = "DEPO_URUN_TBL", schema = "envanterDB" )
public class DepoUrunEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -4151019205584999748L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(targetEntity=DepoEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="DEPO_ID",referencedColumnName="ID", nullable = false)
	private DepoEntity depo;
    
	@ManyToOne(targetEntity=UrunEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="URUN_ID",referencedColumnName="ID", nullable = false)
	private UrunEntity urun;
 
    @Column(name = "ADET", nullable = false)
    private BigDecimal adet;
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return DepoUrunEntity.class;
	}
	
    public DepoUrunEntity() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepoUrunEntity other = (DepoUrunEntity) obj;
		return Objects.equals(adet, other.adet) && Objects.equals(depo, other.depo) && Objects.equals(id, other.id)
				&& Objects.equals(urun, other.urun);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adet, depo, id, urun);
		return result;
	}

	@Override
	public String toString() {
		return "DepoUrunEntity [id=" + id + ", depo=" + depo + ", urun=" + urun + ", adet=" + adet + "]";
	}
    



}
