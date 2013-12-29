package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
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
 * Session Bean implementation class CondivisioneBeanImpl
 */
@Stateless
public class CondivisioneBeanImpl implements CondivisioneBean {


	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public CondivisioneBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public CondivisioneDTO mostraCondivisione(String link) {
		CondivisioneDTO condivisione = new CondivisioneDTO();
		Condivisione dato = em.find(Condivisione.class, link);
		Utente utente = em.find(Utente.class, dato.getUtente().getUsername());
		condivisione.setData(dato.getData());
		condivisione.setLink(dato.getLink());
		condivisione.setUtente(convertiUtenteInDTO(utente));
		return condivisione;
		
	}
	
	@Override
	public void creaCondivisione(CondivisioneDTO condivisione){
		Condivisione nuova = new Condivisione(condivisione);
		nuova.setPrenotazionePacchetto(em.find(Prenotazione_Pacchetto.class, 
				condivisione.getId_Prenotazione()));
		nuova.setUtente(em.find(Utente.class, condivisione.getUtente().getUsername()));
		em.persist(nuova);
	}
	
	/**
	 * Metodo per convertire un'entità utente presente nel database in un oggetto DTO
	 * @param utente - l'utente ottenuto dal database
	 * @return utenteDTO - data transfer object
	 */
	private UtenteDTO convertiUtenteInDTO(Utente utente){
		UtenteDTO nuovo = new UtenteDTO();
		nuovo.setEmail(utente.getEmail());
		nuovo.setUsername(utente.getUsername());
		nuovo.setTelefono(utente.getTelefono());
		return nuovo;
	}

}
