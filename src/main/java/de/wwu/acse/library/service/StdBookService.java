package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wwu.acse.library.data.model.Book;
import de.wwu.acse.library.data.repo.BookRepository;

@Service
public class StdBookService implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Book getBook(int bookId) throws NoSuchElementException {
		Optional<Book> possiblyStoredBook = bookRepository.findById(bookId);
		if (possiblyStoredBook.isEmpty()) {
			throw new NoSuchElementException("Book with given ID does not exist");
		} else {
			return possiblyStoredBook.get();
		}
	}

	@Override
	public Collection<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book update(Book book) throws NoSuchElementException {
		Book toUpdate = getBook(book.getId());
		if (book.getAuthor() != null) {
			toUpdate.setAuthor(book.getAuthor());
		}
		if (book.getIsbn() != null) {
			toUpdate.setIsbn(book.getIsbn());
		}
		if (book.getTitle() != null) {
			toUpdate.setTitle(book.getTitle());
		}
		return bookRepository.save(toUpdate);
	}

	@Override
	public void remove(int bookId) {
		bookRepository.deleteById(bookId);
	}

}
