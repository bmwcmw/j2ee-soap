package sw.wine.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import sw.wine.itf.IBottle;
import sw.wine.itf.IWine;

@Entity
@Table(name = "bottle")
public class Bottle implements IBottle, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "integer")
	private Long id;

	@ManyToOne
	private Wine wine;

	@Override
	@XmlTransient
	public IWine getWine() {
		return wine;
	}

	void setWine(Wine wine) {
		this.wine = wine;
	}

	@Override
	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
