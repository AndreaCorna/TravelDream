package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Condivisione;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
import it.polimi.traveldream.ejb.entities.Utente;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Session Bean implementation class GestionePrenotazioneImpl
 */
@Stateless
public class GestionePrenotazioneBeanImpl implements it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean {


	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	@EJB
	private GestioneUtenteBean gestioneUtente;
    
	
    /**
     * Default constructor. 
     */
    public GestionePrenotazioneBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Prenotazione_PacchettoDTO> getPrenotazioni() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inserisciPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione) {
		Prenotazione_Pacchetto nuova = new Prenotazione_Pacchetto();
		nuova.setAereo1(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo2(em.find(Aereo.class,prenotazione.getAereoRitorno().getId()));
		nuova.setData(prenotazione.getData());
		nuova.setHotel(em.find(Hotel.class,prenotazione.getHotel().getId()));
		nuova.setPacchetto(em.find(Pacchetto.class,prenotazione.getPacchetto().getId()));
		nuova.setUtente(em.find(Utente.class, prenotazione.getUtente().getUsername()));
		nuova.setEscursioni(convertListaEscursioni(prenotazione.getEscursioni()));
		nuova.setDataCheckInHotel(prenotazione.getCheckInHotel());
		nuova.setDataCheckOutHotel(prenotazione.getCheckOutHotel());
		em.persist(nuova);
		em.flush();
		nuova = em.find(Prenotazione_Pacchetto.class, nuova.getId());
		prenotazione.setId(nuova.getId());
		
	}
	
	private List<Escursione> convertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = new Escursione(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	



	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_PacchettoDTO> getListaPrenotazioni() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		Utente utenteOnline = em.find(Utente.class, idUtenteOnline);
		List<Prenotazione_Pacchetto> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Pacchetto a WHERE a.utente =:nome")
			    .setParameter("nome", utenteOnline)
			    .getResultList();
		List<Prenotazione_PacchettoDTO> listaUtenti = convertListaUtentiOnlineToDTO(prenotazioniUtente, utenteOnline.getUsername());
		return listaUtenti;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_ViaggioDTO> getListaPrenotazioniViaggio() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		Utente utenteOnline = em.find(Utente.class, idUtenteOnline);
		List<Prenotazione_Viaggio> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Viaggio a WHERE a.utente =:nome")
			    .setParameter("nome", utenteOnline)
			    .getResultList();
		List<Prenotazione_ViaggioDTO> listaUtenti = convertListaUtentiOnlineViaggiToDTO(prenotazioniUtente, utenteOnline.getUsername());
		return listaUtenti;
	}

	
	/*
	 * Metodi privati per la conversione di un oggetto prelevato dal database in un oggetto che verrà inviato alla view.
	 */
	
	private List<Prenotazione_ViaggioDTO> convertListaUtentiOnlineViaggiToDTO(List<Prenotazione_Viaggio> lista, String idUtenteOnline){
		ArrayList<Prenotazione_ViaggioDTO> listaPacchettiPrenotati = new ArrayList<Prenotazione_ViaggioDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getUtente().getUsername().toLowerCase().equals(idUtenteOnline.toLowerCase()))
			{	Prenotazione_ViaggioDTO nuovo = convertToDTO(lista.get(i));
				listaPacchettiPrenotati.add(nuovo);
			}
		}
		List<Prenotazione_ViaggioDTO> prenotazioni = listaPacchettiPrenotati;
		return prenotazioni;
	}
	
	private List<Prenotazione_PacchettoDTO> convertListaUtentiOnlineToDTO(List<Prenotazione_Pacchetto> lista, String idUtenteOnline){
		ArrayList<Prenotazione_PacchettoDTO> listaPacchettiPrenotati = new ArrayList<Prenotazione_PacchettoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getUtente().getUsername().toLowerCase().equals(idUtenteOnline.toLowerCase()))
			{	Prenotazione_PacchettoDTO nuovo = convertToDTO(lista.get(i));
				listaPacchettiPrenotati.add(nuovo);
			}
		}
		List<Prenotazione_PacchettoDTO> prenotazioni = listaPacchettiPrenotati;
		return prenotazioni;
	}
		
		private Prenotazione_ViaggioDTO convertToDTO(Prenotazione_Viaggio prenotazione){
			Prenotazione_ViaggioDTO nuovo = new Prenotazione_ViaggioDTO();
			nuovo.setId(prenotazione.getId());
			if (prenotazione.getData()!=null)
				nuovo.setData(prenotazione.getData());
			if(prenotazione.getEscursioni()!=null)
				nuovo.setEscursioni(convertListaEscursioniToDTO(prenotazione.getEscursioni()));
			if (prenotazione.getAereo1()!=null)
				nuovo.setAereo(convertAereoToDTO(prenotazione.getAereo1()));                //CONTINUARE DA QUI
			if (prenotazione.getAereo2()!=null)
				nuovo.setAereoRitorno(convertAereoRitornoToDTO(prenotazione.getAereo2()));
			if (prenotazione.getHotel()!=null)
				nuovo.setHotel(convertToDTO(prenotazione.getHotel()));
			nuovo.setUtente(convertToDTO(prenotazione.getUtente()));
			
			return nuovo;
		}
		
		private Prenotazione_PacchettoDTO convertToDTO(Prenotazione_Pacchetto prenotazione){
			Prenotazione_PacchettoDTO nuovo = new Prenotazione_PacchettoDTO();
			nuovo.setId(prenotazione.getId());
			nuovo.setData(prenotazione.getData());
			nuovo.setCondivisioni(convertListaCondivisioniToDTO(prenotazione.getCondivisioni()));
			nuovo.setEscursioni(convertListaEscursioniToDTO(prenotazione.getEscursioni()));
			nuovo.setAereo(convertAereoToDTO(prenotazione.getAereo1()));                //CONTINUARE DA QUI
			nuovo.setAereoRitorno(convertAereoRitornoToDTO(prenotazione.getAereo2()));
			nuovo.setHotel(convertToDTO(prenotazione.getHotel()));
			nuovo.setUtente(convertToDTO(prenotazione.getUtente()));
			nuovo.setPacchetto(convertToDTO(prenotazione.getPacchetto()));
			
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
			return nuovo;
		}
		
		private AereoDTO convertAereoRitornoToDTO(Aereo aereo){
			AereoDTO nuovo = new AereoDTO();
			nuovo.setCittaAtterraggio(aereo.getAtterraggio());
			nuovo.setCittaDecollo(aereo.getDecollo());
			nuovo.setCosto(aereo.getCosto());
			nuovo.setData(aereo.getData());
			nuovo.setId(aereo.getId());
			nuovo.setPostiDisponibili(aereo.getPosti_Disponibili());
			return nuovo;
		}
		
		private AereoDTO convertAereoToDTO(Aereo aereo){
			AereoDTO nuovo = new AereoDTO();
			nuovo.setCittaAtterraggio(aereo.getAtterraggio());
			nuovo.setCittaDecollo(aereo.getDecollo());
			nuovo.setCosto(aereo.getCosto());
			nuovo.setData(aereo.getData());
			nuovo.setId(aereo.getId());
			nuovo.setPostiDisponibili(aereo.getPosti_Disponibili());
			return nuovo;
		}
		
		private List<CondivisioneDTO> convertListaCondivisioniToDTO(List<Condivisione> lista){
			ArrayList<CondivisioneDTO> listaCondivisione = new ArrayList<CondivisioneDTO>();
			for(int i=0;i<lista.size();i++){
					CondivisioneDTO nuovo = convertToDTO(lista.get(i));
					listaCondivisione.add(nuovo);
	
			}
			List<CondivisioneDTO> condivisioni = listaCondivisione;
			return condivisioni;
		}
		
		private CondivisioneDTO convertToDTO(Condivisione condivisione){
			CondivisioneDTO nuovo = new CondivisioneDTO();
			nuovo.setLink(condivisione.getLink());
			nuovo.setData(condivisione.getData());
			nuovo.setId_Prenotazione(condivisione.getPrenotazionePacchetto().getId());
			nuovo.setUtente(convertToDTO(condivisione.getUtente()));
			return nuovo;
		}
		
		private UtenteDTO convertToDTO(Utente utente){
			UtenteDTO nuovo = new UtenteDTO();
			nuovo.setEmail(utente.getEmail());
			nuovo.setUsername(utente.getUsername());
			nuovo.setTelefono(utente.getTelefono());
			nuovo.setPassword(utente.getPassword());
			nuovo.setCodiceFiscale(utente.getAnagrafica().getCf());
			nuovo.setNome(utente.getAnagrafica().getNome());
			nuovo.setDataNascita(utente.getAnagrafica().getData_Nascita());
			nuovo.setLuogoNascita(utente.getAnagrafica().getLuogo_Nascita());
			nuovo.setResidenza(utente.getAnagrafica().getResidenza());
			return nuovo;
		}
		
		private HotelDTO convertToDTO(Hotel hotel){
			HotelDTO nuovo = new HotelDTO();
			nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
			nuovo.setId(hotel.getId());
			nuovo.setCitta(hotel.getCitta());
			nuovo.setNome(hotel.getNome());
			nuovo.setCostoGiornaliero(hotel.getCostoGiornaliero());
			nuovo.setDataFine(hotel.getDataCheckOut());
			nuovo.setDataInizio(hotel.getDataCheckIn());
			Integer value = new Integer(hotel.getStelle());
			nuovo.setRating(value);
			return nuovo;
		}

		//metodi convertTODTO
		
		
		private List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
			ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
			for(int i=0;i<lista.size();i++){
				EscursioneDTO nuova = convertToDTO(lista.get(i));
				listaEscursioni.add(nuova);
			}
			List<EscursioneDTO> escursioni = listaEscursioni;
			return escursioni;
		}
		
		private List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista, String destinazionePacchetto){
			ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
			for(int i=0;i<lista.size();i++){
				if(lista.get(i).getAtterraggio().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
					AereoDTO nuovo = convertAereoToDTO(lista.get(i));
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
					AereoDTO nuovo = convertAereoRitornoToDTO(lista.get(i));
					listaAereiAndata.add(nuovo);
				}
			}
			List<AereoDTO> aerei = listaAereiAndata;
			return aerei;
		}
		/*
		private List<AereoDTO> convertToDTO(Aereo lista){
			ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
			for(int i=0;i<lista.size();i++){
					AereoDTO nuovo = convertToDTO(lista.get(i));
					listaAereiAndata.add(nuovo);
	
			}
			List<AereoDTO> aerei = listaAereiAndata;
			return aerei;
		}
		*/

		
		private List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
			ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
			for(int i=0;i<lista.size();i++){
				HotelDTO nuovo = convertToDTO(lista.get(i));
				listaHotel.add(nuovo);
			}
			List<HotelDTO> hotel = listaHotel;
			return hotel;
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
		/*
		private List<PacchettoDTO> convertListaPacchettiToDTO(List<Pacchetto> lista){
			ArrayList<PacchettoDTO> pacchetti = new ArrayList<PacchettoDTO>();
			for (int i=0;i<lista.size();i++){
				pacchetti.add(convertToDTO(lista.get(i)));
			}
			List<PacchettoDTO> listaPacchetti = pacchetti;
			return listaPacchetti;
		}*/
}
