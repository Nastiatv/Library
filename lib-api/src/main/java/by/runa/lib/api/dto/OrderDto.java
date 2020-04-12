package by.runa.lib.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends ADto {

	private Long userId;
	private Long bookId;
	private LocalDateTime orderDate;
	private LocalDateTime dueDate;
	private boolean isFinished;
	private boolean prolongation;

}
