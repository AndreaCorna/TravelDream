package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import javax.ejb.Local;

@Local
public interface GestioneComponenteBean {

	void aggiungiAereoDB(AereoDTO aereo);

	boolean esisteAereo(String id);

	void aggiungiHotelDB(HotelDTO hotel);

}
