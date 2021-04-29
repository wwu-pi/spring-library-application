package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wwu.acse.library.data.model.Copy;
import de.wwu.acse.library.data.model.Loan;
import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.data.repo.LoanRepository;

@Service
public class StdLoanService implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired // It is better practice not to use the UserRepository directly.
	private UserService userService;

	@Autowired
	private CopyService copyService;
	
	@Override
	public Collection<Loan> getLoansOfUser(User user) {
		return loanRepository.findByUserId(user.getUid());
	}

	@Override
	public void returnLoan(int loanToReturnId, User user) throws NoSuchElementException, IllegalArgumentException {
		Optional<Loan> possiblyStoredLoan = loanRepository.findById(loanToReturnId);
		if (!possiblyStoredLoan.isPresent()) {
			throw new NoSuchElementException("Loan not found");
		}
		Loan storedLoan = possiblyStoredLoan.get();
		if (user.getUid() == storedLoan.getUser().getUid()) {
			// See @PreRemove in Loan!
			loanRepository.delete(storedLoan);
		} else {
			throw new IllegalArgumentException("Argument 'user' and user of 'loanToReturn' do not match");
		}
	}
	
	

	@Override
	public void loan(User user, int copyToLoanInvNo) throws NoSuchElementException, IllegalArgumentException {
		Copy copyToLoan = copyService.getCopy(copyToLoanInvNo);
		if (copyToLoan.getLoan() != null) {
			throw new IllegalArgumentException("This copy is already lent out.");
		}

		User storedUser = userService.getUser(user.getUid());
		Loan newLoan = new Loan();
		newLoan.setUser(storedUser);
		newLoan.setCopy(copyToLoan);
		// Creation date of Loan will be set via annotation in Loan.
		loanRepository.save(newLoan);
	}

}
