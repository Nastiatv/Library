package com.runa.lib.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	private Long id;
	private Long userId;
	private Long bookId;
	private LocalDateTime orderDate;
	private LocalDateTime dueDate;
	private boolean isFinished;
	private boolean prolongation;

}
