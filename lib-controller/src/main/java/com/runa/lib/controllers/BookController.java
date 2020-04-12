package com.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.runa.lib.api.dto.BookDto;
import com.runa.lib.api.service.IBookService;

@RestController
@RequestMapping(value = "/books/")
public class BookController {

	private static final String ID = "{id}";

	@Autowired
	IBookService bookService;

	@GetMapping
	public List<BookDto> getAllBooks() {
		return bookService.getAllBooks();
	}

//	@GetMapping
//	public ModelAndView getAllBooks() {
//		ModelAndView modelAndView = new ModelAndView();
//		List<BookDto> books = bookService.getAllBooks();
//		modelAndView.setViewName("books");
//		modelAndView.addObject("bookList", books);
//		return modelAndView;
//	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView createBook(@RequestBody BookDto bookDto) {
		ModelAndView modelAndView = new ModelAndView();
		BookDto book = bookService.createBook(bookDto);
		modelAndView.setViewName("book");
		return modelAndView.addObject("book", book);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
		ModelAndView modelAndView = new ModelAndView();
		BookDto book = bookService.updateBook(id, bookDto);
		modelAndView.setViewName("book");
		return modelAndView.addObject("book", book);
	}

	@GetMapping(value = ID)
	public ModelAndView getBook(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		BookDto book = bookService.getBookById(id);
		modelAndView.setViewName("book");
		return modelAndView.addObject("book", book);
	}

//	@DeleteMapping(value = ID)
//	public ModelAndView deleteBook(@PathVariable Long id) {
//		bookService.deleteBookById(id);
//		return getAllBooks();
//	}
}
