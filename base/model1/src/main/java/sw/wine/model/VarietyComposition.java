package sw.wine.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import sw.wine.itf.IVarietyComposition;

@Entity
@Table(name = "composition")
@XmlType(propOrder = { "variety", "percentage" })
public class VarietyComposition implements IVarietyComposition, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VarietyComposition() {
		this.key = new Key();
	}

	VarietyComposition(Wine wine, String variety, int percentage) {
		this();
		this.key.wine = wine;
		this.key.variety = variety;
		this.percentage = percentage;
	}

	public static class Key implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@ManyToOne
		private Wine wine;
		private String variety;
	}

	@EmbeddedId
	private Key key;
	private int percentage;

	@Override
	@XmlElement
	public String getVariety() {
		return key.variety;
	}

	@SuppressWarnings("unused")
	private void setVariety(String variety) {
		this.key.variety = variety;
	}

	@Override
	@XmlElement
	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public Wine getWine() {
		return key.wine;
	}

}
