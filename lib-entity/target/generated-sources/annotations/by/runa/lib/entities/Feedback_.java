package by.runa.lib.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Feedback.class)
public abstract class Feedback_ extends by.runa.lib.entities.AEntity_ {

	public static volatile SingularAttribute<Feedback, Book> book;
	public static volatile SingularAttribute<Feedback, Integer> rating;
	public static volatile SingularAttribute<Feedback, String> comment;
	public static volatile SingularAttribute<Feedback, String> userName;
	public static volatile SingularAttribute<Feedback, String> bookName;
	public static volatile SingularAttribute<Feedback, User> user;

	public static final String BOOK = "book";
	public static final String RATING = "rating";
	public static final String COMMENT = "comment";
	public static final String USER_NAME = "userName";
	public static final String BOOK_NAME = "bookName";
	public static final String USER = "user";

}

