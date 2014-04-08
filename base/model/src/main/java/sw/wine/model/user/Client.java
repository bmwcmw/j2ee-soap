    package sw.wine.model.user;
     
    import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
     
    @Entity
    @XmlRootElement(name = "client")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Client {
     
    	/**
    	 * L'identifiant du client.
    	 */
    	@Id
    	@GeneratedValue
    	@XmlElement(name = "id")
    	private int id;
     
    	/**
    	 * Le nom du client.
    	 */
    	@XmlElement(name = "nom")
    	private String nom;
     
    	/**
    	 * La quantité d'argent que le client possède sur son compte de location.
    	 */
    	@XmlElement(name = "compte")
    	private double compte;
     
    	public String getNom() {
    		return nom;
    	}
     
    	public void setNom(String nom) {
    		this.nom = nom;
    	}
     
    	public int getId() {
    		return id;
    	}
     
    	public double getCompte() {
    		return compte;
    	}
     
    	public void setCompte(double compte) {
    		this.compte = compte;
    	}
     
    }

