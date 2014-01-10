package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePacchettoBean {

	List<AereoDTO> getListaAerei();
	
	List<HotelDTO> getListaHotel();
	
	List<EscursioneDTO> getListaEscursioni();

	List<HotelDTO> getListaHotel(String citta);

	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita);

	List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date inizioValidita, Date fineValidita);

	List<EscursioneDTO> getListaEscursioni(String destinazione,Date inizioValidita, Date fineValidita);

	void creaPacchetto(PacchettoDTO pacchetto);

	boolean esisteIdPacchetto(String id);

	List<PacchettoDTO> getListaPacchetti();

	void eliminaPacchetto(PacchettoDTO pacchetto);

}
