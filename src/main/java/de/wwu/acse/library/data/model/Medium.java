package de.wwu.acse.library.data.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

/**
 * Entity representing an abstract medium. Fields with get- and set methods are
 * automatically mapped to a corresponding database column. Annotations are used
 * to define the primary key and relations to other entities.
 * 
 * @author Herbert Kuchen, Henning Heitkoetter
 * @version 1.1
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Medium implements java.io.Serializable {
	private static final long serialVersionUID = -8542384203222995659L;
	/** Primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;
	@NotBlank(message = "Title required")
	protected String title;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "medium", fetch=FetchType.EAGER)
	protected Collection<Copy> copies = new ArrayList<Copy>();

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Copy> getCopies() {
		return copies;
	}

	protected void setCopies(Collection<Copy> copies) {
		this.copies = copies;
	}

	protected void addCopy(Copy copy) {
		this.copies.add(copy);
	}

	protected void removeCopy(Copy copy) {
		this.copies.remove(copy);
	}
}
