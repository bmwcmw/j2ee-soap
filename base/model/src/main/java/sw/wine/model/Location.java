package sw.wine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import sw.wine.itf.ILocation;

@Entity
@XmlType(propOrder = { "country", "region", "subRegion", "wines" })
public class Location implements ILocation, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue()
	private Long id;
	private String country;
	private String region;
	private String subRegion;
	@OneToMany(mappedBy = "location")
	@XmlElement(name = "wine")
	private List<Wine> wines = new ArrayList<Wine>();

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	@XmlElement(name = "subregion")
	public String getSubRegion() {
		return subRegion;
	}

	void setId(Long id) {
		this.id = id;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setSubRegion(String subRegion) {
		this.subRegion = subRegion;
	}

	public Long getId() {
		return id;
	}

	public Collection<Wine> getWines() {
		return wines;
	}

	void removeWine(Wine aThis) {
		wines.remove(aThis);
	}

	void addWine(Wine aThis) {
		wines.add(aThis);
	}
}
