package it.polimi.traveldream.ejb.sessionBeans;

import java.util.List;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import javax.ejb.Local;

@Local
public interface GestioneDipendenteBean {

	/**
	 * Il metodo eleva un utente normale al ruolo di dipendente
	 * @param utenteDTO - DTO dell'utente da elevare
	 */
	void creaDipendente(UtenteDTO utenteDTO);

	/**
	 * Il metodo ritorna la lista dei dipendenti
	 * @return Lista di DTO
	 */
	List<UtenteDTO> getListaDipendenti();

	/**
	 * Il metodo elimina i privilegi di dipendente dall'utente
	 * @param utenteDTO - DTO dell'utente al quale eliminare i privilegi
	 */
	void eliminaDipendente(UtenteDTO utenteDTO);




}
