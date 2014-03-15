/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sw.wine.services;

import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

import sw.wine.itf.DAOException;
import sw.wine.itf.IVarietyComposition;
import sw.wine.itf.IWine;
import sw.wine.model.Bottle;
import sw.wine.model.Location;
import sw.wine.model.Wine;
import sw.wine.model.WineCollection;
import sw.wine.model.dao.JPAWineDAO;

@WebService(name="WineStorageItf", portName="WineStoragePort",
        serviceName="WineStorageService", targetNamespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/storage")
public class WineStorage {
	
	private static final Logger LOG = Logger.getLogger(WineStorage.class.getName());

    @Resource
    JPAWineDAO dao;
    
    @Transactional
    @WebMethod(action="getWines", operationName="getWines")
    public @WebResult(name="locations") WineCollection getWinesForLocations(@WebParam(name="location") Collection<Location> locations) throws DAOException {
        WineCollection collection = new WineCollection();
        for(Location loc: locations) {
            Location locJPA = dao.findOrCreateLocation(loc.getCountry(), loc.getRegion(), loc.getSubRegion());
            collection.addLocation(locJPA);
        }
        // On s'assure que les vins et leurs composants sont chargés
        for(IWine wine: collection.getWines()) {
        	((Wine)wine).getComposition().size();
        	((Wine)wine).getBottles().size();
        }
        return collection;
    }
    
    @Transactional
    @WebMethod
    public @WebResult(name="ack") boolean addWinesFromLocation(@WebParam(name="location") Location location) throws DAOException {
        Location locJPA = dao.findOrCreateLocation(location.getCountry(), location.getRegion(), location.getSubRegion());
        for(Wine wine : location.getWines()) {
        	int nbBottles = wine.getBottles().size();
        	wine.removeBottles(nbBottles);
        	for(IVarietyComposition cmp : wine.getComposition()) {
        		wine.removeVariety(cmp.getVariety());
        		wine.addVariety(cmp.getVariety(), cmp.getPercentage());
        	}
        	LOG.info("---  Vineyard du vin : "+String.valueOf(wine.getVineyard()));
            dao.insertOrUpdate(wine);
            // pour être sur d'avoir la bonne instance
            wine = dao.findWineById(wine.getFBId());
        	wine.setLocation(locJPA);
        	for(int i = 0; i < nbBottles; i++) {
        		Bottle b = wine.newBottle();
        		assert(b.getWine()==wine);
        		dao.insertOrUpdate(b);
        	}
        }
        return true;
    }
}
