package by.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.service.IDepartmentService;

@RestController
@RequestMapping("/departments/")
public class DepartmentController {

	private static final String ID = "{id}";

	@Autowired
	IDepartmentService departmentService;

	@GetMapping
	public ModelAndView getAllDepartments() {
		ModelAndView modelAndView = new ModelAndView();
		List<DepartmentDto> departments = departmentService.getAllDepartments();
		modelAndView.setViewName("alldepartments");
		modelAndView.addObject("departmentList", departments);
		return modelAndView;
	}

	@GetMapping(value = "adddepartment")
	public ModelAndView addDepartment() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("adddepartment");
		return modelAndView.addObject("dto", new DepartmentDto());
	}

	@PostMapping(value = "adddepartment")
	public ModelAndView addDepartmentSubmit(DepartmentDto departmentDto) {
		ModelAndView modelAndView = new ModelAndView();
		DepartmentDto newdepartment = departmentService.createDepartment(departmentDto);
		modelAndView.setViewName("department");
		return modelAndView.addObject("newdepartment", newdepartment);
	}

	@PutMapping(value = ID)
	public ModelAndView updateDepartment(@PathVariable Long id, DepartmentDto departmentDto) {
		ModelAndView modelAndView = new ModelAndView();
		DepartmentDto updatedDepartment = departmentService.updateDepartment(id, departmentDto);
		modelAndView.setViewName("department");
		return modelAndView.addObject("department", updatedDepartment);
	}

	@GetMapping(value = ID)
	public ModelAndView getDepartment(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			DepartmentDto department = departmentService.getDepartmentById(id);
			modelAndView.setViewName("department");
			modelAndView.addObject("department", department);
		} catch (Exception e) {
			modelAndView.setViewName("403");
			//TODO There is no department with id="id"
		}
		return modelAndView;
	}

	@DeleteMapping(value = ID)
	public ModelAndView deleteDepartment(@PathVariable Long id) {
		departmentService.deleteDepartmentById(id);
		return getAllDepartments();
	}
}
