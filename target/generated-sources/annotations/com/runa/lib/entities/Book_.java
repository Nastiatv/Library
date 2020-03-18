package com.runa.hotel.entities;

import javax.annotation.Generated;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.runa.lib.enums.Department;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ extends com.runa.hotel.entities.AEntity_ {

	public static volatile ListAttribute<Book, String> isbn;
	public static volatile ListAttribute<Book, String> isOccupied;
	public static volatile ListAttribute<Book, Integer> quantity;
	public static volatile ListAttribute<Book, Double> rating;
	public static volatile ListAttribute<Book, Department> department;

	public static final String ISBN = "isbn";
	public static final String IS_OCCUPIED = "isOccupied";
	public static final String QUANTITY = "quantity";
	public static final String RATING = "rating";
	public static final String DEPARTMENT = "department";
}
