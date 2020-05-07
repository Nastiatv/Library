package by.runa.lib.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.service.IOrderService;
import by.runa.lib.exceptions.IsAlreadyClosedException;
import by.runa.lib.exceptions.IsAlreadyProlongedException;
import by.runa.lib.exceptions.NoBooksAvailableException;

@RestController
@RequestMapping("/orders/")
public class OrderController {

	@Autowired
	IOrderService orderService;

	@GetMapping
	public ModelAndView getAllOrders() {
		ModelAndView modelAndView = new ModelAndView();
		List<OrderDto> orders = orderService.getAllOrders();
		modelAndView.setViewName("allorders");
		modelAndView.addObject("orderList", orders);
		return modelAndView;
	}

	@GetMapping("{id}")
	public ModelAndView getOrderById(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto order = orderService.getOrderById(id);
			modelAndView.setViewName("oneorder");
			modelAndView.addObject("order", order);
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no order with id="id"
		}
		return modelAndView;
	}

	@GetMapping(value = "addorder/{id}")
	public ModelAndView addOrder(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addorder");
		return modelAndView;
	}

	@PostMapping(value = "addorder/{id}")
	public ModelAndView addOrderSubmit(@PathVariable Long id, Principal principal) {
		final String userName = principal.getName();
		ModelAndView modelAndView = new ModelAndView();
		OrderDto neworder = null;
		try {
			neworder = orderService.createOrder(id, userName);
			modelAndView.setViewName("thanksfororder");
			modelAndView.addObject("order", neworder);
		} catch (NoBooksAvailableException e) {
			modelAndView.setViewName("errors/noBooksAvailable");
		}
		return modelAndView;
	}

	@GetMapping("prolong/{id}")
	public ModelAndView editProlongInOrder(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto orderDto = orderService.prolongOrder(id);
			modelAndView.setViewName("orderProlong");
			modelAndView.addObject("order", orderDto);
		} catch (IsAlreadyProlongedException e) {
			modelAndView.setViewName("errors/isAlreadyProlonged");
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no order with id="id"
		}
		return modelAndView;
	}

	@GetMapping("return/{id}")
	public ModelAndView closeOrder(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto orderDto = orderService.closeOrder(id);
			modelAndView.setViewName("orderClose");
			modelAndView.addObject("order", orderDto);
		} catch (IsAlreadyClosedException e) {
			modelAndView.setViewName("errors/isAlreadyClosed");
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no order with id="id"
		}
		return modelAndView;
	}
}
