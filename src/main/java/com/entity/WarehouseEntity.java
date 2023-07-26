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
@Table(name = "WAREHOUSE_TBL")
public class WarehouseEntity  extends BaseEntity {
	
	private static final long serialVersionUID = -8889516780169331366L;

	@Id
	@Column(name = "ID", nullable = false, columnDefinition = "NUMERIC(16,0)")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "NAME", nullable = false)
    private String name;
 
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    
    @Column(name = "REGION_NAME", nullable = false)
    private String regionName;
    
    @Column(name = "CITY_NAME", nullable = false)
    private String cityName;
    
    @SuppressWarnings("unchecked")
	public Class<?> getEntityClass() {
		return WarehouseEntity.class;
	}
	
    public WarehouseEntity() {}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WarehouseEntity [id=" + id + ", name=" + name + ", address=" + address + ", regionName=" + regionName+ ", cityName=" + cityName + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WarehouseEntity other = (WarehouseEntity) obj;
		return Objects.equals(address, other.address) && Objects.equals(cityName, other.cityName)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(regionName, other.regionName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address, cityName, id, name, regionName);
		return result;
	}

}