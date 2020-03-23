package com.runa.lib.entities;

import java.util.List;

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
@Table(name = "role")
public class Role extends AEntity {

	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "rolies", cascade = CascadeType.ALL)
	private List<User> users;

}