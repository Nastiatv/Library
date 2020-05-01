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
		OrderDto neworder = orderService.createOrder(id, userName);
		modelAndView.setViewName("oneorder");
		return modelAndView.addObject("order", neworder);
	}

	@GetMapping("edit/{id}")
	public ModelAndView getOrderEditForm(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto orderDto = orderService.getOrderById(id);
			modelAndView.setViewName("updateorder");
			modelAndView.addObject("order", orderDto);
			modelAndView.addObject("dto", new OrderDto());
		} catch (Exception e) {
			modelAndView.setViewName("403");
			// TODO There is no order with id="id"
		}
		return modelAndView;
	}

	@PostMapping("edit/{id}")
	public ModelAndView saveOrderChanges(@PathVariable Long id, OrderDto orderDto) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto orderUpdated = orderService.updateOrder(id, orderDto);
			modelAndView.addObject("order", orderUpdated);
			modelAndView.setViewName("oneorder");
		} catch (Exception e) {
			modelAndView.setViewName("403");
			// TODO There is no order with id="id"
		}
		return modelAndView;
	}
}
