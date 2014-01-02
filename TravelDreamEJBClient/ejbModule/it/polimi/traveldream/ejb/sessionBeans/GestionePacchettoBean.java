package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePacchettoBean {

	List<AereoDTO> getListaAerei();

	List<HotelDTO> getListaHotel(String citta);

}
