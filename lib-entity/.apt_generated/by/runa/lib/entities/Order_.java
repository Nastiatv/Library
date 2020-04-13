package by.runa.lib.entities;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends by.runa.lib.entities.AEntity_ {

	public static volatile SingularAttribute<Order, LocalDateTime> dueDate;
	public static volatile SingularAttribute<Order, Book> book;
	public static volatile SingularAttribute<Order, Boolean> isFinished;
	public static volatile SingularAttribute<Order, Boolean> isProlonged;
	public static volatile SingularAttribute<Order, LocalDateTime> orderDate;
	public static volatile SingularAttribute<Order, User> user;

	public static final String DUE_DATE = "dueDate";
	public static final String BOOK = "book";
	public static final String IS_FINISHED = "isFinished";
	public static final String IS_PROLONGED = "isProlonged";
	public static final String ORDER_DATE = "orderDate";
	public static final String USER = "user";

}

