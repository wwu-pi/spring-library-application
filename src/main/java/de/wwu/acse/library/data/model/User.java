package de.wwu.acse.library.data.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entity representing a user of the system. Fields with get- and set methods
 * are automatically mapped to a corresponding database column. Annotations are
 * used to define the primary key and relations to other entities.
 * 
 * @author Herbert Kuchen, Henning Heitkoetter
 * @version 1.1
 */
@Entity
public class User implements UserDetails, java.io.Serializable {
	private static final long serialVersionUID = -6091824661950209090L;
	/** Primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int uid;
	@NotBlank
	@Column(unique = true)
	protected String username;
	protected String address;
	@NotBlank
	private String password;
	@OneToMany(
			// A user can not be deleted if he has pending loans.
			cascade = { DETACH, MERGE, PERSIST, REFRESH }, 
			// The responsible/owner side will be Loan
			mappedBy = "user", 
			// If we would not use eager fetching, we might have problems while "adding" this 
			// collection to a Thymeleaf template. We can avoid eager fetching by using Dtos or 
			// enforcing to load (by accessing the values) before adding the element to a template
			fetch = FetchType.EAGER)
	protected Collection<Loan> loans = new ArrayList<Loan>();

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Collection<Loan> getLoans() {
		return Collections.unmodifiableCollection(this.loans);
	}

	protected void setLoans(Collection<Loan> loans) {
		this.loans = loans;
	}

	protected void addLoan(Loan loan) {
		this.loans.add(loan);
	}

	protected void removeLoan(Loan loan) {
		this.loans.remove(loan);
	}

	@Override
	public String toString() {
		return username + " (UID=" + getUid() + (getAddress() != null ? ", " + getAddress() : "") + ")";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
