package com.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ENVANTER_TBL", schema = "envanterDB" )
public class EnvanterEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -4667716379870493094L;

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


}
