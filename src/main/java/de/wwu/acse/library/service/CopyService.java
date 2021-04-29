package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import de.wwu.acse.library.data.model.Copy;
import de.wwu.acse.library.data.model.Medium;

/**
 * Interface for Copy management.
 * 
 * @author Henning Heitkoetter
 *
 */
public interface CopyService {
	Collection<Copy> getCopiesOfMedium(Medium m);

	Collection<Copy> createCopies(Medium m, int count) throws NoSuchElementException;

	Copy createCopy(Medium m) throws NoSuchElementException;

	Copy getCopy(int invNo) throws NoSuchElementException;

	List<Copy> getAvailableCopiesForMedium(int mediumId);
}
