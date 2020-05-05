package by.runa.lib.api.dto;

import java.util.List;

import by.runa.lib.entities.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookDto extends ADto {

	private String isbn;
	private int quantityAvailable;
	private int quantityInLibrary;
	private double rating;
	private List<Department> departments;
	private BookDetailsDto bookDetailsDto;

}
