package com.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ENVANTER_TBL", schema = "envanterDB" )
@NamedQueries({
@NamedQuery(name="EnvanterEntity.findEnvanterUrunAll", query="SELECT x FROM EnvanterEntity x WHERE x.urunId=?1"),
})
public class EnvanterEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -4667716379870493094L;
	
	public final static String FIND_ENVANTER_URUN_ALL	=	"EnvanterEntity.findEnvanterUrunAll";

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "URUN_ID", nullable = false)
    private Long urunId;

    @Column(name = "DEPO_ID", nullable = false)
    private String depoId;
    
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
		return EnvanterEntity.class;
	}
	
    public EnvanterEntity() {
	}
    
	public EnvanterHstryEntity createAutomaticHistory(){
		EnvanterHstryEntity result = new EnvanterHstryEntity();
		 
		 result.setEnvanterId(this.getId());
		 
		 result.setUrunId(this.getUrunId());
		 result.setDepoId(this.getDepoId());
		 result.setGirisTrh(this.getGirisTrh());
		 result.setCikisTrh(this.getCikisTrh());
		 result.setAdet(this.getAdet());
		
		 return result;
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
		EnvanterEntity other = (EnvanterEntity) obj;
		return Objects.equals(adet, other.adet) && Objects.equals(cikisTrh, other.cikisTrh)
				&& Objects.equals(depoId, other.depoId) && Objects.equals(girisTrh, other.girisTrh)
				&& Objects.equals(id, other.id) && Objects.equals(urunId, other.urunId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adet, cikisTrh, depoId, girisTrh, id, urunId);
		return result;
	}

	@Override
	public String toString() {
		return "EnvanterEntity [id=" + id + ", urunId=" + urunId + ", depoId=" + depoId + ", girisTrh=" + girisTrh
				+ ", cikisTrh=" + cikisTrh + ", adet=" + adet + "]";
	}


}
