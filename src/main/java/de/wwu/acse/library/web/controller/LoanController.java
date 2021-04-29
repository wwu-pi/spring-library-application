package de.wwu.acse.library.web.controller;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import de.wwu.acse.library.data.model.Book;
import de.wwu.acse.library.data.model.Loan;
import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.service.BookService;
import de.wwu.acse.library.service.CopyService;
import de.wwu.acse.library.service.LoanService;

@Controller
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CopyService copyService;

	@Autowired
	private LoanService loanService;

	@GetMapping("/{bookid}")
	public String loan(@PathVariable int bookid, Model model) {
		try {
		Book b = bookService.getBook(bookid);
		model.addAttribute("copies", copyService.getAvailableCopiesForMedium(bookid));
		model.addAttribute("book", b);
		return "loan/details";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
		}
	}

	@GetMapping("/loan")
	public String loanCopy(@RequestParam("bookId") int bookId, @RequestParam("copyId") int copyid, @AuthenticationPrincipal User authUser, Model model) {
		try {
			loanService.loan(authUser, copyid);
			return String.format("redirect:/loan/%d", bookId);
		} catch (NoSuchElementException e) { // TODO Be more specific about the exceptions
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Copy not found", e);
		} catch (IllegalArgumentException e) { 
			return "redirect:/loan/" + bookId;
		}
	}
	
	@GetMapping("/loans")
	public String loansOverview(
			@AuthenticationPrincipal User currentUser,
			Model model) {
		Collection<Loan> loans = loanService.getLoansOfUser(currentUser);
		model.addAttribute("loans", loans);
		return "loan/overview";
	}
	
	@GetMapping("/return")
	public String returnLoan(
			@AuthenticationPrincipal User currentUser,
			@RequestParam("loanId") int loanId) {
		try {
			loanService.returnLoan(loanId, currentUser);
			return "redirect:/loan/loans";
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found", e);
		} catch (IllegalArgumentException e) {
			return "redirect:/books";
		}
	}
}