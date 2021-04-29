package de.wwu.acse.library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wwu.acse.library.data.model.Copy;
import de.wwu.acse.library.data.model.Medium;
import de.wwu.acse.library.data.repo.CopyRepository;
import de.wwu.acse.library.data.repo.MediumRepository;

@Service
public class StdCopyService implements CopyService {

	@Autowired
	private CopyRepository copyRepository;
	@Autowired
	private MediumRepository<Medium> mediumRepository;

	@Override
	public Collection<Copy> getCopiesOfMedium(Medium m) {
		return copyRepository.findByMediumId(m.getId());
	}

	@Override
	public Collection<Copy> createCopies(Medium m, int count) throws NoSuchElementException {
		List<Copy> result = new ArrayList<>();
		Optional<Medium> possiblyStoredMedium = mediumRepository.findById(m.getId());
		if (!possiblyStoredMedium.isPresent()) {
			throw new NoSuchElementException("The medium cannot be found");
		}

		for (int i = 0; i < count; i++) {
			Copy newCopy = new Copy();
			newCopy.setMedium(m);
			// This is inefficient. We can also add all new copies to the list and then
			// invoke saveAll().
			// However, with this current implementation we would have to deal with possibly
			// having unpersisted
			// copies in the list of copies in the medium. We would thus probably need to
			// change the friend-method.
			newCopy = copyRepository.save(newCopy);
			result.add(newCopy);
		}
		return result;
	}

	@Override
	public Copy createCopy(Medium m) throws NoSuchElementException {
		return createCopies(m, 1).iterator().next();
	}

	@Override
	public Copy getCopy(int invNo) throws NoSuchElementException {
		Optional<Copy> possibleCopy = copyRepository.findById(invNo);
		if (!possibleCopy.isPresent()) {
			throw new NoSuchElementException("There is no copy with the given ID.");
		} else {
			return possibleCopy.get();
		}
	}

	@Override
	public List<Copy> getAvailableCopiesForMedium(int mediumId) {
		return copyRepository.findByMediumIdAndLoanIsNull(mediumId);
	}
}
