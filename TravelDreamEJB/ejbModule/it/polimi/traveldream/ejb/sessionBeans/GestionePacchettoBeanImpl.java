package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
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
		List<Pacchetto> pacchettiDB = em.createNamedQuery("Pacchetto.findValidi", Pacchetto.class).getResultList();
		List<PacchettoDTO> pacchetti = convertListaPacchettiToDTO(pacchettiDB);
		return pacchetti;
		
	}

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findValidi",Aereo.class).getResultList();
	   	List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiToDTO(aereiDB);
    	return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(){
		List<Hotel> hotels = em.createNamedQuery("Hotel.findValidi", Hotel.class).getResultList();
		List<HotelDTO> listaHotels = convertListaHotelToDTO(hotels);
		return listaHotels;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(String citta) {
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.citta =:nome and h.valido=1")
			    .setParameter("nome", citta).getResultList();
		List<HotelDTO> listaHotels = convertListaHotelToDTO(hotels);
		return listaHotels;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.valido=1 and a.data BETWEEN :startDate AND :endDate")
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
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.decollo =:nome and a.valido=1 and a.data BETWEEN :startDate AND :endDate")
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
		List<Escursione> escursioniDB = em.createNamedQuery("Escursione.findValidi", Escursione.class).getResultList();
	   	List<EscursioneDTO> listaEscursioni = convertListaEscursioniToDTO(escursioniDB);
    	return listaEscursioni;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date inizioValidita, Date fineValidita) {
		List<Escursione> escursioniDB = em.createQuery("SELECT e FROM Escursione e WHERE e.luogo =:nome and e.valido=1 and e.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", destinazione)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		List<EscursioneDTO> listaEscursioni = convertListaEscursioniToDTO(escursioniDB);
    	return listaEscursioni;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndataDisp(Date partenza, Date ritorno,PacchettoDTO pacchetto) {
		ArrayList<Aereo> aerei = new ArrayList<Aereo>();
		for(AereoDTO aereo:pacchetto.getAereiAndata()){
			Aereo aereoDB = em.find(Aereo.class,aereo.getId());
			if(isAfterPartenza(aereoDB, partenza) && isBeforeRitorno(aereoDB,ritorno) 
					&& haPostiDisponibili(aereoDB,partenza,ritorno)){
				aerei.add(em.find(Aereo.class,aereo.getId()));
			}
		}
		List<AereoDTO> listaAerei = convertListaAereiToDTO(aerei);
		return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitornoDisp(Date partenza, Date ritorno,PacchettoDTO pacchetto) {
		ArrayList<Aereo> aerei = new ArrayList<Aereo>();
		for(AereoDTO aereo:pacchetto.getAereiRitorno()){
			Aereo aereoDB = em.find(Aereo.class,aereo.getId());
			if(isAfterPartenza(aereoDB, partenza) && isBeforeRitorno(aereoDB,ritorno) 
					&& haPostiDisponibili(aereoDB,partenza,ritorno)){
				aerei.add(em.find(Aereo.class,aereo.getId()));
			}
		}
		List<AereoDTO> listaAerei = convertListaAereiToDTO(aerei);
		return listaAerei;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotelDip(Date partenza, Date ritorno, PacchettoDTO pacchetto) {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		for(HotelDTO hotel:pacchetto.getHotels()){
			Hotel hotelDB = em.find(Hotel.class,hotel.getId());
			if(haCamereDisponibili(hotelDB,partenza,ritorno)){
				hotels.add(hotelDB);
			}
		}
		List<HotelDTO> listaHotel = convertListaHotelToDTO(hotels);
		return listaHotel;
	}
	
	@SuppressWarnings("unchecked")
	private boolean haCamereDisponibili(Hotel hotel, Date partenza, Date ritorno){
		List<Prenotazione_Pacchetto> prenotazioniPac = em.createQuery("SELECT p FROM Prenotazione_Pacchetto p "
				+ "WHERE p.dataCheckInHotel BETWEEN :andata AND :ritorno AND p.dataCheckOutHotel BETWEEN :andata AND :ritorno "
				+ "AND p.hotel =:hotel and p.hotel.valido=1")
				.setParameter("hotel", hotel)
				.setParameter("ritorno", ritorno)
				.setParameter("andata",partenza).getResultList();
		List<Prenotazione_Pacchetto> prenotazioniViaggi = em.createQuery("SELECT p FROM Prenotazione_Viaggio p "
				+ "WHERE p.dataCheckInHotel BETWEEN :andata AND :ritorno AND p.dataCheckOutHotel BETWEEN :andata AND :ritorno "
				+ "AND p.hotel =:hotel and p.hotel.valido=1")
				.setParameter("hotel", hotel)
				.setParameter("ritorno", ritorno)
				.setParameter("andata",partenza).getResultList();
		int camereOccupate = prenotazioniPac.size() + prenotazioniViaggi.size();
		return (hotel.getCamere_Disponibili()-camereOccupate)>0;
	}
	
	
	private boolean isAfterPartenza(Aereo aereo, Date partenza){
		return (aereo.getData().after(partenza) || aereo.getData().equals(partenza));
	}
	
	private boolean isBeforeRitorno(Aereo aereo, Date ritorno){
		return (aereo.getData().before(ritorno) || aereo.getData().equals(ritorno));
	}
	
	@SuppressWarnings("unchecked")
	private boolean haPostiDisponibili(Aereo aereo,Date partenza, Date ritorno){
		List<Prenotazione_Pacchetto> listaPrenotazioniPac = em.createQuery("SELECT p FROM Prenotazione_Pacchetto p WHERE ( p.aereo1 =:nome AND p.aereo1.valido=1)"
				+ " OR ( p.aereo2 =:nome AND p.aereo2.valido=1 ) "
				+ "AND (p.aereo1.data BETWEEN :andata AND :ritorno OR p.aereo2.data BETWEEN :andata AND :ritorno)")
				.setParameter("nome", aereo)
				.setParameter("andata",partenza)
				.setParameter("ritorno",ritorno).getResultList();
		int postiOccupati = 0;
		for(Prenotazione_Pacchetto prenotazione:listaPrenotazioniPac){
			postiOccupati = postiOccupati + prenotazione.getPacchetto().getNumeroPersone();
		}
		List<Prenotazione_Viaggio> listaViaggi = em.createQuery("SELECT p FROM Prenotazione_Viaggio p WHERE ( p.aereo1 =:nome AND p.aereo1.valido=1)"
				+ " OR ( p.aereo2 =:nome AND p.aereo2.valido=1 ) "
				+ "AND (p.aereo1.data BETWEEN :andata AND :ritorno OR p.aereo2.data BETWEEN :andata AND :ritorno)")
				.setParameter("nome", aereo)
				.setParameter("andata",partenza)
				.setParameter("ritorno",ritorno).getResultList();
		postiOccupati = postiOccupati + listaViaggi.size();
		
	return (aereo.getPosti_Disponibili()-postiOccupati)>0;
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
		nuovoPacchetto.setFine_Validita(pacchetto.getFine_Validita());
		nuovoPacchetto.setInizio_Validita(pacchetto.getInizio_Validita());
		nuovoPacchetto.setAerei(aerei);
		nuovoPacchetto.setDipendente(dipendente);
		nuovoPacchetto.setNumeroPersone(pacchetto.getNumeroPersone());
		nuovoPacchetto.setValido((byte)1);
		em.persist(nuovoPacchetto);
		em.flush();
		nuovoPacchetto = em.find(Pacchetto.class, nuovoPacchetto.getId());
		pacchetto.setId(nuovoPacchetto.getId());
		
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void eliminaPacchetto(PacchettoDTO pacchetto) {
		Pacchetto eliminato = em.find(Pacchetto.class, pacchetto.getId());
		eliminato.setValido((byte)0);
		em.merge(eliminato);
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaPacchetto(PacchettoDTO pacchetto) {
		Pacchetto modificato = em.find(Pacchetto.class, pacchetto.getId());
		List<Escursione> escursioni = covertListaEscursioni(pacchetto.getEscursioni());
		List<Hotel> hotel = convertListaHotel(pacchetto.getHotels());
		List<Aereo> aerei = convertListaAerei(pacchetto.getAereiAndata(),pacchetto.getAereiRitorno());
		modificato.setDescrizione(pacchetto.getDescrizione());
		modificato.setDestinazione(pacchetto.getDestinazione());
		modificato.setEscursioni(escursioni);
		modificato.setHotels(hotel);
		modificato.setFine_Validita(pacchetto.getFine_Validita());
		modificato.setInizio_Validita(pacchetto.getInizio_Validita());
		modificato.setAerei(aerei);
		modificato.setNumeroPersone(pacchetto.getNumeroPersone());
		em.merge(modificato);
		
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
		nuovo.setValido(aereo.getValido());
		return nuovo;
	}
	
	private HotelDTO convertToDTO(Hotel hotel){
		HotelDTO nuovo = new HotelDTO();
		nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCitta());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		nuovo.setCostoGiornaliero(hotel.getCostoGiornaliero());
		nuovo.setDataFine(hotel.getDataCheckOut());
		nuovo.setDataInizio(hotel.getDataCheckIn());
		nuovo.setValido(hotel.getValido());
		return nuovo;
	}
	
	private EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		nuovo.setValido(escursione.getValido());
		return nuovo;
	}
	
	private PacchettoDTO convertToDTO(Pacchetto pacchetto){
		PacchettoDTO nuovo = new PacchettoDTO();
		nuovo.setDescrizione(pacchetto.getDescrizione());
		nuovo.setDestinazione(pacchetto.getDestinazione());
		nuovo.setFine_Validita(pacchetto.getFine_Validita());
		nuovo.setId(pacchetto.getId());
		nuovo.setInizio_Validita(pacchetto.getInizio_Validita());
		nuovo.setHotels(convertListaHotelToDTO(pacchetto.getHotels()));
		nuovo.setEscursioni(convertListaEscursioniToDTO(pacchetto.getEscursioni()));
		nuovo.setAereiAndata(convertListaAereiAndataToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		nuovo.setAereiRitorno(convertListaAereiRitornoToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		UtenteDTO dipendente = gestioneUtente.getUtenteDTO(pacchetto.getDipendente().getUsername());
		nuovo.setDipendente(dipendente);
		nuovo.setNumeroPersone(pacchetto.getNumeroPersone());
		nuovo.setValido(pacchetto.getValido());
		return nuovo;
	}

/*
 * Metodi per la conversione di liste di oggetti del database
 */
	private List<Escursione> covertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = em.find(Escursione.class, lista.get(i).getId());
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
			Aereo nuovo = em.find(Aereo.class, listaAndata.get(i).getId());
			listaAerei.add(nuovo);
		}
		for(int i=0;i<listaRitorno.size();i++){
			Aereo nuovo = em.find(Aereo.class, listaRitorno.get(i).getId());
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
			Hotel nuovo = em.find(Hotel.class, lista.get(i).getId());
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
