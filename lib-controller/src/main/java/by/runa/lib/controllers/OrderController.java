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

import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.service.IOrderService;

@RestController
@RequestMapping("/orders/")
public class OrderController {

	private static final String ID = "{id}";

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

	@GetMapping(value = "addorder")
	public ModelAndView addOrder() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addorder");
		return modelAndView.addObject("dto", new OrderDto());
	}

	@PostMapping(value = "addorder")
	public ModelAndView addOrderSubmit(OrderDto orderDto) {
		ModelAndView modelAndView = new ModelAndView();
		OrderDto neworder = orderService.createOrder(orderDto);
		modelAndView.setViewName("order");
		return modelAndView.addObject("neworder", neworder);
	}

	@PutMapping(value = ID)
	public ModelAndView updateOrder(@PathVariable Long id, OrderDto orderDto) {
		ModelAndView modelAndView = new ModelAndView();
		OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
		modelAndView.setViewName("order");
		return modelAndView.addObject("order", updatedOrder);
	}

	@GetMapping(value = ID)
	public ModelAndView getOrder(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto order = orderService.getOrderById(id);
			modelAndView.setViewName("order");
			modelAndView.addObject("order", order);
		} catch (Exception e) {
			modelAndView.setViewName("403");
			//TODO There is no order with id="id"
		}
		return modelAndView;
	}

	@DeleteMapping(value = ID)
	public ModelAndView deleteOrder(@PathVariable Long id) {
		orderService.deleteOrderById(id);
		return getAllOrders();
	}
}
