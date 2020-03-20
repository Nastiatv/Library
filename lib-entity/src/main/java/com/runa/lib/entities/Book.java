package com.runa.lib.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.runa.lib.enums.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "book")
public class Book extends AEntity {

	
	private String isbn;
	@Column(name = "is_occupied")
	private boolean isOccupied;
	private int quantity;
	private Double rating;
	@Enumerated(EnumType.STRING)
	private Set<Department> department;
}
