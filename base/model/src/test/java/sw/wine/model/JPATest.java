/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sw.wine.model;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import sw.wine.itf.DAOException;
import sw.wine.itf.WineColor;
import sw.wine.itf.WineType;
import sw.wine.model.dao.JPAWineDAO;

/**
 *
 * @author Famille
 */
public class JPATest extends TestCase {
    
    public void testMapping() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        em.close();
    }
    
    public void testDAO() throws DAOException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        JPAWineDAO dao = new JPAWineDAO(em);
        em.getTransaction().begin();
        Location loc = dao.findOrCreateLocation("France", "Rhone-Alpes", "Rhone");
        Wine wine = new Wine();
        wine.setAppellation("Courante");
        wine.setColor(WineColor.Red);
        wine.setFbId("azertyuiop");
        wine.setLocation(loc);
        wine.setPercentage(14.0);
        wine.setProducer("Ernest");
        wine.setVineyard("Chateau la pompe");
        Calendar annee = Calendar.getInstance();
        annee.clear();
        annee.set(2013, 1, 1);
        wine.setVintage(annee.getTime());
        wine.setWinestyle("Eau courante");
        wine.setWinetype(WineType.Ice);
        wine.addVariety("Eau",100);
        dao.insertOrUpdate(wine);
        Bottle b = new Bottle();
        b.setWine(wine);
        dao.insertOrUpdate(b);
        em.getTransaction().commit();
        em.close();        
    }
}
