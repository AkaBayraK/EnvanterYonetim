package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchInventoryDTO {
	
	private Long productId;
	private Long categoryId;
	private Long siloId;
	private String regionName;
	private String cityName;

}
