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

	/**
	 * Il metodo ritorna la lista degli aerei
	 * @return lista degli aerei
	 */
	List<AereoDTO> getListaAerei();
	/**
	 * Il metodo ritorna la lista degli hotel
	 * @return lista degli hotel
	 */
	List<HotelDTO> getListaHotel();
	/**
	 * Il metodo ritorna la lista delle escursioni
	 * @return lista delle escursioni
	 */
	List<EscursioneDTO> getListaEscursioni();

	/**
	 * Il metodo ritorna la lista degli hotel di una citta
	 * @param citta - la citta dove cercare gli hotel
	 * @return lista degli hotel
	 */
	List<HotelDTO> getListaHotel(String citta);

	/**
	 * Il metodo ritorna la lista degli aerei di andata fissati i parametri
	 * @param cittaAtterraggio - la citta dove deve atterrare l'aereo
	 * @param inizioValidita - data minore in cui cercare il volo 
	 * @param fineValidita - data maggiore in cui cercare il volo
	 * @return lista degli aerei che soddisfano i parametri
	 */
	List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita);

	/**
	 * Il metodo ritorna la lista degli aerei di ritorno fissati i parametri
	 * @param cittaDecollo - la citta dove deve decollare l'aereo
	 * @param inizioValidita - data minore in cui cercare il volo 
	 * @param fineValidita - data maggiore in cui cercare il volo
	 * @return lista degli aerei che soddisfano i parametri
	 */
	List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date inizioValidita, Date fineValidita);

	/**
	 * Il metodo ritorna la lista di escursioni secondo i parametri passati
	 * @param destinazione - luogo delle escursioni 
	 * @param inizioValidita - la data dell'escursione maggiore o uguale a questa data
	 * @param fineValidita - la data dell'escursione minore o uguale a questa data
	 * @return lista delle escursioni
	 */
	List<EscursioneDTO> getListaEscursioni(String destinazione,Date inizioValidita, Date fineValidita);

	/**
	 * Il metodo inserisce un pacchetto nel database
	 * @param pacchetto - DTO contenente tutte le informazioni
	 */
	void creaPacchetto(PacchettoDTO pacchetto);

	/**
	 * Il metodo verifica la presenza di un pacchetto nel database
	 * @param id - id da verificare
	 * @return <true> se esiste gia un pacchetto con l'id, <false> altrimenti
	 */
	boolean esisteIdPacchetto(String id);

	/**
	 * Il metodo ritorna la lista dei pacchetti presenti nel database
	 * @return la lista dei pacchetti
	 */
	List<PacchettoDTO> getListaPacchetti();

	/**
	 * Il metodo elimina un pacchetto
	 * @param pacchetto - DTO contenente le informazioni del pacchetto da eliminare
	 */
	void eliminaPacchetto(PacchettoDTO pacchetto);

	/**
	 * Il metodo modifica un pacchetto presente nel database
	 * @param pacchetto - DTO contenente le informazioni del pacchetto da eliminare
	 */
	void modificaPacchetto(PacchettoDTO pacchetto);

	/**
	 * Il metodo ritorna gli aerei di andata di un pacchetto che hanno posti disponibili tra le date indicate
	 * @param partenza - data di partenza della prenotazione
	 * @param ritorno - data di rientro
	 * @param pacchetto - pacchetto scelto dal cliente
	 * @param value - numero di persone
	 * @return la lista degli aerei
	 */
	List<AereoDTO> getListaAereiAndataDisp(Date partenza, Date ritorno,PacchettoDTO pacchetto, Integer value);

	/**
	 * Il metodo ritorna gli aerei di ritorno di un pacchetto che hanno posti disponibili tra le date indicate
	 * @param partenza - data di partenza della prenotazione
	 * @param ritorno - data di rientro
	 * @param pacchetto - pacchetto scelto dal cliente
	 * @param value - numero di persone
	 * @return la lista degli aerei
	 */
	List<AereoDTO> getListaAereiRitornoDisp(Date partenza, Date ritorno,PacchettoDTO pacchetto, Integer value);

	/**
	 * Il metodo ritorna gli hotel di un pacchetto che hanno posti disponibili tra le date indicate
	 * @param partenza - data di partenza della prenotazione
	 * @param ritorno - data di rientro
	 * @param pacchetto - pacchetto scelto dal cliente
	 * @return la lista degli hotel
	 */
	List<HotelDTO> getListaHotelDip(Date partenza, Date ritorno, PacchettoDTO pacchetto);

}
