package sw.wine.livraison;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import sw.wine.itf.DAOException;
import sw.wine.itf.ICommandeArticles;
import sw.wine.model.Bottle;
import sw.wine.model.Commande;
import sw.wine.model.CommandeArticles;
import sw.wine.model.Wine;
import sw.wine.model.dao.JPAWineDAO;

public class Livraison implements ILivraison {
	EntityManager em;
	
	public Livraison(){
		em = Persistence.createEntityManagerFactory("pg").createEntityManager();
	}

	@Override
	public CommandeInfos commande(Article[] vins) throws NonDisponibleException {
		JPAWineDAO dao = new JPAWineDAO(em);
		em.getTransaction().begin();
		System.out.println("CommandeInfos - Création de commande pour "+vins.length+" vins");
		double prixTotal = 0;
		int nombreCmdTmp;
		int nombreDispTmp;
		double prixTmp;
		Wine w;
		for(int i=0;i<vins.length;i++){
			try {
				w = dao.findWineById(vins[i].getReferenceVin());
				nombreCmdTmp = vins[i].getNombre();
				System.out.println("CommandeInfos - Info du vin"+i
						+" #"+vins[i].getReferenceVin()
						+" : Nb commandé="+nombreCmdTmp);
				nombreDispTmp = w.getBottles().size();
				System.out.println("CommandeInfos - Info du vin"+i
						+" #"+vins[i].getReferenceVin()
						+" : Nb disponible="+nombreDispTmp);
				if(nombreCmdTmp>nombreDispTmp) throw new NonDisponibleException(vins[i]);
				prixTmp = w.getPrice();
				System.out.println("CommandeInfos - Info du vin"+i
						+" #"+vins[i].getReferenceVin()
						+" : Prix="+prixTmp);
				prixTotal += prixTmp * nombreCmdTmp;
			} catch (DAOException e) {
				System.out.println("CommandeInfos - DAOException");
				em.close();  
				throw new NonDisponibleException(vins[i]);
			} catch (NullPointerException e){
				System.out.println("CommandeInfos - NullPointerException");
				em.close();  
				throw new NonDisponibleException(vins[i]);
			}
		}
		try {
			Commande cmd = new Commande();
			for(int i=0;i<vins.length;i++){
				cmd.addArticle(dao.findWineById(vins[i].getReferenceVin()), vins[i].getNombre());
			}
			dao.insertOrUpdate(cmd);
			CommandeInfos newCmd = new CommandeInfos();
			newCmd.setCmdId(String.valueOf(cmd.getId()));
			newCmd.setPrix(prixTotal);//Long to String
			System.out.println("CommandeInfos - Commande créée : prix="+prixTotal);
	        em.getTransaction().commit();
			em.close();  
			return newCmd;
		} catch (DAOException e) {
			em.close();  
			return null;
		}
	}

	@Override
	public Date confirmeCommande(String commandeId)
			throws CommandeInconnueException, NonDisponibleException {
		JPAWineDAO dao = new JPAWineDAO(em);
		em.getTransaction().begin();
		try {
			CommandeArticles ca;
			Wine wine;
			int stockWine;
			int commandeWine;
			ArrayList<ICommandeArticles> articles = 
					new ArrayList<ICommandeArticles>(dao.getAllCommandeArticles());
			HashMap<String, Integer> totaleCommande = new HashMap<String, Integer>();
			for(int i=0;i<articles.size();i++){
				ca = (CommandeArticles)(articles.get(i));
				System.out.println("CommandeInfos - Article dans Commande #"
						+ca.getCommande().getId().longValue()
						+" - Commande cherchée #"
						+Long.parseLong(commandeId));
				if(ca.getCommande().getId().longValue()==Long.parseLong(commandeId)){
					wine = ca.getWine();
					stockWine = ca.getWine().getBottles().size();
					commandeWine = ca.getQuantite();
					System.out.println("CommandeInfos - Article pour Commande #"
							+ca.getCommande().getId().longValue()+" trouvé #" 
							+ca.getId()+" wine #"+wine.getFBId()+":"+stockWine+"disponible(s)");
					if(totaleCommande.containsKey(wine.getFBId())){
						System.out.println("CommandeInfos - wine #"+wine.getFBId()+" déjà existe dans le compteur :"
							+totaleCommande.get(wine.getFBId())+"+"+commandeWine);
						totaleCommande.put(wine.getFBId(), 
								totaleCommande.get(wine.getFBId())+commandeWine);
					} else {
						System.out.println("CommandeInfos - wine #"+wine.getFBId()
							+commandeWine+" bouteille(s) ajoutée(s) dans le compteur");
						totaleCommande.put(wine.getFBId(), commandeWine);
					}
					if( totaleCommande.get(wine.getFBId()) > stockWine ){
						System.out.println("CommandeInfos - wine #"+wine.getFBId()+" : "
							+totaleCommande.get(wine.getFBId())+"commandée(s) mais "
							+stockWine+"disponible(s)");
						em.close();  
						throw new NonDisponibleException(
								new Article(wine.getFBId(),ca.getQuantite()));
					}
				}
			}

		    Iterator<Entry<String, Integer>> itMap = totaleCommande.entrySet().iterator();
		    Iterator<Bottle> itBottle;
		    Integer nbRemove;
		    Entry<String, Integer> elemTemp;
		    while (itMap.hasNext()) {
		    	elemTemp = itMap.next();
		    	wine = dao.findWineById(elemTemp.getKey());
		    	nbRemove = elemTemp.getValue();
				System.out.println("CommandeInfos - Confirmation - Commande confirmée : wine_id="
						+wine.getFBId()+" "+nbRemove+" bouteille(s) prise(s)");
		    	itBottle = wine.getBottles().iterator();
				while (nbRemove>0) {
					dao.deleteBottle(itBottle.next());
					nbRemove--;
				}
		    }
		    
			Commande cmd = dao.findCommandeById(Long.valueOf(commandeId).longValue());
		    Date confirmee = new Date(System.currentTimeMillis()+3*24*60*60*1000);
		    cmd.setConfirmee(confirmee);
		    em.getTransaction().commit();
		    em.close();
		    return confirmee;
		} catch (Exception e) {
			em.close();
			throw new CommandeInconnueException(commandeId);
		}
	}
	
	// Set l'état à "Confirmé"
	@Override
	public boolean livraisonEffectuee(String commandeId)
			throws CommandeInconnueException {
		JPAWineDAO dao = new JPAWineDAO(em);
		em.getTransaction().begin();
		try {
			System.out.println("CommandeInfos - livraisonEffectuee - Commande #"
					+commandeId);
			Commande cmd = dao.findCommandeById(Long.valueOf(commandeId).longValue());
			if(cmd.hasEffectuee()){
			    em.close();
				return false;
			} else {
				cmd.setEffectuee(new Date(System.currentTimeMillis()));
				em.getTransaction().commit();
			    em.close();
				return true;
			}
		} catch (Exception e) {
		    em.close();
			throw new CommandeInconnueException(commandeId);
		}
	}

}
