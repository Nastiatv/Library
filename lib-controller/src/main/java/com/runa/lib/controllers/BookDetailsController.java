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

import com.runa.lib.api.dto.BookDetailsDto;
import com.runa.lib.api.service.IBookDetailsService;

@RestController
@RequestMapping(value = "/bookDetails/")
public class BookDetailsController {

	private static final String ID = "{id}";

	@Autowired
	IBookDetailsService bookDetailsService;

	@GetMapping
	public List<BookDetailsDto> getAllBookDetails() {
		return bookDetailsService.getAllBookDetails();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookDetailsDto addBookDetails(@RequestBody BookDetailsDto dto) {
		return bookDetailsService.addBookDetails(dto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateBookDetails(@PathVariable Long id, @RequestBody BookDetailsDto dto) {
		bookDetailsService.updateBookDetails(id, dto);
	}

	@GetMapping(value = ID)
	public BookDetailsDto getBookDetails(@PathVariable Long id) {
		return bookDetailsService.getBookDetailsById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteBookDetails(@PathVariable Long id) {
		bookDetailsService.deleteBookDetailsById(id);
	}
}
