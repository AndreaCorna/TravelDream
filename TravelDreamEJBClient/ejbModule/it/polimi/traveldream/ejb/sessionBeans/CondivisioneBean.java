package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;

import javax.ejb.Local;

@Local
public interface CondivisioneBean {

	CondivisioneDTO mostraCondivisione(String link);

}
