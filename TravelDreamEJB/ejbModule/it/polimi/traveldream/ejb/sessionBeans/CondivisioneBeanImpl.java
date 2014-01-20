package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Condivisione;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Utente;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Il session bean implementa tutti i metodi necessari per la gestione delle condivisioni presenti nel database.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Stateless
public class CondivisioneBeanImpl implements CondivisioneBean {


	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
    /**
     * Costruttore di default
     */
    public CondivisioneBeanImpl() {
       
    }

    
	@Override
	public CondivisioneDTO mostraCondivisione(String link) {
		CondivisioneDTO condivisione = new CondivisioneDTO();
		Condivisione dato = em.find(Condivisione.class, link);
		condivisione.setData(dato.getData());
		condivisione.setLink(dato.getLink());
		condivisione.setUtente(convertToDTO(dato.getUtente()));
		condivisione.setId_Prenotazione(dato.getPrenotazionePacchetto().getId());
		return condivisione;
		
	}
	
	@Override
	public void creaCondivisione(CondivisioneDTO condivisione, Prenotazione_PacchettoDTO prenotazione){
		Condivisione nuova = new Condivisione(condivisione);
		nuova.setPrenotazionePacchetto(em.find(Prenotazione_Pacchetto.class, 
				prenotazione.getId()));
		nuova.setUtente(em.find(Utente.class, condivisione.getUtente().getUsername()));
		nuova.setData(condivisione.getData());
		nuova.setLink(condivisione.getLink());
		em.persist(nuova);
	}

	/**
	 * Metodo privato che permette di convertire un entità Utente in un data trasfert object UtenteDTO
	 * @param utente - entità da convertire
	 * @return UtenteDTO 
	 */
	private UtenteDTO convertToDTO(Utente utente){
		UtenteDTO nuovo = new UtenteDTO();
		nuovo.setUsername(utente.getUsername());
		nuovo.setEmail(utente.getEmail());
		nuovo.setTelefono(utente.getTelefono());
		return nuovo;
	}

}
