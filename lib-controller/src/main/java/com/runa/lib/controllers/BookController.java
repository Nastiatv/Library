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

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookDto createBook(@RequestBody BookDto bookDto) {
		return bookService.createBook(bookDto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
		bookService.updateBook(id, bookDto);
	}

	@GetMapping(value = ID)
	public BookDto getBook(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBookById(id);
	}
}
