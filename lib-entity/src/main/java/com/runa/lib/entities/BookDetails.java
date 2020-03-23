package com.runa.lib.entities;

import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
@Table(name = "book_details")
public class BookDetails extends AEntity {

	private String name;
	private String author;
	private String picture;
	private String description;

	@OneToOne
	@MapsId
	private Book book;
}
