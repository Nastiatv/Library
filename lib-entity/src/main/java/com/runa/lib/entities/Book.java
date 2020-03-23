package com.runa.lib.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@ElementCollection(targetClass = Department.class)
	@CollectionTable(name = "departments")
	@Column(name = "departments", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Department> departments;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Feedback> feedbacks;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders;

	@OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
	private BookDetails bookDetails;
}
