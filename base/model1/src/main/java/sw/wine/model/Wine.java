package sw.wine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import sw.wine.itf.ILocation;
import sw.wine.itf.IVarietyComposition;
import sw.wine.itf.IWine;
import sw.wine.itf.WineColor;
import sw.wine.itf.WineType;

@Entity
@XmlType(propOrder = { "appellation", "color", "composition", "percentage",
		"producer", "vineyard", "vintage", "winestyle", "winetype", "bottles" })
public class Wine implements IWine, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", length = 15)
	@XmlAttribute(name = "id")
	private String fbId;
	@Temporal(TemporalType.DATE)
	private Date vintage;
	@OneToMany(mappedBy = "key.wine", cascade = CascadeType.ALL)
	@XmlElement(name = "composition")
	private Collection<VarietyComposition> composition = new ArrayList<VarietyComposition>();
	private String producer;
	@ManyToOne
	@JoinColumn(name = "location")
	private Location location;
	private String appellation;
	private String vineyard;
	@Enumerated(EnumType.STRING)
	private WineColor color;
	@XmlElement(name = "percentageAlcohol")
	private double percentage;
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "type")
	private WineType winetype;
	@Column(name = "style")
	@XmlElement(name = "style")
	private String winestyle;
	@OneToMany(mappedBy = "wine")
	@XmlElement(name = "bottle")
	private List<Bottle> bottles = new ArrayList<Bottle>();

	@Override
	public String getFBId() {
		return fbId;
	}

	@Override
	public Date getVintage() {
		return vintage;
	}

	@Override
	public boolean hasVintage() {
		return vintage != null;
	}

	@Override
	public Collection<IVarietyComposition> getComposition() {
		return new ArrayList<IVarietyComposition>(composition);
	}

	@Override
	public String getProducer() {
		return producer;
	}

	@Override
	public ILocation getLocation() {
		return location;
	}

	@Override
	public String getAppellation() {
		return appellation;
	}

	@Override
	public String getVineyard() {
		return vineyard;
	}

	@Override
	public WineColor getColor() {
		return color;
	}

	@Override
	public float getPercentageAlcohol() {
		return (float) percentage;
	}

	@Override
	public WineType getWineType() {
		return winetype;
	}

	@Override
	public String getWineStyle() {
		return winestyle;
	}
	

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public void setVintage(Date vintage) {
		this.vintage = vintage;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setLocation(Location location) {
		if (this.location != null) {
			this.location.removeWine(this);
		}
		this.location = location;
		location.addWine(this);
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public void setVineyard(String vineyard) {
		this.vineyard = vineyard;
	}

	public void setColor(WineColor color) {
		this.color = color;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public void setWinetype(WineType winetype) {
		this.winetype = winetype;
	}

	public void setWinestyle(String winestyle) {
		this.winestyle = winestyle;
	}

	public void addVariety(String variety, int percentage) {
		composition.add(new VarietyComposition(this, variety, percentage));
	}

	public void removeVariety(String variety) {
		for (VarietyComposition v : composition) {
			if (v.getVariety().equals(variety)) {
				composition.remove(v);
				return;
			}
		}
	}

	public Bottle newBottle() {
		Bottle b = new Bottle();
		b.setWine(this);
		bottles.add(b);
		return b;
	}

	public Collection<Bottle> getBottles() {
		return new ArrayList<Bottle>(this.bottles);
	}

	public void removeBottles(int nbBottles) {
		for (int i = 1; i <= nbBottles; i++) {
			Bottle b = bottles.get(bottles.size() - 1);
			b.setWine(null);
			bottles.remove(bottles.size() - 1);
		}
	}

}
