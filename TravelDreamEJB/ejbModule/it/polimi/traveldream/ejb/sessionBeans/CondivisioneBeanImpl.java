package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
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

   /**
    * metodo che riceve il link inserito da un utente nella pagina principale
    * e restituisce la condivisione relativa a quel link 
    */
	@Override
	public CondivisioneDTO mostraCondivisione(String link) {
		CondivisioneDTO condivisione = new CondivisioneDTO();
		Condivisione dato = em.find(Condivisione.class, link);
		condivisione.setData(dato.getData());
		condivisione.setLink(dato.getLink());
		condivisione.setUtente(ConverterDTO.convertToDTO(dato.getUtente()));
		condivisione.setId_Prenotazione(dato.getPrenotazionePacchetto().getId());
		Prenotazione_PacchettoDTO prenotazione = 
				ConverterDTO.convertToDTO(em.find(Prenotazione_Pacchetto.class,dato.getPrenotazionePacchetto().getId()));
		condivisione.setPrenotazione(prenotazione);
		return condivisione;
		
	}
	
	/**
	 * Metodo che crea la condivisione data la condivisione dto e la prenotazione
	 */
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

	

}
