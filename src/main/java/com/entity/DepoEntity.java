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
 * Depo tablosu olmalıdır. Deponun adı, adresi, bulunduğu bölge(Akdeniz, Ege ..), ve şehir bilgisi tutulmalıdır. (Bölge ve şehir bilgilerinin tablolarda tutulmasına gerek yoktur.)
 */
@Data
@Entity
@Table(name = "DEPO_TBL", schema = "envanterDB" )
public class DepoEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -6548645720522961455L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "ADI", nullable = false)
    private String adi;
 
    @Column(name = "ADRES", nullable = false)
    private String adres;
    
    @Column(name = "BOLGE_ADI", nullable = false)
    private String bolgeAdi;
    
    @Column(name = "IL_ADI", nullable = false)
    private String ilAdi;
    
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return DepoEntity.class;
	}
	
    public DepoEntity() {
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
		DepoEntity other = (DepoEntity) obj;
		return Objects.equals(adi, other.adi) && Objects.equals(adres, other.adres)
				&& Objects.equals(bolgeAdi, other.bolgeAdi) && Objects.equals(id, other.id)
				&& Objects.equals(ilAdi, other.ilAdi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(adi, adres, bolgeAdi, id, ilAdi);
	}

	@Override
	public String toString() {
		return "DepoEntity [id=" + id + ", adi=" + adi + ", adres=" + adres + ", bolgeAdi=" + bolgeAdi + ", ilAdi="
				+ ilAdi + "]";
	}

}
