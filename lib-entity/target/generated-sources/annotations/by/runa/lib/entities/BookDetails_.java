package by.runa.lib.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookDetails.class)
public abstract class BookDetails_ extends by.runa.lib.entities.AEntity_ {

	public static volatile SingularAttribute<BookDetails, String> author;
	public static volatile SingularAttribute<BookDetails, String> name;
	public static volatile SingularAttribute<BookDetails, String> description;
	public static volatile SingularAttribute<BookDetails, String> picture;

	public static final String AUTHOR = "author";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String PICTURE = "picture";

}

