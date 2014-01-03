package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Camera;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Utente;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class GestionePacchettoBeanImpl
 */
@Stateless
public class GestionePacchettoBeanImpl implements GestionePacchettoBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public GestionePacchettoBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findAll", Aereo.class).getResultList();
		ArrayList<AereoDTO> aerei = new ArrayList<AereoDTO>();
    	for(int i=0; i<aereiDB.size();i++){
    		AereoDTO aereo = convertToDTO(aereiDB.get(i));
    		aerei.add(aereo);
    	}
    	List<AereoDTO> listaAerei = aerei;
    	return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(){
		List<Hotel> hotels = em.createNamedQuery("Hotel.findAll", Hotel.class).getResultList();
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0; i<hotels.size();i++){
			HotelDTO hotel = convertToDTO(hotels.get(i));
			listaHotel.add(hotel);
		}
		List<HotelDTO> listaHotels = listaHotel;
		return listaHotels;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotelPerCitta(String citta) {
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.città =:nome")
			    .setParameter("nome", citta).getResultList();
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0; i<hotels.size();i++){
			HotelDTO hotel = convertToDTO(hotels.get(i));
			listaHotel.add(hotel);
		}
		List<HotelDTO> listaHotels = listaHotel;
		return listaHotels;
	}
	

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String cittaAtterraggio) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome")
			    .setParameter("nome", cittaAtterraggio).getResultList();
		ArrayList<AereoDTO> aereiAndata = new ArrayList<AereoDTO>();
		for(int i=0; i<aerei.size(); i++){
			AereoDTO aereo = convertToDTO(aerei.get(i));
			aereiAndata.add(aereo);
		}
		List<AereoDTO> listaAerei = aereiAndata;
		return listaAerei;
	}

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitorno(String cittaDecollo) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.decollo =:nome")
			    .setParameter("nome", cittaDecollo).getResultList();
		ArrayList<AereoDTO> aereiRitorno = new ArrayList<AereoDTO>();
		for(int i=0; i<aerei.size(); i++){
			AereoDTO aereo = convertToDTO(aerei.get(i));
			aereiRitorno.add(aereo);
		}
		List<AereoDTO> listaAerei = aereiRitorno;
		return listaAerei;
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
