package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
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
import javax.ejb.EJB;
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
	
	@EJB
	private GestioneUtenteBean gestioneUtente;
    /**
     * Default constructor. 
     */
    public GestionePacchettoBeanImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<PacchettoDTO> getListaPacchetti() {
		List<Pacchetto> pacchettiDB = em.createNamedQuery("Pacchetto.findAll", Pacchetto.class).getResultList();
		List<PacchettoDTO> pacchetti = convertListaPacchettiToDTO(pacchettiDB);
		return pacchetti;
		
	}

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findAll", Aereo.class).getResultList();
	   	List<AereoDTO> listaAerei = convertListaAereiToDTO(aereiDB);
    	return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(){
		List<Hotel> hotels = em.createNamedQuery("Hotel.findAll", Hotel.class).getResultList();
		List<HotelDTO> listaHotels = convertListaHotelToDTO(hotels);
		return listaHotels;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(String citta) {
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.città =:nome")
			    .setParameter("nome", citta).getResultList();
		List<HotelDTO> listaHotels = convertListaHotelToDTO(hotels);
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
		List<AereoDTO> listaAerei = convertListaAereiAndataToDTO(aerei, cittaAtterraggio);
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
		List<AereoDTO> listaAerei = convertListaAereiRitornoToDTO(aerei, cittaDecollo);
		return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni() {
		List<Escursione> escursioniDB = em.createNamedQuery("Escursione.findAll", Escursione.class).getResultList();
	   	List<EscursioneDTO> listaEscursioni = convertListaEscursioniToDTO(escursioniDB);
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
		List<EscursioneDTO> listaEscursioni = convertListaEscursioniToDTO(escursioniDB);
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
		nuovoPacchetto.setFine_Validità(pacchetto.getFine_Validita());
		nuovoPacchetto.setInizio_Validità(pacchetto.getInizio_Validita());
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

/*
 * Metodi privati per la conversione di un oggetto prelevato dal database in un oggetto che verrà inviato alla view.
 */
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
	
	private EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		return nuovo;
	}
	
	private PacchettoDTO convertToDTO(Pacchetto pacchetto){
		PacchettoDTO nuovo = new PacchettoDTO();
		nuovo.setDescrizione(pacchetto.getDescrizione());
		nuovo.setDestinazione(pacchetto.getDestinazione());
		nuovo.setFine_Validita(pacchetto.getFine_Validità());
		nuovo.setId(pacchetto.getId());
		nuovo.setInizio_Validita(pacchetto.getInizio_Validità());
		nuovo.setHotels(convertListaHotelToDTO(pacchetto.getHotels()));
		nuovo.setEscursioni(convertListaEscursioniToDTO(pacchetto.getEscursioni()));
		nuovo.setAereiAndata(convertListaAereiAndataToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		nuovo.setAereiRitorno(convertListaAereiRitornoToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		UtenteDTO dipendente = gestioneUtente.getUtenteDTO(pacchetto.getDipendente().getUsername());
		nuovo.setDipendente(dipendente);
		return nuovo;
	}

/*
 * Metodi per la conversione di liste di oggetti del database
 */
	private List<Escursione> covertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = new Escursione(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	
	private List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
		ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
		for(int i=0;i<lista.size();i++){
			EscursioneDTO nuova = convertToDTO(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<EscursioneDTO> escursioni = listaEscursioni;
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
	
	private List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista, String destinazionePacchetto){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getAtterraggio().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	
	private List<AereoDTO> convertListaAereiRitornoToDTO(List<Aereo> lista, String destinazionePacchetto){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getDecollo().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	
	private List<AereoDTO> convertListaAereiToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAerei = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAerei.add(nuovo);
		}
		List<AereoDTO> aerei = listaAerei;
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
	
	private List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0;i<lista.size();i++){
			HotelDTO nuovo = convertToDTO(lista.get(i));
			listaHotel.add(nuovo);
		}
		List<HotelDTO> hotel = listaHotel;
		return hotel;
	}
	
	private List<PacchettoDTO> convertListaPacchettiToDTO(List<Pacchetto> lista){
		ArrayList<PacchettoDTO> pacchetti = new ArrayList<PacchettoDTO>();
		for (int i=0;i<lista.size();i++){
			pacchetti.add(convertToDTO(lista.get(i)));
		}
		List<PacchettoDTO> listaPacchetti = pacchetti;
		return listaPacchetti;
	}



	

	

	

}
