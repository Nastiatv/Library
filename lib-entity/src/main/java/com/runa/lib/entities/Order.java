package com.runa.lib.entities;

import java.util.Date;

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
