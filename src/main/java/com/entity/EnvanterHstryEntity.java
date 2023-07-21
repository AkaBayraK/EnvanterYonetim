package com.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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
@Table(name = "ENVANTER_HSTRY_TBL", schema = "envanterDB" )
public class EnvanterHstryEntity extends BaseEntity {
	
	private static final long serialVersionUID = -1143298752717035987L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "ENVANTER_ID", nullable = false)
    private Long envanterId;

    @Column(name = "URUN_ID", nullable = false)
    private Long urunId;

    @Column(name = "DEPO_ID", nullable = false)
    private Long depoId;
    
    /* Eğer giriş tarihi dolu ise envantere ürün giri olduğu belirtir*/
    @Column(name = "GIRIS_TRH", nullable = false)
    private Date girisTrh;

    /* Eğer çıkış tarihi dolu ise o üründen ve depodan ürün çıkışı olduğu belirlenir.*/
    @Column(name = "CIKIS_TRH", nullable = false)
    private Date cikisTrh;
    
    @Column(name = "ADET", nullable = false)
    private BigDecimal adet;
    
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return EnvanterHstryEntity.class;
	}
	
    public EnvanterHstryEntity() {
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
		EnvanterHstryEntity other = (EnvanterHstryEntity) obj;
		return Objects.equals(adet, other.adet) && Objects.equals(cikisTrh, other.cikisTrh)
				&& Objects.equals(depoId, other.depoId) && Objects.equals(envanterId, other.envanterId)
				&& Objects.equals(girisTrh, other.girisTrh) && Objects.equals(id, other.id)
				&& Objects.equals(urunId, other.urunId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(adet, cikisTrh, depoId, envanterId, girisTrh, id, urunId);
	}

	@Override
	public String toString() {
		return "EnvanterHstryEntity [id=" + id + ", envanterId=" + envanterId + ", urunId=" + urunId + ", depoId="
				+ depoId + ", girisTrh=" + girisTrh + ", cikisTrh=" + cikisTrh + ", adet=" + adet + "]";
	}


}
