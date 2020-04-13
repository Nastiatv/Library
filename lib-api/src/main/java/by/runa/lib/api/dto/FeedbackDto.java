package by.runa.lib.api.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import by.runa.lib.enums.Rating;
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

	@Enumerated(EnumType.STRING)
	private Rating rating;

	private String userName;
	private String comment;

}
