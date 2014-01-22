package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
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

public class ConverterDTO {
	
	@PersistenceContext
    private static EntityManager em;
	
	
	private static GestioneUtenteBean gestioneUtente = new GestioneUtenteBeanImpl();
	
	@Resource
	private EJBContext context;
	
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
    
	protected static UtenteDTO convertToAnagraficaDTO(Anagrafica anagrafica, UtenteDTO nuovo) {
		
		nuovo.setCognome(anagrafica.getCognome());
		nuovo.setDataNascita(anagrafica.getData_Nascita());
		nuovo.setLuogoNascita(anagrafica.getLuogo_Nascita());
		nuovo.setNome(anagrafica.getNome());
		nuovo.setResidenza(anagrafica.getResidenza());
		return nuovo;
	}
	
		
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


	protected static List<Escursione> covertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = em.find(Escursione.class, lista.get(i).getId());
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	
	protected static List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
		ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
		for(int i=0;i<lista.size();i++){
			EscursioneDTO nuova = convertToDTO(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<EscursioneDTO> escursioni = listaEscursioni;
		return escursioni;
	}
	
	protected static List<Aereo> convertListaAerei(List<AereoDTO> listaAndata, List<AereoDTO> listaRitorno){
		ArrayList<Aereo> listaAerei = new ArrayList<Aereo>();
		for(int i=0;i<listaAndata.size();i++){
			Aereo nuovo = em.find(Aereo.class, listaAndata.get(i).getId());
			listaAerei.add(nuovo);
		}
		for(int i=0;i<listaRitorno.size();i++){
			Aereo nuovo = em.find(Aereo.class, listaRitorno.get(i).getId());
			listaAerei.add(nuovo);
		}
		List<Aereo> aerei = listaAerei;
		return aerei;
	}
	
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
	
	protected static List<AereoDTO> convertListaAereiToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAerei = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAerei.add(nuovo);
		}
		List<AereoDTO> aerei = listaAerei;
		return aerei;
	}
	
	
	protected static List<Hotel> convertListaHotel(List<HotelDTO> lista){
		ArrayList<Hotel> listaHotel = new ArrayList<Hotel>();
		for(int i=0;i<lista.size();i++){
			Hotel nuovo = em.find(Hotel.class, lista.get(i).getId());
			listaHotel.add(nuovo);
		}
		List<Hotel> hotel = listaHotel;
		return hotel;
	}
	
	protected static List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0;i<lista.size();i++){
			HotelDTO nuovo = convertToDTO(lista.get(i));
			listaHotel.add(nuovo);
		}
		List<HotelDTO> hotel = listaHotel;
		return hotel;
	}
	
	protected static List<PacchettoDTO> convertListaPacchettiToDTO(List<Pacchetto> lista){
		ArrayList<PacchettoDTO> pacchetti = new ArrayList<PacchettoDTO>();
		for (int i=0;i<lista.size();i++){
			pacchetti.add(convertToDTO(lista.get(i)));
		}
		List<PacchettoDTO> listaPacchetti = pacchetti;
		return listaPacchetti;
	}
	
	protected static List<Escursione> convertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = new Escursione(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	
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
	
	protected static List<CondivisioneDTO> convertListaCondivisioniToDTO(List<Condivisione> lista){
		ArrayList<CondivisioneDTO> listaCondivisione = new ArrayList<CondivisioneDTO>();
		for(int i=0;i<lista.size();i++){
				CondivisioneDTO nuovo = convertToDTO(lista.get(i));
				listaCondivisione.add(nuovo);

		}
		List<CondivisioneDTO> condivisioni = listaCondivisione;
		return condivisioni;
	}
	
	protected static CondivisioneDTO convertToDTO(Condivisione condivisione){
		CondivisioneDTO nuovo = new CondivisioneDTO();
		nuovo.setLink(condivisione.getLink());
		nuovo.setData(condivisione.getData());
		nuovo.setId_Prenotazione(condivisione.getPrenotazionePacchetto().getId());
		nuovo.setUtente(convertToDTO(condivisione.getUtente()));
		return nuovo;
	}
	
	protected static List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
				}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	
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
