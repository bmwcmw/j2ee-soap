package sw.wine.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import sw.wine.itf.ICommandeArticles;

@Entity
@Table(name = "commande_articles")
public class CommandeArticles implements ICommandeArticles, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "commande_id")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name = "wine_id")
	private Wine wine;
	
	private int quantite;
	
	public CommandeArticles(){}
	
	public CommandeArticles(Commande c, Wine w, int q){
		this.wine=w;
		this.commande=c;
		this.quantite=q;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	@XmlTransient
	public Commande getCommande() {
		return commande;
	}

	@Override
	public void setCommande(Commande cmd) {
		this.commande = cmd;
	}
	
	@XmlTransient
	@Override
	public Wine getWine() {
		return wine;
	}

	@Override
	public void setWine(Wine wine) {
		this.wine = wine;
	}
	
	@Override
	public int getQuantite() {
		return quantite;
	}

	@Override
	public void setQuantite(int q) {
		this.quantite = q;
	}
}