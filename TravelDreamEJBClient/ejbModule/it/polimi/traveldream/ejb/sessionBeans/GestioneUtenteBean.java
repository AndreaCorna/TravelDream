package it.polimi.traveldream.ejb.sessionBeans;

import java.util.List;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import javax.ejb.Local;

@Local
public interface GestioneUtenteBean {

	/**
	 * Il metodo permette la registrazione di un nuovo utente
	 * @param utente - DTO contenente le informazioni del nuovo utente
	 */
	void aggiungiNuovoUtente(UtenteDTO utente);

	/**
	 * Il metodo verifica l'utilizzo di un username
	 * @param username - lo username da verificare
	 * @return <true> se gia usato, altrimenti <false>
	 */
	boolean esisteUsername(String username);
	
	/**
	 * Il metodo permette di modificare il profilo dell'utente
	 * @param utente - DTO contenente tutte le informazioni
	 */
	void modificaProfiloUtente(UtenteDTO utente);
	
	/**
	 * Il metodo elimina un utente dal database
	 * @param string - lo username dell'utente da eliminare
	 */
	void eliminaProfilo(String string);

	/**
	 * Il metodo ritorna le informazioni dell'utente attivo nella sessione
	 * @return DTO con le informazioni dell'utente attivo
	 */
	UtenteDTO getUtenteDTO();

	/**
	 * Verifica per l'unicita del codice fiscale
	 * @param codiceFiscale - codice fiscale da controllare
	 * @return <true> se esiste gia, <false> altrimenti
	 */
	boolean esisteCodiceFiscale(String codiceFiscale);

	/**
	 * Ritorna la lista di tutti gli  utenti
	 * @return lista degli utenti
	 */
	List<UtenteDTO> getListaUtenti();

	/**
	 * Il metodo ritorna la lista dei soli utenti base
	 * @return la lista degli utenti base
	 */
	List<UtenteDTO> getListaUtentiBase();

	/**
	 * Ritorna il DTO contenente le informazioni del dipendente
	 * @param dipendente - id del dipendente
	 * @return DTO con le informazioni
	 */
	UtenteDTO getUtenteDTO(String dipendente);
	/**
	 * Il metodo verifica che l'utente sia solo un utente e non un dipendente
	 */
	boolean isUtente();

	
}
