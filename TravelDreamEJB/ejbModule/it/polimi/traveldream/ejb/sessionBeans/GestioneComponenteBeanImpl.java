package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class GestioneComponenteBeanImpl
 */
@Stateless
public class GestioneComponenteBeanImpl implements GestioneComponenteBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public GestioneComponenteBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiAereoDB(AereoDTO aereo) {
		Aereo nuovo = new Aereo(aereo);
		em.persist(nuovo);
		
	}

	@Override
	public boolean esisteAereo(String id) {
		Integer num = new Integer(id);
		if (em.find(Aereo.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

}
