package by.runa.lib.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ extends by.runa.lib.entities.AEntity_ {

	public static volatile SingularAttribute<Book, Integer> quantityAvailable;
	public static volatile SingularAttribute<Book, Integer> quantityInLibrary;
	public static volatile SingularAttribute<Book, BookDetails> bookDetails;
	public static volatile SingularAttribute<Book, String> isbn;
	public static volatile SingularAttribute<Book, Double> rating;
	public static volatile ListAttribute<Book, Feedback> feedbacks;
	public static volatile ListAttribute<Book, Order> orders;
	public static volatile ListAttribute<Book, Department> departments;

	public static final String QUANTITY_AVAILABLE = "quantityAvailable";
	public static final String QUANTITY_IN_LIBRARY = "quantityInLibrary";
	public static final String BOOK_DETAILS = "bookDetails";
	public static final String ISBN = "isbn";
	public static final String RATING = "rating";
	public static final String FEEDBACKS = "feedbacks";
	public static final String ORDERS = "orders";
	public static final String DEPARTMENTS = "departments";

}

