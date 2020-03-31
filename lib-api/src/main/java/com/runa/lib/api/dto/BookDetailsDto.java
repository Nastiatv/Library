package com.runa.lib.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailsDto {

	private Long id;
	private String name;
	private String author;
	private String description;
	private String picture;

}
