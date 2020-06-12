package by.runa.lib.utils.mappers;

import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.entities.BookDetails;

import org.springframework.stereotype.Component;

@Component
public class BookDetailsMapper extends AMapper<BookDetails, BookDetailsDto> {

    public BookDetailsMapper() {
        super(BookDetails.class, BookDetailsDto.class);
    }
}