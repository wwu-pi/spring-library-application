package de.wwu.acse.library.data.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Entity representing the loan of a medium. Fields with get- and set methods
 * are automatically mapped to a corresponding database column. Annotations are
 * used to define the primary key and relations to other entities.
 * 
 * @author Herbert Kuchen, Henning Heitkoetter
 * @version 1.1
 */
@Entity
public class Loan implements java.io.Serializable {
	private static final long serialVersionUID = -2329313355806501807L;
	/** Primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;
	@CreationTimestamp
	protected Date date;
	@ManyToOne
	@NotNull
	protected User user;
	@OneToOne
	@NotNull
	protected Copy copy;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		if (this.user != null)
			this.user.removeLoan(this);
		this.user = user;
		if (user != null)
			user.addLoan(this);
	}

	public Copy getCopy() {
		return this.copy;
	}

	public void setCopy(Copy copy) {
		if (this.copy != null)
			this.copy.setLoan(null);
		this.copy = copy;
		if (copy != null)
			copy.setLoan(this);
	}
	
	@PreRemove
	private void prepareRemove() {
		this.setCopy(null);
		this.setUser(null);
	}
}
