package by.runa.lib.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends by.runa.lib.entities.AEntity_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> name;
	public static volatile ListAttribute<User, Feedback> feedbacks;
	public static volatile ListAttribute<User, Order> orders;
	public static volatile SingularAttribute<User, Department> department;
	public static volatile SingularAttribute<User, String> email;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String FEEDBACKS = "feedbacks";
	public static final String ORDERS = "orders";
	public static final String DEPARTMENT = "department";
	public static final String EMAIL = "email";

}

