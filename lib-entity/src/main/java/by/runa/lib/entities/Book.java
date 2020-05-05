package by.runa.lib.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "book")
public class Book extends AEntity {

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "quantity_available")
	private int quantityAvailable = 0;

	@Column(name = "quantity_in_library")
	private int quantityInLibrary = 0;

	@Column(name = "rating")
	private Double rating;

	@ManyToMany
	@JoinTable(name = "department_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
	private List<Department> departments;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Feedback> feedbacks;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	private BookDetails bookDetails;

}
