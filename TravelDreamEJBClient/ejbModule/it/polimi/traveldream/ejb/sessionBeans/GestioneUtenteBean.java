package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AnagraficaDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;

import javax.ejb.Local;

@Local
public interface GestioneUtenteBean {

	void aggiungiNuovoUtente(UtenteDTO utente);

	boolean esisteUsername(String username);
	
	void modificaProfiloUtente(UtenteDTO utente);
	
	void eliminaProfilo(String string);

	UtenteDTO getUtenteDTO();

	boolean esisteCodiceFiscale(String codiceFiscale);

	
}
