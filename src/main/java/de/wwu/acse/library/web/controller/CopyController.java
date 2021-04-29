package de.wwu.acse.library.web.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import de.wwu.acse.library.data.model.Book;
import de.wwu.acse.library.service.BookService;
import de.wwu.acse.library.service.CopyService;

@Controller
@RequestMapping("/copies")
public class CopyController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CopyService copyService;

	@GetMapping("/{bookid}")
	public String edit(@PathVariable int bookid, Model model) {
		try {
			Book b = bookService.getBook(bookid);
			model.addAttribute("book", b);
			return "copies/details";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}

	@GetMapping("/{bookid}/create")
	public String create(@PathVariable int bookid, Model model) {
		try {
			Book b = bookService.getBook(bookid);
			model.addAttribute("book", b);
			copyService.createCopy(b);
			return "redirect:/copies/{bookid}";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}
}