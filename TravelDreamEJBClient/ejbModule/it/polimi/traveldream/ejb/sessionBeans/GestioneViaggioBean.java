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

	public void prenotaViaggio();
	
	public List<AereoDTO> getListaAerei();
	
	
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date dataPartenza);
	
	public void mostraRiepilogo();

	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio,
			Date partenza);

	List<AereoDTO> getListaAereiRitorno(String cittaDecollo,
			Date partenza);

	public List<AereoDTO> aggiungiAereoViaggio(AereoDTO aereoAndata);

	List<HotelDTO> getListaHotel(String destinazione);

	void creaViaggio(Prenotazione_ViaggioDTO viaggio, int modalita);

	}
