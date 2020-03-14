package com.runa.lib.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.runa.lib.enums.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
@EqualsAndHashCode(callSuper = true)
public class Feedback extends AEntity {

	private Long bookId;
	private Long userId;

	@Enumerated(EnumType.STRING)
	private Rating rating;

	private String userName;
	private String comment;

}