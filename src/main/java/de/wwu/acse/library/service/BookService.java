package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import de.wwu.acse.library.data.model.Book;

/**
 * Interface for Book management.
 * 
 * @author Henning Heitkoetter
 *
 */
public interface BookService {
	/**
	 * Create a new book in the database with properties as specified by
	 * <code>book</code>.
	 * 
	 * @param book The newly created book has the same property values as this
	 *             parameter.
	 * @return The newly created book.
	 */
	Book createBook(Book book);

	/**
	 * Returns the book with the specified ID.
	 * 
	 * @param bookId The ID of the book.
	 * @return The book.
	 * @throws NoSuchElementException If no book exists for the given ID.
	 */
	Book getBook(int bookId) throws NoSuchElementException;

	/**
	 * Returns the book which was updated by this operation
	 * 
	 * @param book The book the values of which are used to update the existing
	 *             book.
	 * @return The updated book.
	 * @throws NoSuchElementException If no book exists for the given ID.
	 */
	Book update(Book book) throws NoSuchElementException;

	/**
	 * Retrieves all books from stored in the system.
	 * 
	 * @return All books.
	 */
	Collection<Book> getAllBooks();

	/**
	 * Removes the book with the given id.
	 * 
	 * @param bookId The ID of the book.
	 * @return The book.
	 * @throws NoSuchElementException If no book exists for the given ID.
	 */
	void remove(int bookId);
}
