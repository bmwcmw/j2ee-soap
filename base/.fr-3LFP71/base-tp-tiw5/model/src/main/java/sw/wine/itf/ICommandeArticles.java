package sw.wine.itf;

import sw.wine.model.Commande;
import sw.wine.model.Wine;

public interface ICommandeArticles {

	Long getId();

	void setId(Long id);
	
	public Commande getCommande();

	void setCommande(Commande cmd);
	
	Wine getWine();
	
	void setWine(Wine wine);
	
	int getQuantite();
	
	void setQuantite(int q);

}
