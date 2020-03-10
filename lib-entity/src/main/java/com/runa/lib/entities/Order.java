package com.runa.lib.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "order")
public class Order extends AEntity {

	private Long userId;
	private Long bookId;
	private Long notificationId;
	private Date orderDate;
	private Date dueDate;
	private boolean isFinished;
	private boolean prolongation;

}
