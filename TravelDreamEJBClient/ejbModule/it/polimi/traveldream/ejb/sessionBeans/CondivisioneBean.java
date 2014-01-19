package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;

import javax.ejb.Local;

@Local
public interface CondivisioneBean {
	/**
	 * Il metodo carica tutte le informazioni della condivisione che viene identificata con il link passato
	 * @param link - il link ricevuto che identifica la condivisione
	 * @return un DTO contenente tutte le informazioni della condivisione
	 */
	CondivisioneDTO mostraCondivisione(String link);

	/**
	 * Il metodo permette di aggiungere una condivisione al database
	 * @param condivisione - DTO con le informazioni della condivisione
	 * @param prenotazione - DTO con le informazioni del pacchetto condiviso
	 */
	void creaCondivisione(CondivisioneDTO condivisione, Prenotazione_PacchettoDTO prenotazione);

}
