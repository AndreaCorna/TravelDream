package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import javax.ejb.Local;

@Local
public interface GestioneComponenteBean {

	/**
	 * Il metodo permette di aggiungere un nuovo aereo al database
	 * @param aereo - DTO contenente le informazioni del nuovo aereo
	 */
	void aggiungiAereoDB(AereoDTO aereo);

	/**
	 * Il metodo verifica che esista o meno di un aereo controllando l'id
	 * @param id - id da verificare
	 * @return <true> se esiste, <false> altrimenti
	 */
	boolean esisteAereo(String id);

	/**
	 * Aggiunge un nuovo hotel al database
	 * @param hotel - DTO contenente tutte le informazioni
	 */
	void aggiungiHotelDB(HotelDTO hotel);

	/**
	 * Il metodo verifica che esista o meno di un hotel controllando l'id
	 * @param id - id da verificare
	 * @return <true> se esiste, <false> altrimenti
	 */
	boolean esisteHotel(String id);
	
	/**
	 Il metodo verifica che esista o meno di un'escursione controllando l'id
	 * @param id - id da verificare
	 * @return <true> se esiste, <false> altrimenti
	 */
	boolean esisteEscursione(String id);

	/**
	 * Il metodo aggiunge una nuova escursione al database
	 * @param escursione - DTO contenente tutte le informazioni dell'escursione
	 */
	void aggiungiEscursioneDB(EscursioneDTO escursione);

	/**
	 * Ritorna un oggetto DTO contenente le informazioni dell'aereo con l'id indicato
	 * @param id - id dell'aereo da caricare
	 * @return DTO con le informazioni dell'aereo identificato dall'id
	 */
	AereoDTO getAereoById(String id);

	/**
	 * Il metodo modifica un aereo presente nel database
	 * @param aereo - DTO contenente le modifiche
	 */
	void modificaAereo(AereoDTO aereo);

	/**
	 * Ritorna un oggetto DTO contenente le informazioni dell'hotel con l'id indicato
	 * @param id - id dell'aereo da caricare
	 * @return DTO con le informazioni dell'hotel identificato dall'id
	 */
	HotelDTO getHotelById(String id);

	/**
	 * Il metodo modifica un hotel presente nel database
	 * @param hotel - DTO contenente tutte le modifiche
	 */
	void modificaHotel(HotelDTO hotel);

	/**
	 * Ritorna un oggetto DTO contenente le informazioni dell'escursione con l'id indicato
	 * @param id - id dell'aereo da caricare
	 * @return DTO con le informazioni dell'escursione identificata dall'id
	 */
	EscursioneDTO getEscursioneById(String id);

	/**
	 * Il metodo modifica un'escursione presente nel database
	 * @param escursione - DTO contenente tutte le modifiche
	 */
	void modificaEscursione(EscursioneDTO escursione);

	/**
	 * Il metodo setta a 0 il bit di valido dell'aereo
	 * @param aereo - aereo da eliminare
	 */
	void eliminaAereo(AereoDTO aereo);

	/**
	 * Il metodo setta a 0 il bit di valido dell'hotel
	 * @param hotel - hotel da eliminare
	 */
	void eliminaHotel(HotelDTO hotel);

	/**
	 * Il metodo setta a 0 il bit di valido dell'escursione
	 * @param escursione - escursione da eliminare
	 */
	void eliminaEscursione(EscursioneDTO escursione);

	

}
