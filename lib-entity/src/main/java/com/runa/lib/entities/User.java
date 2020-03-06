package com.runa.lib.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode (callSuper = true)
@Table(name="user")
public class User extends AEntity {
	
	private String email;
	private String password;
	private String name;
	

}
