package by.runa.lib.api.dto;

import java.time.LocalDateTime;

import by.runa.lib.entities.Book;
import by.runa.lib.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends ADto {

	private User user;
	private Book book;
	private LocalDateTime orderDate;
	private LocalDateTime dueDate;
	private boolean isFinished;
	private boolean isProlonged;

}
