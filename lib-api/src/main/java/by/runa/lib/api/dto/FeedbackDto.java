package by.runa.lib.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackDto extends ADto {

    private Long bookId;
    private Long userId;

    private int rating;

    private String userName;
    private String comment;

}
