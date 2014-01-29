package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
/**
 * Interfaccia locale che permette la comunicazione con il session bean che fornisce i metodi necessari
 * per la gestione del viaggio
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Local
public interface GestioneViaggioBean {

	/**
	 * Il metodo ritorna la lista di escursioni, filtrate secondo la destinazione e la data.
	 * @param destinazione - la destinazione cercata
	 * @param dataPartenza - la data cercata
	 * @return la lista di oggetti EscursioneDTO
	 */
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date dataPartenza);
	/**
	 * Il metodo ritorna la lista di aerei secondo i parametri passati
	 * @param cittaAtterraggio - citta di atterraggio dell'aereo
	 * @param partenza - data di partenza
	 * @param cittaPartenza - citta di decollo dell'aereo
	 * @return la lista di oggetti AereoDTO
	 */
	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio,
			Date partenza, String cittaPartenza);

	/**
	 * Il metodo ritorna la lista di aerei secondo i parametri passati
	 * @param cittaDecollo - citta di decollo dell'aereo
	 * @param partenza - data di partenza
	 * @param cittaPartenza - citta di arrivo dell'aereo
	 * @return la lista di oggetti AereoDTO
	 */
	List<AereoDTO> getListaAereiRitorno(String cittaDecollo,
			Date partenza, String cittaPartenza);

	/**
	 * Il metodo inserisce la prenotazione di un viaggio creato dall'utente
	 * @param viaggio - il viaggio da salvare
	 * @param modalita - status della prenotazione
	 */
	void creaViaggio(Prenotazione_ViaggioDTO viaggio, int modalita);
	
	/**
	 * Il metodo ritorna la lista di hotel che soddisfano i requisiti
	 * @param destinazione - la citta in cui cercare l'hotel
	 * @param dataAndata - data che indica l'inizia dell'intervallo in cui cercare l'hotel
	 * @param dataRitorno - data che indica la fine dell'intervallo in cui cercare l'hotel
	 * @return la lista di oggetti HotelDTO
	 */
	List<HotelDTO> getListaHotel(String destinazione, Date dataAndata,
			Date dataRitorno);

	}
