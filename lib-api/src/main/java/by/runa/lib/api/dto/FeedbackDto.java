package by.runa.lib.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeedbackDto extends ADto {

	private Long bookId;
	private Long userId;

	private int rating;

	private String userName;
	private String comment;

}
