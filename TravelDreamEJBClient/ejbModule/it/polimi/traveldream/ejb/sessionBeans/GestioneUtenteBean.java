package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import javax.ejb.Local;

@Local
public interface GestioneUtenteBean {

	void aggiungiNuovoUtente(UtenteDTO utente);

}