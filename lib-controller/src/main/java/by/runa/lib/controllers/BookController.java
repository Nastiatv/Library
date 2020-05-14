package by.runa.lib.controllers;

import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.utils.ImgFileUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books/")
public class BookController {

    private static final String ERRORS = "errors";
    private static final String MESSAGE = "message";
    private static final String BOOK = "book";

    @Autowired
    IBookService bookService;

    @Autowired
    IFeedbackService feedbackService;

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

    @GetMapping("{id}")
    public ModelAndView getBookById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            BookDto book = bookService.getBookById(id);
            List<FeedbackDto> listFeedbacks = feedbackService.getAllFeedbacksByBookId(id);
            modelAndView.setViewName("onebook");
            modelAndView.addObject(BOOK, book);
            modelAndView.addObject("listFeedbacks", listFeedbacks);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = "addbook")
    public ModelAndView addBook() {
        ModelAndView modelAndView = new ModelAndView();
        List<DepartmentDto> departments = departmentService.getAllDepartments();
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

    @GetMapping("edit/{id}")
    public ModelAndView getBookEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            BookDto bookDto = bookService.getBookById(id);
            modelAndView.setViewName("updatebook");
            modelAndView.addObject(BOOK, bookDto);
            modelAndView.addObject("detailsdto", new BookDetailsDto());
            modelAndView.addObject("dto", new BookDto());
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveBookChanges(BookDto bookDto,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            BookDto bookUpdated = bookService.updateBook(bookDto, file);
            imgFileUploader.createOrUpdate(bookDto, file);
            modelAndView.addObject("book", bookUpdated);
            modelAndView.setViewName("changesSaved");
        } catch (IOException | EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteBook(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            BookDto bookDto = bookService.getBookById(id);
            modelAndView.addObject("book", bookDto);
            modelAndView.setViewName("deletebook");
            modelAndView.addObject("departmentdto", new DepartmentDto());
            modelAndView.addObject("bookDto", new BookDto());
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("delete/{id}")
    public ModelAndView deletebookSubmit(BookDto bookDto, DepartmentDto departmentDto) {
        ModelAndView modelAndView = new ModelAndView();
        bookService.deleteBookById(bookDto.getId(), departmentDto);
        modelAndView.setViewName("changesSaved");
        return modelAndView;
    }
}
