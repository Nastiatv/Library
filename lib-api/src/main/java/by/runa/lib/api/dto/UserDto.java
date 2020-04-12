package by.runa.lib.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ADto {

	private String email;
	private String password;
	private String name;
	private Long departmentId;
	private String departmentName;

}
