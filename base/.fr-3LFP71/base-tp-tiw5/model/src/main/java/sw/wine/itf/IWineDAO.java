package sw.wine.itf;

import java.util.Collection;

public interface IWineDAO {

	void insertOrUpdate(IWine wine) throws DAOException;

	void insertOrUpdate(IBottle bottle) throws DAOException;

	void insertOrUpdate(ILocation location) throws DAOException;

	IWine findWineById(String fbId) throws DAOException;

	IBottle findBottleById(long id) throws DAOException;

	ILocation findOrCreateLocation(String country, String region,
			String subregion) throws DAOException;
	
	Collection<IWine> getAllWines();
	
	Collection<ILocation> getAllLocations();
	
	Collection<IBottle> getAllBottles();
	
	void deleteWine(IWine wine);
	
	void deleteBottle(IBottle bottle);
	
	void deleteLocation(ILocation location);

}
