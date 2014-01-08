package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;

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
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiHotelDB(HotelDTO hotel) {
		Hotel nuovo = new Hotel(hotel);
		em.persist(nuovo);
		
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiEscursioneDB(EscursioneDTO escursione) {
		Escursione nuovo = new Escursione(escursione);
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

	@Override
	public boolean esisteHotel(String id) {
		Integer num = new Integer(id);
		if (em.find(Hotel.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	@Override
	public boolean esisteEscursione(String id) {
		Integer num = new Integer(id);
		if (em.find(Escursione.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	@Override
	public AereoDTO getAereoById(String id) {
		Integer value = new Integer(id);
		Aereo aereo = em.find(Aereo.class, value.intValue());
		return convertToDTO(aereo);
	}
	

	@Override
	public void modificaAereo(AereoDTO aereo) {
		Aereo modificato = em.find(Aereo.class, aereo.getId());
		modificato.setAtterraggio(aereo.getCittaAtterraggio());
		modificato.setCosto(aereo.getCosto());
		modificato.setData(aereo.getData());
		modificato.setDecollo(aereo.getCittaDecollo());
		modificato.setPosti_Disponibili(aereo.getPostiDisponibili());
		em.merge(modificato);
	}
	
	private AereoDTO convertToDTO(Aereo aereo){
		AereoDTO nuovo = new AereoDTO();
		nuovo.setCittaAtterraggio(aereo.getAtterraggio());
		nuovo.setCittaDecollo(aereo.getDecollo());
		nuovo.setCosto(aereo.getCosto());
		nuovo.setData(aereo.getData());
		nuovo.setId(aereo.getId());
		nuovo.setPostiDisponibili(aereo.getPosti_Disponibili());
		return nuovo;
	}


	
	

}
