package com.runa.lib.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode (callSuper = true)
@Table(name = "nitification")
public class Notification extends AEntity {
	private String announcement;
}
