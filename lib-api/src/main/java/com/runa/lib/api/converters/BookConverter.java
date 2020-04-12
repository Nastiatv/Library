package com.runa.lib.api.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.runa.lib.api.dao.IDepartmentDao;
import com.runa.lib.api.dto.BookDto;
import com.runa.lib.entities.Book;
import com.runa.lib.entities.Department;

@Component
public class BookConverter {

	@Autowired
	private IDepartmentDao departmentDao;

	public static List<BookDto> convertList(List<Book> entities) {
		List<BookDto> listDto = new ArrayList<>();
		for (Book book : entities) {
			BookDto dto = new BookDto();
			dto.setId(book.getId());
			dto.setIsbn(book.getIsbn());
			dto.setOccupied(book.isOccupied());
			dto.setQuantity(book.getQuantity());
			dto.setDepartment(convertListDepartmentsToString(book.getDepartments()));
			dto.setBookDetails(book.getBookDetails());
			listDto.add(dto);
		}
		return listDto;
	}

	public static BookDto entityToDto(Book entity) {
		BookDto dto = new BookDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setIsbn(entity.getIsbn());
			dto.setOccupied(entity.isOccupied());
			dto.setQuantity(entity.getQuantity());
			dto.setDepartment(convertListDepartmentsToString(entity.getDepartments()));
			dto.setBookDetails(entity.getBookDetails());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public Book dtoToEntity(BookDto dto) {
		Book entity = new Book();
		entity.setId(dto.getId());
		entity.setIsbn(dto.getIsbn());
		entity.setOccupied(dto.isOccupied());
		entity.setQuantity(dto.getQuantity());
		entity.setDepartments(convertStringToListDepartments(dto.getDepartment()));
		entity.setBookDetails(dto.getBookDetails());
		return entity;
	}

	public static String convertListDepartmentsToString(List<Department> departments) {
		String departmentsInString = "";
		int n = 0;
		for (Department department : departments) {
			if (n == 0) {
				departmentsInString = department.getName();
				n++;
			} else {
				departmentsInString = departmentsInString.concat(", " + department.getName());
			}
		}
		return departmentsInString;
	}

	public List<Department> convertStringToListDepartments(String departmentsInString) {
		List<Department> departments = new ArrayList<>();
		String[] list = departmentsInString.split(", ");
		for (String dep : list) {
			departments.add(departmentDao.getAll().stream().filter(c -> c.getName().equals(dep)).findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Invalid enum number : " + dep)));
		}
		return departments;
	}
}