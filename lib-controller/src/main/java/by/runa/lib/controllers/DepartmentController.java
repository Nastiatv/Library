package by.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.service.IDepartmentService;

@RestController
@RequestMapping("/departments/")
public class DepartmentController {

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
		List<DepartmentDto> departments = departmentService.getAllDepartments();
		modelAndView.addObject("departmentsList", departments);
		modelAndView.setViewName("adddepartment");
		return modelAndView.addObject("departmentdto", new DepartmentDto());
	}

	@PostMapping(value = "adddepartment")
	public ModelAndView addDepartmentSubmit(DepartmentDto departmentDto) {
		ModelAndView modelAndView = new ModelAndView();
		DepartmentDto newdepartment = departmentService.createDepartment(departmentDto);
		modelAndView.setViewName("onedepartment");
		return modelAndView.addObject("department", newdepartment);
	}

	@GetMapping("edit/{id}")
	public ModelAndView getDepartmentEditForm(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			DepartmentDto departmentDto = departmentService.getDepartmentById(id);
			modelAndView.setViewName("updatedepartment");
			modelAndView.addObject("department", departmentDto);
			modelAndView.addObject("dto", new DepartmentDto());
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no department with id="id"
		}
		return modelAndView;
	}

	@PostMapping("edit/{id}")
	public ModelAndView saveDepartmentChanges(@PathVariable Long id, DepartmentDto departmentDto) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			DepartmentDto departmentUpdated = departmentService.updateDepartment(id, departmentDto);
			modelAndView.addObject("department", departmentUpdated);
			modelAndView.setViewName("changesSaved");
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no department with id="id"
		}
		return modelAndView;
	}

	@GetMapping("delete/{id}")
	public ModelAndView deleteDepartment(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			DepartmentDto departmentDto = departmentService.getDepartmentById(id);
			modelAndView.addObject("department", departmentDto);
			modelAndView.setViewName("deletedepartment");
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no department with id="id"
		}
		return modelAndView;
	}

	@PostMapping("delete/{id}")
	public ModelAndView deletedepartmentSubmit(@PathVariable Long id) {
		departmentService.deleteDepartmentById(id);
		return getAllDepartments();
	}
}
