package com.runa.lib.api.dto;

import com.runa.lib.entities.BookDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

	private Long id;
	private String isbn;
	private boolean isOccupied;
	private int quantity;
	private double rating;
	private String department;
	private BookDetails bookDetails;

}
