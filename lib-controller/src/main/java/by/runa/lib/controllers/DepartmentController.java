package by.runa.lib.controllers;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IDepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/departments/")
public class DepartmentController {

    private static final String ERRORS = "errors";
    private static final String MESSAGE = "message";
    private static final String DEPARTMENT = "department";

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

    @GetMapping("{id}")
    public ModelAndView getDepartmentById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            DepartmentDto department = departmentService.getDepartmentById(id);
            modelAndView.setViewName("onedepartment");
            modelAndView.addObject(DEPARTMENT, department);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
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
        return modelAndView.addObject(DEPARTMENT, newdepartment);
    }

    @GetMapping("edit/{id}")
    public ModelAndView getDepartmentEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            DepartmentDto departmentDto = departmentService.getDepartmentById(id);
            modelAndView.setViewName("updatedepartment");
            modelAndView.addObject(DEPARTMENT, departmentDto);
            modelAndView.addObject("dto", new DepartmentDto());
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveDepartmentChanges(@PathVariable Long id, DepartmentDto departmentDto) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            DepartmentDto departmentUpdated = departmentService.updateDepartment(id, departmentDto);
            modelAndView.addObject(DEPARTMENT, departmentUpdated);
            modelAndView.setViewName("changesSaved");
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteDepartment(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            DepartmentDto departmentDto = departmentService.getDepartmentById(id);
            modelAndView.addObject(DEPARTMENT, departmentDto);
            modelAndView.setViewName("deletedepartment");
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("delete/{id}")
    public ModelAndView deletedepartmentSubmit(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return getAllDepartments();
    }
}
