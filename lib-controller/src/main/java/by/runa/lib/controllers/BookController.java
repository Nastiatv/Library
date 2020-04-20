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

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.api.service.IDepartmentService;

@RestController
@RequestMapping("/books/")
public class BookController {

	private static final String ID = "{id}";

	@Autowired
	IBookService bookService;
	
	@Autowired
	IDepartmentService departmentService;

	@GetMapping
	public ModelAndView getAllBooks() {
		ModelAndView modelAndView = new ModelAndView();
		List<BookDto> books = bookService.getAllBooks();
		modelAndView.setViewName("allbooks");
		modelAndView.addObject("bookList", books);
		return modelAndView;
	}

	@GetMapping(value = "addbook")
	public ModelAndView addBook() {
		ModelAndView modelAndView = new ModelAndView();
		List<DepartmentDto> departments=departmentService.getAllDepartments();
		modelAndView.addObject("departmentsList", departments);
		modelAndView.setViewName("addbook");
		modelAndView.addObject("departmentdto", new DepartmentDto());
		return modelAndView.addObject("bookdto", new BookDto());
	}

	@PostMapping(value = "addbook")
	public ModelAndView addBookSubmit(BookDto bookDto, DepartmentDto departmentDto) {
		ModelAndView modelAndView = new ModelAndView();
		BookDto newbook = bookService.createBook(bookDto, departmentDto);
		modelAndView.setViewName("onebook");
		return modelAndView.addObject("book", newbook);
	}

	@PutMapping(value = ID)
	public ModelAndView updateBook(@PathVariable Long id, BookDto bookDto) {
		ModelAndView modelAndView = new ModelAndView();
		BookDto updatedBook = bookService.updateBook(id, bookDto);
		modelAndView.setViewName("book");
		return modelAndView.addObject("book", updatedBook);
	}

	@GetMapping(value = ID)
	public ModelAndView getBook(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			BookDto book = bookService.getBookById(id);
			modelAndView.setViewName("onebook");
			modelAndView.addObject("book", book);
		} catch (Exception e) {
			modelAndView.setViewName("403");
			//TODO There is no book with id="id"
		}
		return modelAndView;
	}

	@DeleteMapping(value = ID)
	public ModelAndView deleteBook(@PathVariable Long id) {
		bookService.deleteBookById(id);
		return getAllBooks();
	}
}
