package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface GestioneViaggioBean {

	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date dataPartenza);

	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio,
			Date partenza, String cittaPartenza);

	List<AereoDTO> getListaAereiRitorno(String cittaDecollo,
			Date partenza, String cittaPartenza);

	void creaViaggio(Prenotazione_ViaggioDTO viaggio, int modalita);

	List<HotelDTO> getListaHotel(String destinazione, Date dataAndata,
			Date dataRitorno);

	}
