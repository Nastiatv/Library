package com.runa.lib.api.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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

}
