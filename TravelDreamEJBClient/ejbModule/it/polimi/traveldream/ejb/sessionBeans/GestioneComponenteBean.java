package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import javax.ejb.Local;

@Local
public interface GestioneComponenteBean {

	void aggiungiAereoDB(AereoDTO aereo);

	boolean esisteAereo(String id);

	void aggiungiHotelDB(HotelDTO hotel);

	boolean esisteHotel(String id);

	boolean esisteEscursione(String id);

	void aggiungiEscursioneDB(EscursioneDTO escursione);

	AereoDTO getAereoById(String id);

	void modificaAereo(AereoDTO aereo);

}
