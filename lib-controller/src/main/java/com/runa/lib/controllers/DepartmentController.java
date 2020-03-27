package com.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runa.lib.api.dto.DepartmentDto;
import com.runa.lib.api.service.IDepartmentService;

@RestController
@RequestMapping(value = "/departments/")
public class DepartmentController {

	private static final String ID = "{id}";

	@Autowired
	IDepartmentService departmentService;

	@GetMapping
	public List<DepartmentDto> getAllDepartment() {
		return departmentService.getAllDepartments();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DepartmentDto addDepartment(@RequestBody DepartmentDto dto) {
		return departmentService.addDepartment(dto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto dto) {
		departmentService.updateDepartment(id, dto);
	}

	@GetMapping(value = ID)
	public DepartmentDto getDepartment(@PathVariable Long id) {
		return departmentService.getDepartmentById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteDepartment(@PathVariable Long id) {
		departmentService.deleteDepartmentById(id);
	}
}
