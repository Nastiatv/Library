package by.runa.lib.api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends ADto {

	private UserDto userDto;
	private BookDto bookDto;
	private LocalDate orderDate;
	private LocalDate dueDate;
	private boolean finished;
	private boolean prolonged;

}
