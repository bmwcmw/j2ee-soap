package sw.wine.livraison;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "vin")
@XmlAccessorType(XmlAccessType.FIELD)
public class Article {
	

	@XmlElement(name = "reference")
	private String referenceVin;
	

	@XmlElement(name = "quantite")
	private int nombre;
	
	public Article(){}

	public Article(String referenceVin, int nombre) {
		this.referenceVin = referenceVin;
		this.nombre = nombre;
	}

	public String getReferenceVin() {
		return referenceVin;
	}

	public void setReferenceVin(String referenceVin) {
		this.referenceVin = referenceVin;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nombre;
		result = prime * result
				+ ((referenceVin == null) ? 0 : referenceVin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (nombre != other.nombre)
			return false;
		if (referenceVin == null) {
			if (other.referenceVin != null)
				return false;
		} else if (!referenceVin.equals(other.referenceVin))
			return false;
		return true;
	}
}
