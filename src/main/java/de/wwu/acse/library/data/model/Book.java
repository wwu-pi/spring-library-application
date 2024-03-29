package de.wwu.acse.library.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a book. Fields with get- and set methods are
 * automatically mapped to a corresponding database column. Annotations are used
 * to define the primary key and relations to other entities.
 *
 * @author Herbert Kuchen, Henning Heitkoetter
 * @version 1.1
 */
@Entity
public class Book extends Medium implements java.io.Serializable {
	private static final long serialVersionUID = 4965400399083190672L;
	// only basic checks
	@Pattern(regexp = "[0-9X]*", message = "ISBN - illegal character (only digits or X allowed)")
	@Size(min = 10, message = "ISBN too short (at least 10 characters required)")
	@Column(unique = true)
	protected String isbn;
	protected String author;

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String nr) {
		this.isbn = nr;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String a) {
		this.author = a;
	}

	@Override
	public String toString() {
		return getTitle() + " (ID=" + getId() + (getAuthor() != null ? ", " + getAuthor() : "")
				+ (getIsbn() != null ? ", " + getIsbn() : "") + ")";
	}
}
