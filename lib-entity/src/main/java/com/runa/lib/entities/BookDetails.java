package com.runa.lib.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode (callSuper = true)
@Table(name="book_details")
public class BookDetails extends AEntity{
	
	private String name;
	private String author;
	private String discription;
	

}
