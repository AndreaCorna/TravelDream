package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;


import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePrenotazioneBean {
	/**
	 * Il metodo ritorna le prenotazioni inerenti ad un pacchetto dell'utente attivo.
	 * @return lista di Prenotazione_PacchettoDTO
	 */
	List<Prenotazione_PacchettoDTO> getPrenotazioni();

	/**
	 * Il metodo inserisce nel database una prenotazione relativa ad un pacchetto.
	 * @param prenotazione - la prenotazione da salvare
	 */
	void inserisciPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione);
	
	/**
	 * Il metodo ritorna le prenotazioni inerenti ad un pacchetto dell'utente attivo.
	 * @return lista di Prenotazione_PacchettoDTO
	 */
	List<Prenotazione_PacchettoDTO> getListaPrenotazioni();

	/**
	 * Il metodo ritorna le prenotazioni riferite a viaggi creati dall'utente attivo.
	 * @return lista di Prenotazione_PacchettoDTO
	 */
	List<Prenotazione_ViaggioDTO> getListaPrenotazioniViaggio();

	/**
	 * Il metodo permette di aggiornare la prenotazione di un pacchetto.
	 * @param prenotazione - la prenotazione da aggiornare.
	 */
	void aggiornaPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione);
}
