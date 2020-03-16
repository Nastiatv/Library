package com.runa.lib.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.runa.lib.enums.Department;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "book")
public class Book extends AEntity {

	private String isbn;
	private boolean isOccupied;
	private int quantity;
	private double rating;

	@Enumerated(EnumType.STRING)
	private Department department;
}