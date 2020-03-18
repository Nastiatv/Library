package com.runa.lib.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "order_book")
public class Order extends AEntity {

	@Column(name = "user_id")
	private Long userId;
	@Column(name = "book_id")
	private Long bookId;
	@Column(name = "notification_id")
	private Long notificationId;
	@Column(name = "order_date")
	private Date orderDate;
	@Column(name = "due_date")
	private Date dueDate;
	@Column(name = "is_finished")
	private boolean isFinished;
	private boolean prolongation;

}
