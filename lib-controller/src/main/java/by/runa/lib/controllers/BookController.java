package by.runa.lib.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.utils.ImgFileUploader;

@RestController
@RequestMapping("/books/")
public class BookController {

	@Autowired
	IBookService bookService;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	ImgFileUploader imgFileUploader;

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

	@GetMapping("edit/{book}")
	public ModelAndView getBookEditForm(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			BookDto bookDto = bookService.getBookById(id);
			List<DepartmentDto> departments = departmentService.getAllDepartments();
			modelAndView.addObject("departmentsList", departments);
			modelAndView.setViewName("updatebook");
			modelAndView.addObject("book", bookDto);
			modelAndView.addObject("departmentdto", new DepartmentDto());
			modelAndView.addObject("dto", new BookDto());
		} catch (Exception e) {
			modelAndView.setViewName("403");
			// TODO There is no book with id="id"
		}
		return modelAndView;
	}

	@PostMapping("edit/{book}")
	public ModelAndView saveBookChanges(BookDto bookDto, DepartmentDto departmentDto,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView();
		BookDto bookUpdated = bookService.updateBook(bookDto, departmentDto);
		try {
			imgFileUploader.createOrUpdate(bookDto, file);
			modelAndView.addObject("book", bookUpdated);
			modelAndView.setViewName("onebook");
		} catch (IOException e) {
			modelAndView.setViewName("403");
		}
		return modelAndView;
	}

	@DeleteMapping("delete/{book}")
	public ModelAndView deleteBook(@PathVariable Long id) {
		bookService.deleteBookById(id);
		return getAllBooks();
	}
}

