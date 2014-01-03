package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePacchettoBean {

	List<AereoDTO> getListaAerei();
	
	List<HotelDTO> getListaHotel();

	List<HotelDTO> getListaHotel(String citta);

	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita);

	List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date inizioValidita, Date fineValidita);

}
