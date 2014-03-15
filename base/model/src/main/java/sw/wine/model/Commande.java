package sw.wine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import sw.wine.itf.ICommande;


@Entity
@Table(name = "commande")
public class Commande implements ICommande, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "integer")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date confirmee;

	@Temporal(TemporalType.DATE)
	private Date effectuee;
	
	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
	@XmlElement(name = "vin")
	private Collection<CommandeArticles> articles = new ArrayList<CommandeArticles>();

	@Override
	public Long getId() {
		return id;
	}	
	
	@Override
	public Date getConfirmee() {
		return confirmee;
	}
	@Override
	public void setConfirmee(Date confirmee) {
		this.confirmee = confirmee;
	}
	@Override
	public boolean hasConfirmee() {
		return confirmee != null;
	}

	@Override
	public Date getEffectuee() {
		return effectuee;
	}
	@Override
	public void setEffectuee(Date effectuee) {
		this.effectuee = effectuee;
	}
	@Override
	public boolean hasEffectuee() {
		return effectuee != null;
	}
	
	public void addArticle(Wine w, int quantite) {
		articles.add(new CommandeArticles(this, w, quantite));
	}

	public void removeArticle(CommandeArticles vin) {
		for (CommandeArticles v : articles) {
			if ((v.getQuantite()==vin.getQuantite())
				 && v.getWine().equals(vin.getWine())
				 && v.getCommande().equals(vin.getCommande())) {
				articles.remove(v);
				return;
			}
		}
	}

	@Override
	public Collection<CommandeArticles> getArticles() {
		return new ArrayList<CommandeArticles>(articles);
	}
	
	public Commande(){}

}