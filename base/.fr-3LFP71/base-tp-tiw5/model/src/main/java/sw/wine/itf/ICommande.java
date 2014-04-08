package sw.wine.itf;

import java.util.Collection;
import java.util.Date;

import sw.wine.model.CommandeArticles;

public interface ICommande {
	
	Long getId();
	
	Date getConfirmee();
	void setConfirmee(Date confirmee);
	boolean hasConfirmee();
	
	Date getEffectuee();
	void setEffectuee(Date effectuee);
	boolean hasEffectuee();
	Collection<CommandeArticles> getArticles();
}
