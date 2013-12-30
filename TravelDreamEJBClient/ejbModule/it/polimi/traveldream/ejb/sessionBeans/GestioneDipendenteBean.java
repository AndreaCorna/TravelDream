package it.polimi.traveldream.ejb.sessionBeans;

import java.util.List;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import javax.ejb.Local;

@Local
public interface GestioneDipendenteBean {

	void creaDipendente(UtenteDTO utenteDTO);

	List<UtenteDTO> getListaDipendenti();

	void eliminaDipendente(UtenteDTO utenteDTO);




}
