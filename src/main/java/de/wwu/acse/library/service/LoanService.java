package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import de.wwu.acse.library.data.model.Loan;
import de.wwu.acse.library.data.model.User;

/**
 *Interface for Loan management.
 * 
 * @author Henning Heitkoetter
 *
 */
public interface LoanService {

	Collection<Loan> getLoansOfUser(User user);

	void returnLoan(int loanToReturnId, User user) throws NoSuchElementException, IllegalArgumentException;

	void loan(User user, int copyToLoanInvNo) throws NoSuchElementException, IllegalArgumentException;

}
