package sw.wine.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import sw.wine.itf.DAOException;
import sw.wine.itf.IBottle;
import sw.wine.itf.ICommande;
import sw.wine.itf.ICommandeArticles;
import sw.wine.itf.ILocation;
import sw.wine.itf.IWine;
import sw.wine.itf.IWineDAO;
import sw.wine.model.Bottle;
import sw.wine.model.Commande;
import sw.wine.model.Location;
import sw.wine.model.Wine;

public class JPAWineDAO implements IWineDAO {

	private EntityManager em;

	public JPAWineDAO() {
	}

	public JPAWineDAO(EntityManager em) {
		this.em = em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	/*LIVRAISON*/
	public void insertOrUpdate(ICommande commande) throws DAOException {
		try {
			Commande c = (Commande) commande;
			if (c.getId() != null && em.find(Commande.class, c.getId()) != null) {
				em.merge(c);
			} else {
				em.persist(c);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
//	public void insertOrUpdate(ICommandeArticles commandearticles) throws DAOException {
//		try {
//			CommandeArticles ca = (CommandeArticles) commandearticles;
//			if (ca.getId() != null && em.find(CommandeArticles.class, ca.getId()) != null) {
//				em.merge(ca);
//			} else {
//				em.persist(ca);
//			}
//		} catch (ClassCastException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}

	public Commande findCommandeById(long id) throws DAOException {
		return em.find(Commande.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ICommandeArticles> getAllCommandeArticles() {
		return new ArrayList<ICommandeArticles>(em.createQuery(
				"SELECT ca FROM CommandeArticles ca").getResultList());
	}
	
	public void deleteCommande(ICommande commande) {
		em.remove((Commande) commande);
	}
	
//	public void deleteCommandeArticles(ICommandeArticles ca) {
//		em.remove((CommandeArticles) ca);
//	}
	/*LIVRAISON*/
	@Override
	public void insertOrUpdate(IWine wine) throws DAOException {
		try {
			Wine w = (Wine) wine;
			if (w.getFBId() != null && em.find(Wine.class, w.getFBId()) != null) {
				em.merge(w);
			} else {
				em.persist(w);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void insertOrUpdate(IBottle bottle) throws DAOException {
		try {
			Bottle b = (Bottle) bottle;
			if (b.getId() != null && em.find(Bottle.class, b.getId()) != null) {
				em.merge(b);
			} else {
				em.persist(b);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void insertOrUpdate(ILocation location) throws DAOException {
		try {
			Location l = (Location) location;
			if (l.getId() != null && em.find(Location.class, l.getId()) != null) {
				em.merge(l);
			} else {
				em.persist(l);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Wine findWineById(String fbId) throws DAOException {
		return em.find(Wine.class, fbId);
	}

	@Override
	public Bottle findBottleById(long id) throws DAOException {
		return em.find(Bottle.class, id);
	}

	@Override
	public Location findOrCreateLocation(String country, String region,
			String subregion) throws DAOException {
		try {
			Location l = (Location) em
					.createQuery(
							"SELECT l FROM Location l WHERE l.country = :c AND l.region = :r AND l.subRegion = :s")
					.setParameter("c", country).setParameter("r", region)
					.setParameter("s", subregion).getSingleResult();
			return l;
		} catch (NoResultException e) {
			Location l = new Location();
			l.setCountry(country);
			l.setRegion(region);
			l.setSubRegion(subregion);
			em.persist(l);
			return l;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IWine> getAllWines() {
		return new ArrayList<IWine>(em.createQuery("SELECT w FROM Wine w")
				.getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ILocation> getAllLocations() {
		return new ArrayList<ILocation>(em.createQuery(
				"SELECT l FROM Location l").getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IBottle> getAllBottles() {
		return new ArrayList<IBottle>(em.createQuery("SELECT b FROM Bottle b")
				.getResultList());
	}

	@Override
	public void deleteWine(IWine wine) {
		em.remove((Wine) wine);
	}

	@Override
	public void deleteBottle(IBottle bottle) {
		em.remove((Bottle) bottle);
	}

	@Override
	public void deleteLocation(ILocation location) {
		em.remove((Location) location);
	}
}
