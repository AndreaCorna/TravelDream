package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Anagrafica;
import it.polimi.traveldream.ejb.entities.Camera;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Utente;

public class Coverter {
	
	@PersistenceContext
    private static EntityManager em;
	
	@EJB
	private static GestioneUtenteBean gestioneUtente;
	
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
		return nuovo;
	}
	
	protected static HotelDTO convertToDTO(Hotel hotel){
		HotelDTO nuovo = new HotelDTO();
		nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCittà());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		nuovo.setCostoGiornaliero(hotel.getCostoGiornaliero());
		nuovo.setDataFine(hotel.getDataCheckOut());
		nuovo.setDataInizio(hotel.getDataCheckIn());
		ArrayList<CameraDTO> camere = new ArrayList<CameraDTO>();
		for(Camera camera:hotel.getCamere()){
			camere.add(convertToDTO(camera));
		}
		nuovo.setCamere(camere);
		return nuovo;
	}
	
	protected static CameraDTO convertToDTO(Camera camera){
		CameraDTO nuovo = new CameraDTO();
		nuovo.setCosto(camera.getCosto());
		nuovo.setData_Checkin(camera.getData_Checkin());
		nuovo.setData_Checkout(camera.getData_Checkout());
		nuovo.setId(camera.getId());
		nuovo.setOccupata(camera.getOccupata());
		nuovo.setPosti(camera.getPosti());
		return nuovo;
	}

	protected static EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		return nuovo;
	}
	
	protected static UtenteDTO convertToDTO(Utente dipendente) {
		UtenteDTO dto = new UtenteDTO();
		dto.setUsername(dipendente.getUsername());
		dto.setTelefono(dipendente.getTelefono());
		dto.setEmail(dipendente.getEmail());
		dto.setCodiceFiscale(dipendente.getAnagrafica().getCf());
		Anagrafica anag = em.find(Anagrafica.class, dipendente.getAnagrafica().getCf());
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
		nuovo.setFine_Validita(pacchetto.getFine_Validità());
		nuovo.setId(pacchetto.getId());
		nuovo.setInizio_Validita(pacchetto.getInizio_Validità());
		nuovo.setHotels(convertListaHotelToDTO(pacchetto.getHotels()));
		nuovo.setEscursioni(convertListaEscursioniToDTO(pacchetto.getEscursioni()));
		nuovo.setAereiAndata(convertListaAereiAndataToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		nuovo.setAereiRitorno(convertListaAereiRitornoToDTO(pacchetto.getAerei(), pacchetto.getDestinazione()));
		UtenteDTO dipendente = gestioneUtente.getUtenteDTO(pacchetto.getDipendente().getUsername());
		nuovo.setDipendente(dipendente);
		nuovo.setNumeroPersone(pacchetto.getNumeroPersone());
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


}
