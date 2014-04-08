///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package sw.wine.model;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Iterator;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Persistence;
//
//import junit.framework.TestCase;
//import sw.wine.itf.DAOException;
//import sw.wine.itf.ICommandeArticles;
//import sw.wine.itf.WineColor;
//import sw.wine.itf.WineType;
//import sw.wine.model.dao.JPAWineDAO;
//
///**
// *
// * @author Famille
// */
//public class JPATest extends TestCase {
//    
//    public void testMapping() {
//        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
//        em.close();
//    }
//    
//    public void testDAO() throws DAOException {
//        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
//        JPAWineDAO dao = new JPAWineDAO(em);
//        em.getTransaction().begin();
//        Location loc = dao.findOrCreateLocation("France", "Rhone-Alpes", "Rhone");
//        Wine wine = new Wine();
//        wine.setAppellation("Courante");
//        wine.setColor(WineColor.Red);
//        wine.setFbId("azertyuiop");
//        wine.setLocation(loc);
//        wine.setPercentage(14.0);
//        wine.setProducer("Ernest");
//        wine.setVineyard("Chateau la pompe");
//        Calendar annee = Calendar.getInstance();
//        annee.clear();
//        annee.set(2013, 1, 1);
//        wine.setVintage(annee.getTime());
//        wine.setWinestyle("Eau courante");
//        wine.setWinetype(WineType.Ice);
//        wine.addVariety("Eau",100);
//        wine.setPrice(3.5);
//        dao.insertOrUpdate(wine);
//        Bottle b = new Bottle();
//        b.setWine(wine);
//        dao.insertOrUpdate(b);
//        em.getTransaction().commit();
//        em.close();        
//    }
//    
//    public void testLivraisonDAO() throws DAOException {
//        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
//        JPAWineDAO dao = new JPAWineDAO(em);
//        em.getTransaction().begin();
//        Location loc = dao.findOrCreateLocation("CHINA", "GD", "GZ");
//        Wine wine = new Wine();
//        wine.setAppellation("apple1");
//        wine.setColor(WineColor.Red);
//        wine.setFbId("cmw2013");
//        wine.setLocation(loc);
//        wine.setPercentage(14.0);
//        wine.setProducer("Chez Moi");
//        wine.setVineyard("Chateau A");
//        Calendar annee = Calendar.getInstance();
//        annee.clear();
//        annee.set(2013, 1, 1);
//        wine.setVintage(annee.getTime());
//        wine.setWinestyle("My Style");
//        wine.setWinetype(WineType.Ice);
//        wine.addVariety("H2O",77);
//        wine.setPrice(102.35);
//        dao.insertOrUpdate(wine);
//        Bottle b = new Bottle();
//        b.setWine(wine);
//        dao.insertOrUpdate(b);
//        
//        Commande newCMD = new Commande();
//        newCMD.addArticle(wine, 33);
//        newCMD.addArticle(wine, 44);
//        newCMD.addArticle(wine, 44);
//        Calendar today = Calendar.getInstance();
//        today.clear();
//        today.set(2013, 11, 8);
//        newCMD.setConfirmee(today.getTime());
//        newCMD.setEffectuee(today.getTime());
//        
//        int oldSize = dao.getAllCommandeArticles().size();
//        dao.insertOrUpdate(newCMD);
//        
//        /*Important*/
//        em.getTransaction().commit();
//        
//        long idGot = dao.findCommandeById(newCMD.getId()).getId();
//        System.out.println("new Commande found : id="+idGot);
//        
//        Collection<ICommandeArticles> collection = dao.getAllCommandeArticles();
//        Iterator<ICommandeArticles> it = collection.iterator();        
//        CommandeArticles temp;
//        while (it.hasNext()){
//        	temp=(CommandeArticles) it.next();
//        	if(temp.getCommande().getId().longValue()==idGot)
//        		System.out.println("new Articles added : id="+
//        			temp.getId()+" to Commande id="+temp.getCommande().getId());
//        }
//        
//        System.out.println("new Articles added : "+(collection.size()-oldSize));
//        
//    	
//        System.out.println("test findWindById cmw: "+dao.findWineById("cmw")
//        		+" prix="+dao.findWineById("cmw").getPrice()
//        		+" disponible="+dao.findWineById("cmw").getBottles().size());
//        System.out.println("test findWindById cmw2013: "+dao.findWineById("cmw2013")
//        		+" prix="+dao.findWineById("cmw2013").getPrice()
//        		+" disponible="+dao.findWineById("cmw2013").getBottles().size());
//        
//        
//        System.out.println(dao.findWineById("cmw2013").getPrice()
//        		*dao.findWineById("cmw2013").getBottles().size());
//        
//        ArrayList<ICommandeArticles> articles = 
//				new ArrayList<ICommandeArticles>(dao.getAllCommandeArticles());
//        System.out.println("Nb des CommandeArticles : "+articles.size());
//        em.close();        
//    }
//    
//}
