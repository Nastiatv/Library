package by.runa.lib.utils.mappers;

import org.springframework.stereotype.Component;

import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.entities.BookDetails;

@Component
public class BookDetailsMapper extends AMapper<BookDetails, BookDetailsDto> {

	public BookDetailsMapper() {
		super(BookDetails.class, BookDetailsDto.class);
	}

}
