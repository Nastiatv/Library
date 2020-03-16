package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.runa.lib.entities.Feedback;
import com.runa.lib.enums.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

	private Long id;
	private Long bookId;
	private Long userId;

	@Enumerated(EnumType.STRING)
	private Rating rating;

	private String userName;
	private String comment;

	public static List<FeedbackDto> convertList(List<Feedback> entities) {
		List<FeedbackDto> listDto = new ArrayList<>();
		for (Feedback book : entities) {
			FeedbackDto dto = new FeedbackDto();
			dto.setId(book.getId());
			dto.setBookId(book.getBookId());
			dto.setUserId(book.getUserId());
			dto.setUserName(book.getUserName());
			dto.setRating(book.getRating());
			dto.setComment(book.getComment());
			listDto.add(dto);
		}
		return listDto;
	}

	public static FeedbackDto entityToDto(Feedback entity) {
		FeedbackDto dto = new FeedbackDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setBookId(entity.getBookId());
			dto.setUserId(entity.getUserId());
			dto.setUserName(entity.getUserName());
			dto.setRating(entity.getRating());
			dto.setComment(entity.getComment());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Feedback dtoToEntity(FeedbackDto dto) {
		Feedback entity = new Feedback();
		entity.setId(dto.getId());
		entity.setBookId(dto.getBookId());
		entity.setUserId(dto.getUserId());
		entity.setUserName(dto.getUserName());
		entity.setRating(dto.getRating());
		entity.setComment(dto.getComment());
		return entity;
	}
}
