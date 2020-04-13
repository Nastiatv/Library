package by.runa.lib.controllers;

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
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.api.service.IRoleService;

@RestController
@RequestMapping(value = "/roles/")
public class RoleController {

	private static final String ID = "{id}";

	@Autowired
	IRoleService roleService;

	@GetMapping
	public ModelAndView getAllroles() {
		ModelAndView modelAndView = new ModelAndView();
		List<RoleDto> roles = roleService.getAllRoles();
		modelAndView.setViewName("allroles");
		modelAndView.addObject("rolesList", roles);
		return modelAndView;
	}

	@GetMapping(value = "addrole")
	public ModelAndView addRole() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addrole");
		return modelAndView.addObject("dto", new RoleDto());
	}

	@PostMapping(value = "addrole")
	public ModelAndView addRoleSubmit(RoleDto roleDto) {
		ModelAndView modelAndView = new ModelAndView();
		RoleDto newrole = roleService.createRole(roleDto);
		modelAndView.setViewName("role");
		return modelAndView.addObject("newrole", newrole);
	}

	@PutMapping(value = ID)
	public ModelAndView updateRole(@PathVariable Long id, RoleDto roleDto) {
		ModelAndView modelAndView = new ModelAndView();
		RoleDto updatedRole = roleService.updateRole(id, roleDto);
		modelAndView.setViewName("role");
		return modelAndView.addObject("role", updatedRole);
	}

	@GetMapping(value = ID)
	public ModelAndView getRole(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			RoleDto role = roleService.getRoleById(id);
			modelAndView.setViewName("role");
			modelAndView.addObject("role", role);
		} catch (Exception e) {
			modelAndView.setViewName("403");
			//TODO There is no role with id="id"
		}
		return modelAndView;
	}

	@DeleteMapping(value = ID)
	public ModelAndView deleteRole(@PathVariable Long id) {
		roleService.deleteRoleById(id);
		return getAllroles();
	}
}
