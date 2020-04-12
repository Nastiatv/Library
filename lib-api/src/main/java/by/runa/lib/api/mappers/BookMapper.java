package by.runa.lib.api.mappers;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.BookDetails;

@Component
public class BookMapper extends AMapper<Book, BookDto> {

	public BookMapper(ModelMapper mapper) {
		super(Book.class, BookDto.class);
		this.mapper = mapper;
	}

	@PostConstruct
	public void setupMapper() {
		mapper.createTypeMap(Book.class, BookDto.class).addMappings(m -> m.skip(BookDto::setBookDetails))
				.setPostConverter(toDtoConverter());
		mapper.createTypeMap(BookDto.class, Book.class).addMappings(m -> m.skip(Book::setBookDetails))
				.setPostConverter(toEntityConverter());
	}

	@Override
	void mapSpecificFields(Book source, BookDto destination) {
		destination.setBookDetails(getBookDetails(source));
	}

	private BookDetails getBookDetails(Book source) {
		return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getBookDetails();
	}

	@Override
	void mapSpecificFields(BookDto source, Book destination) {
		destination.setBookDetails(source.getBookDetails());
	}

//	public static String convertListDepartmentsToString(List<Department> departments) {
//		String departmentsInString = "";
//		int n = 0;
//		for (Department department : departments) {
//			if (n == 0) {
//				departmentsInString = department.getName();
//				n++;
//			} else {
//				departmentsInString = departmentsInString.concat(", " + department.getName());
//			}
//		}
//		return departmentsInString;
//	}
//
//	public List<Department> convertStringToListDepartments(String departmentsInString) {
//		List<Department> departments = new ArrayList<>();
//		String[] list = departmentsInString.split(", ");
//		for (String dep : list) {
//			departments.add(departmentDao.getAll().stream().filter(c -> c.getName().equals(dep)).findFirst()
//					.orElseThrow(() -> new IllegalArgumentException("Invalid enum number : " + dep)));
//		}
//		return departments;
//	}
}
