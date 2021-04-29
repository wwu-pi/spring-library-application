package de.wwu.acse.library.web.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import de.wwu.acse.library.data.model.Book;
import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public String getAllBooks(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("books", bookService.getAllBooks());
		return "books/all";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("book", new Book());
		return "books/create";
	}

	@PostMapping("/create")
	public String create(@Valid Book b, Errors errors) {
		if (errors.hasErrors()) {
			return "books/create";
		}
		bookService.createBook(b);
		return "redirect:/books";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		try {
			Book b = bookService.getBook(id);
			model.addAttribute("book", b);
			return "books/edit";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}

	@PostMapping("/{bookid}/edit")
	public String edit(@Valid @ModelAttribute("book") Book b, BindingResult result,
			@PathVariable("bookid") int bookid) {
		if (result.hasErrors()) {
			return "books/edit";
		}
		try {
			bookService.update(b);
			return "redirect:/books";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}

	@GetMapping("/{bookid}/delete")
	public String delete(@PathVariable("bookid") int bookid) {
		try {
			Book book = bookService.getBook(bookid);
			bookService.remove(book.getId());
			return "redirect:/books";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}

	@GetMapping("/{bookid}")
	public String details(Model model, @PathVariable("bookid") int bookid) {
		try {
			Book book = bookService.getBook(bookid);
			model.addAttribute("book", book);
			return "books/details";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}
}