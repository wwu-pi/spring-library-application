package de.wwu.acse.library.data.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

/**
 * Entity representing a copy of a medium which can be borrowed. Fields with
 * get- and set methods are automatically mapped to a corresponding database
 * column. Annotations are used to define the primary key and relations to other
 * entities.
 * 
 * @author Herbert Kuchen, Henning Heitkoetter
 * @version 1.1
 */
@Entity
public class Copy implements java.io.Serializable {
	private static final long serialVersionUID = 2133698994451972839L;
	/** primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int inventoryNo;
	@ManyToOne
	@NotNull
	protected Medium medium;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "copy", fetch = FetchType.EAGER)
	protected Loan loan;

	public int getInventoryNo() {
		return this.inventoryNo;
	}

	public void setInventoryNo(int inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public Medium getMedium() {
		return this.medium;
	}

	public void setMedium(Medium medium) {
		if (this.medium != null)
			this.medium.removeCopy(this);
		this.medium = medium;
		if (medium != null)
			medium.addCopy(this);
	}

	public Loan getLoan() {
		return this.loan;
	}

	protected void setLoan(Loan loan) {
		this.loan = loan;
	}
}
