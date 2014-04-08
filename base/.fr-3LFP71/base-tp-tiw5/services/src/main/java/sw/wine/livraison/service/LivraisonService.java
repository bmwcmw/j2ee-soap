package sw.wine.livraison.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.mortbay.log.Log;
import org.springframework.transaction.annotation.Transactional;

import sw.wine.itf.DAOException;
import sw.wine.itf.ICommandeArticles;
import sw.wine.livraison.Article;
import sw.wine.livraison.CommandeInconnueException;
import sw.wine.livraison.CommandeInfos;
import sw.wine.livraison.NonDisponibleException;
import sw.wine.model.Bottle;
import sw.wine.model.Commande;
import sw.wine.model.CommandeArticles;
import sw.wine.model.Wine;
import sw.wine.model.dao.ClientDAO;
import sw.wine.model.dao.JPAWineDAO;
import sw.wine.model.user.Client;
import sw.wine.handlers.*;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.binding.soap.SoapMessage;


@WebService(name="LivraisonServiceItf", portName="LivraisonServicePort",
serviceName="LivraisonServiceService", targetNamespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/")
@HandlerChain(file="handler-chains.xml")
public class LivraisonService{
	
    @Resource
    JPAWineDAO dao;
    
    @Resource 
    ClientDAO cDao;
    
	EntityManager em;
	
	@Resource
	WebServiceContext wsc;
	
	public LivraisonService(){
		em = Persistence.createEntityManagerFactory("pg").createEntityManager();
	}

	
    @Transactional
    @WebMethod(action="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/commande", operationName="commande")
	public @WebResult(name="infos") CommandeInfos commande(@WebParam(name="vin") /*Collection<Article>*/Article[] vins) throws NonDisponibleException {
		JPAWineDAO dao = new JPAWineDAO(em);
		EntityTransaction et=em.getTransaction();
		if (!et.isActive()) et.begin();
		ClientDAO cDao = new ClientDAO(em);
		System.out.println("CommandeInfos - Création de commande pour "+vins.length+" vins");
		double prixTotal = 0;
		int nombreCmdTmp;
		int nombreDispTmp;
		double prixTmp;
		Wine w;
		Client client=null;
		Map<String, Client> attachments = (Map<String, Client>) wsc.getMessageContext().get(javax.xml.ws.handler.MessageContext.INBOUND_MESSAGE_ATTACHMENTS); 
		for(String attachmentKey: attachments.keySet()) {
            client =  attachments.get(attachmentKey);
            Logger.getLogger("asy").info(client.getNom()+" "+client.getCompte());
        }

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
				//em.close();  
				throw new NonDisponibleException(vins[i]);
			} catch (NullPointerException e){
				System.out.println("CommandeInfos - NullPointerException");
				//em.close();  
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
			client.setCompte(client.getCompte()-prixTotal);
			cDao.insertOrUpdate(client);
	        et.commit();
			//em.close();  
			return newCmd;
		} catch (DAOException e) {
			//em.close();  
			return null;
		}
	}

	
    @Transactional
    @WebMethod(action="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/confirmation", operationName="confirmation")
	public @WebResult(name="dateLivraison") Date confirmation(@WebParam(name="confirmation") String commandeId)
			throws CommandeInconnueException, NonDisponibleException{
    	if (commandeId==null) commandeId="716";
		JPAWineDAO dao = new JPAWineDAO(em);
		EntityTransaction et=em.getTransaction();
		if (!et.isActive()) et.begin();
		Logger.getLogger("livraisonService").info("commandeId   "+commandeId);
		//throw new CommandeInconnueException(commandeId);
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
						//em.close();
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
		    et.commit();
		    //em.close();
		    return confirmee;
		} catch (Exception e) {
			//em.close();
			//throw e;
			throw new CommandeInconnueException(commandeId);
		}
	}
	
	// Set l'état à "Confirmé"
	
    @Transactional
    @WebMethod(action="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/livraisonEffectuee", operationName="livraisonEffectuee")
	public @WebResult(name="livraisonEffectueeRespons") boolean livraisonEffectuee(@WebParam(name="livraisonEffectuee")String commandeId)
			throws CommandeInconnueException {
		JPAWineDAO dao = new JPAWineDAO(em);
		EntityTransaction et=em.getTransaction();
		if (!et.isActive()) et.begin();
		try {
			System.out.println("CommandeInfos - livraisonEffectuee - Commande #"
					+commandeId);
			Commande cmd = dao.findCommandeById(Long.valueOf(commandeId).longValue());
			if(cmd.hasEffectuee()){
			    //em.close();
				return false;
			} else {
				cmd.setEffectuee(new Date(System.currentTimeMillis()));
				et.commit();
			    //em.close();
				return true;
			}
		} catch (Exception e) {
		    //em.close();
			throw new CommandeInconnueException(commandeId);
		}
	}

}
