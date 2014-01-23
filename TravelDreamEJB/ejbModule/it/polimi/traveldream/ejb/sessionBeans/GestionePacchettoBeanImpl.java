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
       
    }
    
    /**
     * Metodo che restituisce la lista dei pacchetti prenotabili
     */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<PacchettoDTO> getListaPacchetti() {
		Date data = new Date();
		List<Pacchetto> pacchettiDB = em.createQuery("SELECT p FROM Pacchetto p WHERE p.valido=1 and p.fine_Validita > :data")
				.setParameter("data", data, TemporalType.TIMESTAMP)
				.getResultList();
		List<PacchettoDTO> pacchetti = ConverterDTO.convertListaPacchettiToDTO(pacchettiDB);
		return pacchetti;
		
	}

	/**
	 * Metodo che restituisce la lista degli aerei prenotabili
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findValidi",Aereo.class).getResultList();
	   	List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiToDTO(aereiDB);
    	return listaAerei;
	}
	
	/**
	 * Metodo che restituisce la lista degli hotel prenotabili
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(){
		List<Hotel> hotels = em.createNamedQuery("Hotel.findValidi", Hotel.class).getResultList();
		List<HotelDTO> listaHotels = ConverterDTO.convertListaHotelToDTO(hotels);
		return listaHotels;
	}
	
	/**
	 * Metodo che restituisce la lista delle escursioni prenotabili
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(String citta) {
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.citta =:nome and h.valido=1")
			    .setParameter("nome", citta).getResultList();
		List<HotelDTO> listaHotels = ConverterDTO.convertListaHotelToDTO(hotels);
		return listaHotels;
	}
	
	/**
	 * Metodo che restituisce la lista degli aerei prenotabili data la cittï¿½ di destinazione
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.valido=1 and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", cittaAtterraggio)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiAndataToDTO(aerei, cittaAtterraggio);
		return listaAerei;
	}

	/**
	 * Metodo che restituisce la lista degli aerei prenotabili data la cittï¿½ di destinazione
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date inizioValidita, Date fineValidita) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.decollo =:nome and a.valido=1 and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", cittaDecollo)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiRitornoToDTO(aerei, cittaDecollo);
		return listaAerei;
	}
	
	/**
	 * Metodo che restituisce la lista delle escursioni prenotabili
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni() {
		List<Escursione> escursioniDB = em.createNamedQuery("Escursione.findValidi", Escursione.class).getResultList();
	   	List<EscursioneDTO> listaEscursioni = ConverterDTO.convertListaEscursioniToDTO(escursioniDB);
    	return listaEscursioni;
	}

	/**
	 * Metodo che restituisce la lista delle escursioni prenotabili dato il luogo dove avverranno e la data dell'escursione
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date inizioValidita, Date fineValidita) {
		List<Escursione> escursioniDB = em.createQuery("SELECT e FROM Escursione e WHERE e.luogo =:nome and e.valido=1 and e.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", destinazione)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		List<EscursioneDTO> listaEscursioni = ConverterDTO.convertListaEscursioniToDTO(escursioniDB);
    	return listaEscursioni;
	}
	
	/**
	 * Metodo che restituisce gli aerei di andata prenotabili che corrispondono ai parametri inseriti
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndataDisp(Date partenza, Date ritorno,PacchettoDTO pacchetto, Integer value) {
		ArrayList<Aereo> aerei = new ArrayList<Aereo>();
		for(AereoDTO aereo:pacchetto.getAereiAndata()){
			Aereo aereoDB = em.find(Aereo.class,aereo.getId());
			if(isAfterPartenza(aereoDB, partenza) && isBeforeRitorno(aereoDB,ritorno) 
					&& haPostiDisponibili(aereoDB,partenza,ritorno,value)){
				aerei.add(em.find(Aereo.class,aereo.getId()));
			}
		}
		List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiToDTO(aerei);
		return listaAerei;
	}
	
	/**
	 * Metodo che restituisce gli aerei di ritorno prenotabili che corrispondono ai parametri inseriti
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitornoDisp(Date partenza, Date ritorno,PacchettoDTO pacchetto, Integer value) {
		ArrayList<Aereo> aerei = new ArrayList<Aereo>();
		for(AereoDTO aereo:pacchetto.getAereiRitorno()){
			Aereo aereoDB = em.find(Aereo.class,aereo.getId());
			if(isAfterPartenza(aereoDB, partenza) && isBeforeRitorno(aereoDB,ritorno) 
					&& haPostiDisponibili(aereoDB,partenza,ritorno,value)){
				aerei.add(em.find(Aereo.class,aereo.getId()));
			}
		}
		List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiToDTO(aerei);
		return listaAerei;
	}
	
	/**
	 * Metodo che restituisce gli hotel prenotabili che corrispondono ai parametri dati
	 */
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
		List<HotelDTO> listaHotel = ConverterDTO.convertListaHotelToDTO(hotels);
		return listaHotel;
	}
	
	/**
	 * metodo che dato un albergo verifica se questo ha camere disponibili tra una certa data di partenza e di ritorno
	 * @param hotel di cui si vuole verificare la disponibilitï¿½
	 * @param partenza data di checkin nell'hotel
	 * @param ritorno data di checkout nell'hotel
	 * @return
	 */
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
	
	/** 
	 * metodo che verifica se la data di ritorno è dopo quella della partenza di un dato aereo
	 * @param aereo
	 * @param partenza
	 * @return true se il vincolo è rispettato
	 */
	private boolean isAfterPartenza(Aereo aereo, Date partenza){
		return (aereo.getData().after(partenza) || aereo.getData().equals(partenza));
	}
	
	/**
	 * Metood che verifica se la data di andata è prima di quella di ritorno di un dato aereo 
	 * @param aereo
	 * @param ritorno
	 * @return true se il vincolo è rispettato 
	 */
	private boolean isBeforeRitorno(Aereo aereo, Date ritorno){
		return (aereo.getData().before(ritorno) || aereo.getData().equals(ritorno));
	}
	
	/**
	 * Metodo che verifica se un dato aereo ha posti disponibili in un dato periodo
	 * @param aereo di cui si vuole saperne la disponibilita 
	 * @param partenza data in cui si desidera partire
	 * @param ritorno data in cui si desidera tornare 
	 * @param value numero di posti che si desidera prenotare
	 * @return un booleano che è true se vi sono posti disponibili
	 */
	@SuppressWarnings("unchecked")
	private boolean haPostiDisponibili(Aereo aereo,Date partenza, Date ritorno,Integer value){
		List<Prenotazione_Pacchetto> listaPrenotazioniPac = em.createQuery("SELECT p FROM Prenotazione_Pacchetto p WHERE ( p.aereo1 =:nome AND p.aereo1.valido=1)"
				+ " OR ( p.aereo2 =:nome AND p.aereo2.valido=1 ) "
				+ "AND (p.aereo1.data BETWEEN :andata AND :ritorno OR p.aereo2.data BETWEEN :andata AND :ritorno)")
				.setParameter("nome", aereo)
				.setParameter("andata",partenza)
				.setParameter("ritorno",ritorno).getResultList();
		int postiOccupati = 0;
		for(Prenotazione_Pacchetto prenotazione:listaPrenotazioniPac){
			postiOccupati = postiOccupati + prenotazione.getNumeroPersone();
		}
		List<Prenotazione_Viaggio> listaViaggi = em.createQuery("SELECT p FROM Prenotazione_Viaggio p WHERE ( p.aereo1 =:nome AND p.aereo1.valido=1)"
				+ " OR ( p.aereo2 =:nome AND p.aereo2.valido=1 ) "
				+ "AND (p.aereo1.data BETWEEN :andata AND :ritorno OR p.aereo2.data BETWEEN :andata AND :ritorno)")
				.setParameter("nome", aereo)
				.setParameter("andata",partenza)
				.setParameter("ritorno",ritorno).getResultList();
		postiOccupati = postiOccupati + listaViaggi.size();
		
	return (aereo.getPosti_Disponibili()-postiOccupati)>=value;
	}
	
	/**
	 * Metodo che inserisce il pacchetto all'interno del database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void creaPacchetto(PacchettoDTO pacchetto) {
		Pacchetto nuovoPacchetto = new Pacchetto();
		System.out.println("IN EJB"+pacchetto.getEscursioni());
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
	
	/**
	 * Metodo che si occupa di eliminare il pacchetto passato in ingresso
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void eliminaPacchetto(PacchettoDTO pacchetto) {
		Pacchetto eliminato = em.find(Pacchetto.class, pacchetto.getId());
		eliminato.setValido((byte)0);
		em.merge(eliminato);
	}
	
	/**
	 *  Metodo che si occupa di modificare il pacchetto passato in ingresso
	 */
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
	
	/**
	 *  Metodo che si occupa dato l'id di un dato pachetto verifica se questo esiste e ritorna true se esiste false altrimenti
	 */
	@Override
	public boolean esisteIdPacchetto(String id) {
		Integer value = new Integer(id);
		if (em.find(Pacchetto.class,value.intValue())!=null){
	           return true;
	        }
	        return false;
	}

	@Override
	public PacchettoDTO getPacchetto(String id) {
		Integer value = new Integer(id);
		PacchettoDTO pacchetto = ConverterDTO.convertToDTO(em.find(Pacchetto.class, value.intValue()));
		return pacchetto;
	}


 // Metodi per la conversione di liste di oggetti del database
 
	private List<Escursione> covertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = em.find(Escursione.class, lista.get(i).getId());
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
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
	
	
	
	
	
	private List<Hotel> convertListaHotel(List<HotelDTO> lista){
		ArrayList<Hotel> listaHotel = new ArrayList<Hotel>();
		for(int i=0;i<lista.size();i++){
			Hotel nuovo = em.find(Hotel.class, lista.get(i).getId());
			listaHotel.add(nuovo);
		}
		List<Hotel> hotel = listaHotel;
		return hotel;
	}
	
	
	
	

	

	



	

	

	

}
