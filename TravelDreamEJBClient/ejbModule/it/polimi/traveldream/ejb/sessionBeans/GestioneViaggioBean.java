package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface GestioneViaggioBean {

	public void prenotaViaggio();
	
	public List<AereoDTO> getListaAerei();
	
	public List<HotelDTO> getListaHotel();
	
	public List<EscursioneDTO> getListaEscursioni();
	
	public void mostraRiepilogo();

	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio,
			Date inizioValidita, Date fineValidita);

	List<AereoDTO> getListaAereiRitorno(String cittaDecollo,
			Date inizioValidita, Date fineValidita);
}
