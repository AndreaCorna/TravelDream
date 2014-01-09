package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePrenotazioneBean {
	

	List<Prenotazione_PacchettoDTO> getPrenotazioni();

	List<AereoDTO> getAereo(String Città);

	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio,
			Date inizioValidita, Date fineValidita);
}
