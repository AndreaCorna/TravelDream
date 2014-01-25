package it.polimi.traveldream.ejb.sessionBeans;



import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
import it.polimi.traveldream.ejb.entities.Utente;


import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session bean che implementa le funzioni per la gestione della prenotazione di un viaggio o di un
 * pacchetto offerto dall'agenzia.
 * @author Alessandro Brunitti - Andrea Corna
 *
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
    	
    }

    /**
     * getter della lista delle prenotazioni
     */
	@Override
	public List<Prenotazione_PacchettoDTO> getPrenotazioni() {
		return null;
	}

	/**
	 * Metodo che si occupa data la prenotazione di costruire la prenotazione del pacchetto 
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public void inserisciPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione) {
		Prenotazione_Pacchetto nuova = new Prenotazione_Pacchetto();
		nuova.setAereo1(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo2(em.find(Aereo.class,prenotazione.getAereoRitorno().getId()));
		nuova.setData(prenotazione.getData());
		nuova.setHotel(em.find(Hotel.class,prenotazione.getHotel().getId()));
		nuova.setPacchetto(em.find(Pacchetto.class,prenotazione.getPacchetto().getId()));
		nuova.setUtente(em.find(Utente.class, prenotazione.getUtente().getUsername()));
		nuova.setEscursioni(ConverterDTO.convertListaEscursioni(prenotazione.getEscursioni()));
		nuova.setDataCheckInHotel(prenotazione.getCheckInHotel());
		nuova.setDataCheckOutHotel(prenotazione.getCheckOutHotel());
		nuova.setNumeroPersone(prenotazione.getNumeroPersone());
		em.persist(nuova);
		em.flush();
		nuova = em.find(Prenotazione_Pacchetto.class, nuova.getId());
		prenotazione.setId(nuova.getId());
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public void aggiornaPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione) {
		Prenotazione_Pacchetto nuova = em.find(Prenotazione_Pacchetto.class,prenotazione.getId());
		System.out.println(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo1(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo2(em.find(Aereo.class,prenotazione.getAereoRitorno().getId()));
		nuova.setData(prenotazione.getData());
		nuova.setHotel(em.find(Hotel.class,prenotazione.getHotel().getId()));
		nuova.setPacchetto(em.find(Pacchetto.class,prenotazione.getPacchetto().getId()));
		nuova.setUtente(em.find(Utente.class, prenotazione.getUtente().getUsername()));
		nuova.setEscursioni(ConverterDTO.convertListaEscursioni(prenotazione.getEscursioni()));
		nuova.setDataCheckInHotel(prenotazione.getCheckInHotel());
		nuova.setDataCheckOutHotel(prenotazione.getCheckOutHotel());
		nuova.setNumeroPersone(prenotazione.getNumeroPersone());
		em.merge(nuova);
	}
	
	/**
	 * metodo che permette di recuperare le prenotazioni relative ai pacchetti prenotati dell'utente che richiama il metodo chiamante
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_PacchettoDTO> getListaPrenotazioni() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		Utente utenteOnline = em.find(Utente.class, idUtenteOnline);
		List<Prenotazione_Pacchetto> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Pacchetto a WHERE a.utente =:nome")
			    .setParameter("nome", utenteOnline)
			    .getResultList();
		List<Prenotazione_PacchettoDTO> listaUtenti = ConverterDTO.convertListaUtentiOnlineToDTO(prenotazioniUtente, utenteOnline.getUsername());
		return listaUtenti;
	}
	
	/**
	 * metodo che permette di recuperare le prenotazioni relative ai viaggi prenotati dell'utente che richiama il metodo chiamante
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_ViaggioDTO> getListaPrenotazioniViaggio() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		Utente utenteOnline = em.find(Utente.class, idUtenteOnline);
		List<Prenotazione_Viaggio> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Viaggio a WHERE a.utente =:nome")
			    .setParameter("nome", utenteOnline)
			    .getResultList();
		List<Prenotazione_ViaggioDTO> listaUtenti = ConverterDTO.convertListaUtentiOnlineViaggiToDTO(prenotazioniUtente, utenteOnline.getUsername());
		return listaUtenti;
	}

	
		
}
