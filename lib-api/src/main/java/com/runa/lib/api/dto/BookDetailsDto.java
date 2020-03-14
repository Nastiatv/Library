package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.entities.BookDetails;

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
	private String discription;

	public static List<BookDetailsDto> convertList(List<BookDetails> entities) {
		List<BookDetailsDto> listDto = new ArrayList<>();
		for (BookDetails book : entities) {
			BookDetailsDto dto = new BookDetailsDto();
			dto.setId(book.getId());
			dto.setName(book.getName());
			dto.setAuthor(book.getAuthor());
			dto.setDiscription(book.getDiscription());
			listDto.add(dto);
		}
		return listDto;
	}

	public static BookDetailsDto entityToDto(BookDetails entity) {
		BookDetailsDto dto = new BookDetailsDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setAuthor(entity.getAuthor());
			dto.setDiscription(entity.getDiscription());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static BookDetails dtoToEntity(BookDetailsDto dto) {
		BookDetails entity = new BookDetails();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setAuthor(dto.getAuthor());
		entity.setDiscription(dto.getDiscription());
		return entity;
	}
}
