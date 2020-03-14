package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	private String email;
	private String password;
	private String name;

	public static List<UserDto> convertList(List<User> entities) {
		List<UserDto> listDto = new ArrayList<>();
		for (User User : entities) {
			UserDto dto = new UserDto();
			dto.setId(User.getId());
			dto.setEmail(User.getEmail());
			dto.setPassword(User.getPassword());
			dto.setName(User.getName());
			listDto.add(dto);
		}
		return listDto;
	}

	public static UserDto entityToDto(User entity) {
		UserDto dto = new UserDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			dto.setPassword(entity.getPassword());
			dto.setName(entity.getName());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static User dtoToEntity(UserDto dto) {
		User entity = new User();
		entity.setId(dto.getId());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setName(dto.getName());
		return entity;
	}
}
