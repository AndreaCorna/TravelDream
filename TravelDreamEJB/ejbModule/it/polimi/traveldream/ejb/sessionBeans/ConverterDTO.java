package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Anagrafica;
import it.polimi.traveldream.ejb.entities.Condivisione;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
import it.polimi.traveldream.ejb.entities.Utente;
/**
 * La classe fornisce una serie di metodi necessari per la conversione da entita del database a oggetti DTO per la comunicazione
 * con il tier client. 
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
public class ConverterDTO {
	
	@PersistenceContext
    private static EntityManager em;
	
	
	private static GestioneUtenteBean gestioneUtente = new GestioneUtenteBeanImpl();
	
	
	@Resource
	private EJBContext context;
	/**
	 * Il metodo converte un'istanza aereo del database in in oggetto DTO
	 * @param aereo - aereo da convertire
	 * @return DTO equivalente al parametro
	 */
	protected static AereoDTO convertToDTO(Aereo aereo){
		AereoDTO nuovo = new AereoDTO();
		nuovo.setCittaAtterraggio(aereo.getAtterraggio());
		nuovo.setCittaDecollo(aereo.getDecollo());
		nuovo.setCosto(aereo.getCosto());
		nuovo.setData(aereo.getData());
		nuovo.setId(aereo.getId());
		nuovo.setPostiDisponibili(aereo.getPosti_Disponibili());
		nuovo.setValido(aereo.getValido());
		return nuovo;
	}
	/**
	 * Il metodo converte un'istanza hotel del database in in oggetto DTO
	 * @param hotel - hotel da convertire
	 * @return DTO equivalente al parametro
	 */
	protected static HotelDTO convertToDTO(Hotel hotel){
		HotelDTO nuovo = new HotelDTO();
		nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCitta());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		nuovo.setCostoGiornaliero(hotel.getCostoGiornaliero());
		nuovo.setValido(hotel.getValido());
		return nuovo;
	}
	/**
	 * Il metodo converte un'istanza escursione del database in in oggetto DTO
	 * @param escursione - escursione da convertire
	 * @return DTO equivalente al parametro
	 */
	protected static EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		nuovo.setValido(escursione.getValido());
		return nuovo;
	}
	/**
	 * Il metodo converte un'istanza utente del database in in oggetto DTO
	 * @param dipendente - utente da convertire
	 * @return DTO equivalente al parametro
	 */
	protected static UtenteDTO convertToDTO(Utente dipendente) {
		UtenteDTO dto = new UtenteDTO();
		dto.setUsername(dipendente.getUsername());
		dto.setTelefono(dipendente.getTelefono());
		dto.setEmail(dipendente.getEmail());
		dto.setCodiceFiscale(dipendente.getAnagrafica().getCf());
		Anagrafica anag = dipendente.getAnagrafica();
		dto = convertToAnagraficaDTO(anag,dto);
		return dto;
	}
	/**
	 * Il metodo setta i campi anagrafica di un utente
	 * @param anagrafica - anagrafica da convertire
	 * @param nuovo - utente al quale aggiungere l'anagrafica
	 * @return utenteDTO con anagrafica
	 */
	protected static UtenteDTO convertToAnagraficaDTO(Anagrafica anagrafica, UtenteDTO nuovo) {
		
		nuovo.setCognome(anagrafica.getCognome());
		nuovo.setDataNascita(anagrafica.getData_Nascita());
		nuovo.setLuogoNascita(anagrafica.getLuogo_Nascita());
		nuovo.setNome(anagrafica.getNome());
		nuovo.setResidenza(anagrafica.getResidenza());
		return nuovo;
	}
	
	/**
	 * Il metodo converte un'istanza pacchetto in un oggetto DTO
	 * @param pacchetto - pacchetto da convertire
	 * @return DTO equivalente al parametro
	 */
	protected static PacchettoDTO convertToDTO(Pacchetto pacchetto){
		PacchettoDTO nuovo = new PacchettoDTO();
		nuovo.setDescrizione(pacchetto.getDescrizione());
		nuovo.setDestinazione(pacchetto.getDestinazione());
		nuovo.setFine_Validita(pacchetto.getFine_Validita());
		nuovo.setId(pacchetto.getId());
		nuovo.setInizio_Validita(pacchetto.getInizio_Validita());
		nuovo.setHotels(convertListaHotelToDTO(pacchetto.getHotels()));
		nuovo.setEscursioni(convertListaEscursioniToDTO(pacchetto.getEscursioni()));
		nuovo.setAereiAndata(convertListaAereiAndataToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		nuovo.setAereiRitorno(convertListaAereiRitornoToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		UtenteDTO dipendente = convertToDTO(pacchetto.getDipendente());
		nuovo.setDipendente(dipendente);
		nuovo.setNumeroPersone(pacchetto.getNumeroPersone());
		nuovo.setValido(pacchetto.getValido());
		return nuovo;
	}

	
	/**
	 * Il metodo converte una lista di entita escursione in una lista di escursioneDTO
	 * @param lista - lista delle escursioni
	 * @return lista di oggetti dto
	 */
	protected static List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
		ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
		for(int i=0;i<lista.size();i++){
			EscursioneDTO nuova = convertToDTO(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<EscursioneDTO> escursioni = listaEscursioni;
		return escursioni;
	}
	
	/**
	 * Il metodo prende da una lista di aerei la lista degli aerei di andata
	 * @param lista - lista da scorrere
	 * @param destinazionePacchetto - stringa che rappresenta la destinazione del pacchetto al quale appartiene la lista
	 * @return lista degli aerei di andata del pacchetto
	 */
	protected static List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista, String destinazionePacchetto){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getAtterraggio().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	/**
	 * Il metodo ritorna la lista dei pacchetti di ritorno di un pacchetto
	 * @param lista - lista degli aerei del pacchetto
	 * @param destinazionePacchetto - stringa rappresentante la destinazione del pacchetto
	 * @return lista degli aerei di ritorno del pacchetto
	 */
	protected static List<AereoDTO> convertListaAereiRitornoToDTO(List<Aereo> lista, String destinazionePacchetto){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getDecollo().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	/**
	 * Il metodo converte una lista di entita aereo in una lista di oggetti DTO
	 * @param lista - lista degli aerei
	 * @return lista di oggetti AereoDTO
	 */
	protected static List<AereoDTO> convertListaAereiToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAerei = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAerei.add(nuovo);
		}
		List<AereoDTO> aerei = listaAerei;
		return aerei;
	}
	
	
	/**
	 * Il metodo converte una lista di entity hotel in una lista di oggetti DTO
	 * @param lista - lista degli hotel da convertire
	 * @return lista di oggetti dto.
	 */
	protected static List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0;i<lista.size();i++){
			HotelDTO nuovo = convertToDTO(lista.get(i));
			listaHotel.add(nuovo);
		}
		List<HotelDTO> hotel = listaHotel;
		return hotel;
	}
	/**
	 * Il metodo converte una lista di entity pacchetto in una lista di oggetti dto
	 * @param lista - lista da convertire
	 * @return lista di oggetti dto
	 */
	protected static List<PacchettoDTO> convertListaPacchettiToDTO(List<Pacchetto> lista){
		ArrayList<PacchettoDTO> pacchetti = new ArrayList<PacchettoDTO>();
		for (int i=0;i<lista.size();i++){
			pacchetti.add(convertToDTO(lista.get(i)));
		}
		List<PacchettoDTO> listaPacchetti = pacchetti;
		return listaPacchetti;
	}
	/**
	 * Il metodo converte una lista di EscursioniDTO in una lista di entity Escursione
	 * @param lista - la lista da convertire
	 * @return lista di entity
	 */
	protected static List<Escursione> convertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = new Escursione(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	/**
	 * Il metodo converte la lista delle prenotazioni viaggio di un utente in una lista equivalente di prenotazione viaggio DTO
	 * @param lista - la lista da convertire
	 * @param idUtenteOnline - username dell'utente
	 * @return la lista di oggetti dto
	 */
	protected static List<Prenotazione_ViaggioDTO> convertListaUtentiOnlineViaggiToDTO(List<Prenotazione_Viaggio> lista, String idUtenteOnline){
		ArrayList<Prenotazione_ViaggioDTO> listaPacchettiPrenotati = new ArrayList<Prenotazione_ViaggioDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getUtente().getUsername().toLowerCase().equals(idUtenteOnline.toLowerCase()))
			{	Prenotazione_ViaggioDTO nuovo = convertToDTO(lista.get(i));
				listaPacchettiPrenotati.add(nuovo);
			}
		}
		List<Prenotazione_ViaggioDTO> prenotazioni = listaPacchettiPrenotati;
		return prenotazioni;
	}
	/**
	 * Il metodo converte una lista di prenotazione pacchetto di un utente in una lista di oggetti DTO equivalenti
	 * @param lista - lista da convertire
	 * @param idUtenteOnline - username dell'utente
	 * @return lista di oggetti dto
	 */
	protected static List<Prenotazione_PacchettoDTO> convertListaUtentiOnlineToDTO(List<Prenotazione_Pacchetto> lista, String idUtenteOnline){
		ArrayList<Prenotazione_PacchettoDTO> listaPacchettiPrenotati = new ArrayList<Prenotazione_PacchettoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getUtente().getUsername().toLowerCase().equals(idUtenteOnline.toLowerCase()))
			{	Prenotazione_PacchettoDTO nuovo = convertToDTO(lista.get(i));
				listaPacchettiPrenotati.add(nuovo);
			}
		}
		List<Prenotazione_PacchettoDTO> prenotazioni = listaPacchettiPrenotati;
		return prenotazioni;
	}
	/**
	 * Il metodo converte una prenotazione viaggio in un oggetto dto equivalente
	 * @param prenotazione - la prenotazione da convertire
	 * @return la prenotazione viaggio DTO
	 */
	protected static Prenotazione_ViaggioDTO convertToDTO(Prenotazione_Viaggio prenotazione){
		Prenotazione_ViaggioDTO nuovo = new Prenotazione_ViaggioDTO();
		nuovo.setId(prenotazione.getId());
		if (prenotazione.getData()!=null)
			nuovo.setData(prenotazione.getData());
		if(prenotazione.getEscursioni()!=null)
			nuovo.setEscursioni(convertListaEscursioniToDTO(prenotazione.getEscursioni()));
		if (prenotazione.getAereo1()!=null)
			nuovo.setAereo(convertToDTO(prenotazione.getAereo1()));               
		if (prenotazione.getAereo2()!=null)
			nuovo.setAereoRitorno(convertToDTO(prenotazione.getAereo2()));
		if (prenotazione.getHotel()!=null)
			nuovo.setHotel(convertToDTO(prenotazione.getHotel()));
		nuovo.setUtente(convertToDTO(prenotazione.getUtente()));
		
		return nuovo;
	}
	/**
	 * Il metodo converte una prenotazione pacchetto in un oggetto dto equivalente
	 * @param prenotazione - la prenotazione da convertire
	 * @return la prenotazione pacchetto DTO
	 */
	protected static Prenotazione_PacchettoDTO convertToDTO(Prenotazione_Pacchetto prenotazione){
		Prenotazione_PacchettoDTO nuovo = new Prenotazione_PacchettoDTO();
		nuovo.setId(prenotazione.getId());
		nuovo.setData(prenotazione.getData());
		nuovo.setCondivisioni(convertListaCondivisioniToDTO(prenotazione.getCondivisioni()));
		nuovo.setEscursioni(convertListaEscursioniToDTO(prenotazione.getEscursioni()));
		nuovo.setAereo(convertToDTO(prenotazione.getAereo1()));             
		nuovo.setAereoRitorno(convertToDTO(prenotazione.getAereo2()));
		nuovo.setHotel(convertToDTO(prenotazione.getHotel()));
		nuovo.setUtente(convertToDTO(prenotazione.getUtente()));
		nuovo.setPacchetto(convertToDTO(prenotazione.getPacchetto()));
		nuovo.setNumeroPersone(prenotazione.getNumeroPersone());
		return nuovo;
	}
	/**
	 * Il metodo converte una lista di convidisioni in una equivalente costituita da oggetti CondivisioneDTO
	 * @param lista - lista da convertire
	 * @return lista di oggetti DTO
	 */
	protected static List<CondivisioneDTO> convertListaCondivisioniToDTO(List<Condivisione> lista){
		ArrayList<CondivisioneDTO> listaCondivisione = new ArrayList<CondivisioneDTO>();
		for(int i=0;i<lista.size();i++){
				CondivisioneDTO nuovo = convertToDTO(lista.get(i));
				listaCondivisione.add(nuovo);

		}
		List<CondivisioneDTO> condivisioni = listaCondivisione;
		return condivisioni;
	}
	/**
	 * Il metodo converte una entity condivisione in un oggetto dto equivalente
	 * @param condivisione - la condivisione da convertire
	 * @return l'oggetto dto 
	 */
	protected static CondivisioneDTO convertToDTO(Condivisione condivisione){
		CondivisioneDTO nuovo = new CondivisioneDTO();
		nuovo.setLink(condivisione.getLink());
		nuovo.setData(condivisione.getData());
		nuovo.setId_Prenotazione(condivisione.getPrenotazionePacchetto().getId());
		nuovo.setUtente(convertToDTO(condivisione.getUtente()));
		return nuovo;
	}
	/**
	 * Il metodo converte una lista di entity aereo in una lista di oggetti dto equivalenti
	 * @param lista - la lista da convertire
	 * @return lista di oggetti DTO convertiti
	 */
	protected static List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
				}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	/**
	 * Il metodo converte una lista di entity aereo in una lista di oggetti dto equivalenti
	 * @param lista - la lista da convertire
	 * @return lista di oggetti DTO convertiti
	 */
	protected static List<AereoDTO> convertListaAereiRitornoToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAereiRitorno = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			{	AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAereiRitorno.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiRitorno;
		return aerei;
	}


}
