package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;

import javax.ejb.Local;

@Local
public interface CondivisioneBean {

	CondivisioneDTO mostraCondivisione(String link);

	void creaCondivisione(CondivisioneDTO condivisione, Prenotazione_PacchettoDTO prenotazione);

}
