package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Camera;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Utente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

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
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(String citta) {
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.citt√† =:nome")
			    .setParameter("nome", citta).getResultList();
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0; i<hotels.size();i++){
			HotelDTO hotel = convertToDTO(hotels.get(i));
			listaHotel.add(hotel);
		}
		List<HotelDTO> listaHotels = listaHotel;
		return listaHotels;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", cittaAtterraggio)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		ArrayList<AereoDTO> aereiAndata = new ArrayList<AereoDTO>();
		for(int i=0; i<aerei.size(); i++){
			AereoDTO aereo = convertToDTO(aerei.get(i));
			aereiAndata.add(aereo);
		}
		List<AereoDTO> listaAerei = aereiAndata;
		return listaAerei;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date inizioValidita, Date fineValidita) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.decollo =:nome and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", cittaDecollo)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		ArrayList<AereoDTO> aereiRitorno = new ArrayList<AereoDTO>();
		for(int i=0; i<aerei.size(); i++){
			AereoDTO aereo = convertToDTO(aerei.get(i));
			aereiRitorno.add(aereo);
		}
		List<AereoDTO> listaAerei = aereiRitorno;
		return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni() {
		List<Escursione> escursioniDB = em.createNamedQuery("Escursione.findAll", Escursione.class).getResultList();
		ArrayList<EscursioneDTO> escursioni = new ArrayList<EscursioneDTO>();
    	for(int i=0; i<escursioniDB.size();i++){
    		EscursioneDTO escursione = convertToDTO(escursioniDB.get(i));
    		escursioni.add(escursione);
    	}
    	List<EscursioneDTO> listaEscursioni = escursioni;
    	return listaEscursioni;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date inizioValidita, Date fineValidita) {
		List<Escursione> escursioniDB = em.createQuery("SELECT e FROM Escursione e WHERE e.luogo =:nome and e.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", destinazione)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		ArrayList<EscursioneDTO> escursioni = new ArrayList<EscursioneDTO>();
    	for(int i=0; i<escursioniDB.size();i++){
    		EscursioneDTO escursione = convertToDTO(escursioniDB.get(i));
    		escursioni.add(escursione);
    	}
    	List<EscursioneDTO> listaEscursioni = escursioni;
    	return listaEscursioni;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void creaPacchetto(PacchettoDTO pacchetto) {
		Pacchetto nuovoPacchetto = new Pacchetto();
		List<Escursione> escursioni = covertListaEscursioni(pacchetto.getEscursioni());
		List<Hotel> hotel = convertListaHotel(pacchetto.getHotels());
		List<Aereo> aerei = convertListaAerei(pacchetto.getAereiAndata(),pacchetto.getAereiRitorno());
		Utente dipendente = em.find(Utente.class, context.getCallerPrincipal().getName());
		nuovoPacchetto.setDescrizione(pacchetto.getDescrizione());
		nuovoPacchetto.setDestinazione(pacchetto.getDestinazione());
		nuovoPacchetto.setEscursioni(escursioni);
		nuovoPacchetto.setHotels(hotel);
		nuovoPacchetto.setFine_Validit‡(pacchetto.getFine_Validita());
		nuovoPacchetto.setInizio_Validit‡(pacchetto.getInizio_Validita());
		nuovoPacchetto.setAerei(aerei);
		nuovoPacchetto.setDipendente(dipendente);
		nuovoPacchetto.setId(pacchetto.getId());
		em.persist(nuovoPacchetto);
		
	}
	
	@Override
	public boolean esisteIdPacchetto(String id) {
		Integer value = new Integer(id);
		if (em.find(Pacchetto.class,value.intValue())!=null){
	           return true;
	        }
	        return false;
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
		nuovo.setCitta(hotel.getCitt‡());
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
	
	private EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		return nuovo;
	}
	
	private List<Escursione> covertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = new Escursione(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	
	private List<Aereo> convertListaAerei(List<AereoDTO> listaAndata, List<AereoDTO> listaRitorno){
		ArrayList<Aereo> listaAerei = new ArrayList<Aereo>();
		for(int i=0;i<listaAndata.size();i++){
			Aereo nuovo = new Aereo(listaAndata.get(i));
			listaAerei.add(nuovo);
		}
		for(int i=0;i<listaRitorno.size();i++){
			Aereo nuovo = new Aereo(listaRitorno.get(i));
			listaAerei.add(nuovo);
		}
		List<Aereo> aerei = listaAerei;
		return aerei;
	}
	
	private List<Hotel> convertListaHotel(List<HotelDTO> lista){
		ArrayList<Hotel> listaHotel = new ArrayList<Hotel>();
		for(int i=0;i<lista.size();i++){
			Hotel nuovo = new Hotel(lista.get(i));
			listaHotel.add(nuovo);
		}
		List<Hotel> hotel = listaHotel;
		return hotel;
	}

	

	

	

}
