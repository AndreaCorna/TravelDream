package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
import it.polimi.traveldream.ejb.entities.Utente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 * Session Bean implementation class GestioneViaggioBeanImpl
 */
@Stateless
@LocalBean
public class GestioneViaggioBeanImpl implements GestioneViaggioBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	@EJB
	private GestioneUtenteBean gestioneUtente;
    /**
     * Default constructor. 
     */
    public GestioneViaggioBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void prenotaViaggio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findAll", Aereo.class).getResultList();
	   	List<AereoDTO> listaAerei = convertListaAereiToDTO(aereiDB);
    	return listaAerei;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(String destinazione){
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.citta =:nome and h.camere_Disponibili > 0")
				.setParameter("nome", destinazione)
			    .getResultList();
		List<HotelDTO> listaHotels = convertListaHotelToDTO(hotels);
		return listaHotels;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date dataPartenza) {
		List<Escursione> escursioni = em.createQuery("SELECT a FROM Escursione a WHERE a.luogo =:nome and a.data =:startDate")
				.setParameter("nome", destinazione)
			    .setParameter("startDate", dataPartenza, TemporalType.TIMESTAMP)
			    .getResultList();
	   	List<EscursioneDTO> listaEscursioni = convertListaEscursioniToDTO(escursioni);
    	return listaEscursioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String destinazione, Date andata) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data =:startDate")
			    .setParameter("nome", destinazione)
			    .setParameter("startDate", andata, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiAndataToDTO(aerei);
		return listaAerei;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date partenza) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data =:startDate")
			    .setParameter("nome", cittaDecollo)
			    .setParameter("startDate", partenza, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiRitornoToDTO(aerei);
		return listaAerei;
	}
	
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public void creaViaggio(Prenotazione_ViaggioDTO viaggio, int modalita) {
		
		Prenotazione_Viaggio nuovoViaggio = new Prenotazione_Viaggio();
		if ((modalita == 1)|| (modalita == 3)||(modalita == 2)||(modalita == 7)||(modalita == 8)||(modalita == 9)||(modalita == 10)||(modalita == 11))
		{
			Aereo aereiAndata = convertAereiAndata(viaggio.getAereoAndata());
			
			nuovoViaggio.setAereo1(aereiAndata);
				
		}
		if ((modalita == 8)||(modalita == 9)||(modalita == 10)||(modalita == 11))
		{
			Aereo aereiRitorno = convertAereiRitorno(viaggio.getAereoRitorno());
			nuovoViaggio.setAereo2(aereiRitorno);
			
		}
		if((modalita == 6)||(modalita == 4)||(modalita == 2)||( modalita == 7)||(modalita == 9)||(modalita == 11))
		{
			List<Escursione> escursioni = convertListaEscursioni(viaggio.getEscursioni());
			
			nuovoViaggio.setEscursioni(escursioni);
			
			
		}
		
		if((modalita == 5)||(modalita == 3)||(modalita == 4)||(modalita == 7)||(modalita ==10)||(modalita ==11)){
			Hotel hotel = convertHotel(viaggio.getHotel());
			
			nuovoViaggio.setHotel(hotel);
			nuovoViaggio.setDataCheckInHotel(viaggio.getHotel().getDataInizio());
			nuovoViaggio.setDataCheckOutHotel(viaggio.getHotel().getDataFine());
			
			
		}
		Utente utente = em.find(Utente.class, context.getCallerPrincipal().getName());
		nuovoViaggio.setData(new Date());
		nuovoViaggio.setUtente(utente);
		em.persist(nuovoViaggio);
		em.flush();
		nuovoViaggio = em.find(Prenotazione_Viaggio.class, nuovoViaggio.getId());
		viaggio.setId(nuovoViaggio.getId());
		
	}
	
	@Override
	public void mostraRiepilogo() {
		// TODO Auto-generated method stub
		
	}

	
	//ELENCO DEI CONVERTLISTA QUALCOSA TO DTO
	private List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
				}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	
	private List<AereoDTO> convertListaAereiRitornoToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAereiRitorno = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			{	AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAereiRitorno.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiRitorno;
		return aerei;
	}
	private List<AereoDTO> convertListaAereiToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAerei = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAerei.add(nuovo);
		}
		List<AereoDTO> aerei = listaAerei;
		return aerei;
	}
	
	private List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
		ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
		for(int i=0;i<lista.size();i++){
			EscursioneDTO nuova = convertToDTO(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<EscursioneDTO> escursioni = listaEscursioni;
		return escursioni;
	}
	
	
	private List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0;i<lista.size();i++){
			HotelDTO nuovo = convertToDTO(lista.get(i));
			listaHotel.add(nuovo);
		}
		List<HotelDTO> hotel = listaHotel;
		return hotel;
	}
	
	//ELENCO DEI CONVERT TO DTO
	
	private AereoDTO convertToDTO(Aereo aereo){
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
	private HotelDTO convertToDTO(Hotel hotel){
		HotelDTO nuovo = new HotelDTO();
		nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCitta());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		return nuovo;
	}
	
	private EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		return nuovo;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> aggiungiAereoViaggio(AereoDTO aereoAndata) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data = startDate")
			    .setParameter("nome", aereoAndata.getCittaAtterraggio())
			    .setParameter("startDate", aereoAndata.getData())
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiAndataToDTO(aerei);
		return listaAerei;
	}
	/*
	 * Metodi per la conversione di liste di oggetti del database
	 */
		private List<Escursione> convertListaEscursioni(List<EscursioneDTO> lista){
			ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
			for(int i=0;i<lista.size();i++){
				Escursione nuova = em.find(Escursione.class, lista.get(i).getId());
				listaEscursioni.add(nuova);
			}
			List<Escursione> escursioni = listaEscursioni;
			return escursioni;
		}
		
		private Hotel convertHotel(HotelDTO hotel){
			Hotel listaHotel = new Hotel();
				Hotel nuovo = em.find(Hotel.class, hotel.getId());
				listaHotel = nuovo;
			
			return listaHotel;
		}
		
		private Aereo convertAereiAndata(AereoDTO aereoAndata){
			Aereo listaAerei = new Aereo();
				Aereo nuovo = em.find(Aereo.class, aereoAndata.getId());
				listaAerei = nuovo;
			
			return listaAerei;
		}
		
		private Aereo convertAereiRitorno(AereoDTO aereoRitorno){
			Aereo listaAerei = new Aereo();
				Aereo nuovo = em.find(Aereo.class, aereoRitorno.getId());
				listaAerei = nuovo;
			
			return listaAerei;
		}
}
