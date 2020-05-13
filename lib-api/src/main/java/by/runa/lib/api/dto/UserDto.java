package by.runa.lib.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ADto {

    private String email;
    private String password;
    private String username;
    private Long departmentId;
    private String departmentName;

}
