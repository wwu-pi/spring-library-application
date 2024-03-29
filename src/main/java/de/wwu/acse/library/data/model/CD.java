package de.wwu.acse.library.data.model;

import jakarta.persistence.Entity;

/**
 * Entity representing a cd. Fields with get- and set methods are automatically
 * mapped to a corresponding database column. Annotations are used to define the
 * primary key and relations to other entities.
 * 
 * @author Herbert Kuchen
 * @version 1.0
 */
@Entity
public class CD extends Medium implements java.io.Serializable {
	private static final long serialVersionUID = -2916143633109514792L;
	protected String asin;
	protected String artist;

	public String getASIN() {
		return this.asin;
	}

	public void setASIN(String asin) {
		this.asin = asin;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
}
