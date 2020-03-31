package com.runa.lib.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
@Table(name = "department")
public class Department extends AEntity {

	@ManyToMany(mappedBy = "departments", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Book> books;

	private String name;

}
