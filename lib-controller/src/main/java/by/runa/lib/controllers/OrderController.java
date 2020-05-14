package by.runa.lib.controllers;

import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.exceptions.IsAlreadyClosedException;
import by.runa.lib.api.exceptions.IsAlreadyProlongedException;
import by.runa.lib.api.exceptions.NoBooksAvailableException;
import by.runa.lib.api.service.IOrderService;
import by.runa.lib.api.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders/")
public class OrderController {

    private static final String ERRORS = "errors";
    private static final String MESSAGE = "message";
    private static final String ORDER = "order";

    @Autowired
    IOrderService orderService;

    @Autowired
    IUserService userService;

    @GetMapping
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        List<OrderDto> orders = orderService.getAllOrders();
        modelAndView.setViewName("allorders");
        modelAndView.addObject("orderList", orders);
        return modelAndView;
    }

    @GetMapping("my")
    public ModelAndView getMyOrders(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        final String currentUser = principal.getName();
        try {
            long principalId = userService.getUserByName(currentUser).getId();
            List<OrderDto> orders = orderService.getAllOrdersByUserId(principalId);
            modelAndView.setViewName("allorders");
            modelAndView.addObject("orderList", orders);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("{id}")
	public ModelAndView getOrderById(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			OrderDto order = orderService.getOrderById(id);
			modelAndView.setViewName("oneorder");
			modelAndView.addObject(ORDER, order);
		} catch (EntityNotFoundException e) {
			modelAndView.setViewName(ERRORS);
			modelAndView.addObject(MESSAGE, e.getMessage());
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
        try {
            OrderDto neworder = orderService.createOrder(id, userName);
            modelAndView.setViewName("thanksfororder");
            modelAndView.addObject(ORDER, neworder);
        } catch (NoBooksAvailableException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("prolong/{id}")
    public ModelAndView editProlongInOrder(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            OrderDto orderDto = orderService.prolongOrder(id);
            modelAndView.setViewName("orderProlong");
            modelAndView.addObject(ORDER, orderDto);
        } catch (IsAlreadyProlongedException | IsAlreadyClosedException | EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("return/{id}")
    public ModelAndView closeOrder(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            OrderDto orderDto = orderService.closeOrder(id);
            modelAndView.setViewName("orderClose");
            modelAndView.addObject(ORDER, orderDto);
        } catch (IsAlreadyClosedException | EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }
}
