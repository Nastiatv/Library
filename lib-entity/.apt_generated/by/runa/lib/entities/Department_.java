package by.runa.lib.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ extends by.runa.lib.entities.AEntity_ {

	public static volatile SetAttribute<Department, Book> books;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile ListAttribute<Department, User> users;

	public static final String BOOKS = "books";
	public static final String NAME = "name";
	public static final String USERS = "users";

}

