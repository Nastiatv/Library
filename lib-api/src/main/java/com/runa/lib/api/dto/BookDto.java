package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.runa.lib.entities.Book;
import com.runa.lib.enums.Department;

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
	@Enumerated(EnumType.STRING)
	private Department department;

	public static List<BookDto> convertList(List<Book> entities) {
		List<BookDto> listDto = new ArrayList<>();
		for (Book book : entities) {
			BookDto dto = new BookDto();
			dto.setId(book.getId());
			dto.setIsbn(book.getIsbn());
			dto.setOccupied(book.isOccupied());
			dto.setQuantity(book.getQuantity());
			dto.setRating(book.getRating());
			dto.setDepartment(book.getDepartment());
			listDto.add(dto);
		}
		return listDto;
	}

	public static BookDto entityToDto(Book entity) {
		BookDto dto = new BookDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setIsbn(entity.getIsbn());
			dto.setOccupied(entity.isOccupied());
			dto.setQuantity(entity.getQuantity());
			dto.setRating(entity.getRating());
			dto.setDepartment(entity.getDepartment());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Book dtoToEntity(BookDto dto) {
		Book entity = new Book();
		entity.setId(dto.getId());
		entity.setIsbn(dto.getIsbn());
		entity.setOccupied(dto.isOccupied());
		entity.setQuantity(dto.getQuantity());
		entity.setRating(dto.getRating());
		entity.setDepartment(dto.getDepartment());
		return entity;
	}
}
