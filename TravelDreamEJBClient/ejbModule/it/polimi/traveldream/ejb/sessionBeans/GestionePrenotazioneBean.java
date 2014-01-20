package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;


import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePrenotazioneBean {

	List<Prenotazione_PacchettoDTO> getPrenotazioni();

	void inserisciPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione);
	
	
	List<Prenotazione_PacchettoDTO> getListaPrenotazioni();

	List<Prenotazione_ViaggioDTO> getListaPrenotazioniViaggio();
}
