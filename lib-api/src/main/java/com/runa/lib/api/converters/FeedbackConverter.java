package com.runa.lib.api.converters;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.api.dto.FeedbackDto;
import com.runa.lib.entities.Feedback;

public class FeedbackConverter {

	public static List<FeedbackDto> convertList(List<Feedback> entities) {
		List<FeedbackDto> listDto = new ArrayList<>();
		for (Feedback book : entities) {
			FeedbackDto dto = new FeedbackDto();
			dto.setId(book.getId());
			dto.setBookId(book.getBook().getId());
			dto.setUserId(book.getUser().getId());
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
			dto.setBookId(entity.getBook().getId());
			dto.setUserId(entity.getUser().getId());
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
		entity.getBook().setId(dto.getBookId());
		entity.getUser().setId(dto.getUserId());
		entity.setUserName(dto.getUserName());
		entity.setRating(dto.getRating());
		entity.setComment(dto.getComment());
		return entity;
	}
}
