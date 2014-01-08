package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Camera;
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
	public HotelDTO getHotelById(String id) {
		Integer value = new Integer(id);
		Hotel hotel = em.find(Hotel.class, value.intValue());
		return convertToDTO(hotel);
	}
	

	

	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaAereo(AereoDTO aereo) {
		Aereo modificato = em.find(Aereo.class, aereo.getId());
		modificato.setAtterraggio(aereo.getCittaAtterraggio());
		modificato.setCosto(aereo.getCosto());
		modificato.setData(aereo.getData());
		modificato.setDecollo(aereo.getCittaDecollo());
		modificato.setPosti_Disponibili(aereo.getPostiDisponibili());
		em.merge(modificato);
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaHotel(HotelDTO hotel) {
		Hotel modificato = em.find(Hotel.class, hotel.getId());
		modificato.setCamere_Disponibili(hotel.getCamereDisponibili());
		modificato.setCittà(hotel.getCitta());
		modificato.setNome(hotel.getNome());
		modificato.setStelle(hotel.getRating().intValue());
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
	
	private HotelDTO convertToDTO(Hotel hotel){
		HotelDTO nuovo = new HotelDTO();
		nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCittà());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		ArrayList<CameraDTO> camere = new ArrayList<CameraDTO>();
		for(Camera camera:hotel.getCamere()){
			camere.add(convertToDTO(camera));
		}
		nuovo.setCamere(camere);
		return nuovo;
	}
	
	private CameraDTO convertToDTO(Camera camera){
		CameraDTO nuovo = new CameraDTO();
		nuovo.setCosto(camera.getCosto());
		nuovo.setData_Checkin(camera.getData_Checkin());
		nuovo.setData_Checkout(camera.getData_Checkout());
		nuovo.setId(camera.getId());
		nuovo.setOccupata(camera.getOccupata());
		nuovo.setPosti(camera.getPosti());
		return nuovo;
	}

	

	

	
	

}
