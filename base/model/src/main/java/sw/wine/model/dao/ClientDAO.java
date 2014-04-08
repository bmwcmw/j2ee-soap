package sw.wine.model.dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import sw.wine.itf.DAOException;
import sw.wine.model.user.Client;

public class ClientDAO {
	
	private EntityManager em;
	
	public ClientDAO(){
		em = Persistence.createEntityManagerFactory("pg").createEntityManager();
	}
		
	public ClientDAO(EntityManager em){
			this.em=em;
		}
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public void insertOrUpdate(Client c) throws DAOException {
		try {
			if (em.find(Client.class, c.getId()) != null) {
				em.merge(c);
			} else {
				em.persist(c);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public Client findClientById(int id) throws DAOException {
		return em.find(Client.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Client> getAllClients() {
		return new ArrayList<Client>(em.createQuery("SELECT ca FROM Client ").getResultList());
	}
	
	public void deleteClient(Client client) {
		em.remove((Client) client);
	}



}
