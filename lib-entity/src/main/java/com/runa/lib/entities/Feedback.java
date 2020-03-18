package com.runa.lib.entities;

import javax.persistence.Column;
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

	@Column(name = "book_id")
	private Long bookId;
	@Column(name = "user_id")
	private Long userId;

	@Enumerated(EnumType.STRING)
	private Rating rating;
	@Column(name = "user_name")
	private String userName;
	private String comment;

}