package it.polimi.traveldream.ejb.sessionBeans;

import java.util.List;

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

	List<UtenteDTO> getListaUtenti();

	List<UtenteDTO> getListaUtentiBase();

	UtenteDTO getUtenteDTO(String dipendente);

	
}
