package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
import it.polimi.traveldream.ejb.entities.Utente;

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
 * Session bean per la gestione di un viaggio creato da un utente.
 * @author Alessandro Brunitti - Andrea Corna
 *
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
    }

    /**
     * Metodo che dato il luogo in cui vi � l'hotel e la data di checkin e di checkout restituisce la lista di hotel che soddisfano la ricerca
     */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(String destinazione, Date dataAndata, Date dataRitorno){
		
		List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h WHERE h.citta =:nome and h.valido = 1")
				.setParameter("nome", destinazione)
			    .getResultList();
		for (int i = 0;i<hotels.size();i++)
		{
			if(!haCamereDisponibili(hotels.get(i),dataAndata, dataRitorno))
					hotels.remove(i);
		}
		List<HotelDTO> listaHotels = ConverterDTO.convertListaHotelToDTO(hotels);
		return listaHotels;
	}
	
	/**
	 * metodo che permette di verificare dato un hotel se questo ha camere disponibili in un dato periodo
	 * @param hotel
	 * @param partenza
	 * @param ritorno
	 * @return true se l'hotel ha camere disponibili per il periodo dato in ingresso
	 */
	@SuppressWarnings("unchecked")
	private boolean haCamereDisponibili(Hotel hotel, Date partenza, Date ritorno){
		List<Prenotazione_Pacchetto> prenotazioniPac = em.createQuery("SELECT p FROM Prenotazione_Pacchetto p "
				+ "WHERE p.dataCheckInHotel BETWEEN :andata AND :ritorno AND p.dataCheckOutHotel BETWEEN :andata AND :ritorno "
				+ "AND p.hotel =:hotel and p.hotel.valido=1")
				.setParameter("hotel", hotel)
				.setParameter("ritorno", ritorno)
				.setParameter("andata",partenza).getResultList();
		List<Prenotazione_Pacchetto> prenotazioniViaggi = em.createQuery("SELECT p FROM Prenotazione_Viaggio p "
				+ "WHERE p.dataCheckInHotel BETWEEN :andata AND :ritorno AND p.dataCheckOutHotel BETWEEN :andata AND :ritorno "
				+ "AND p.hotel =:hotel and p.hotel.valido=1")
				.setParameter("hotel", hotel)
				.setParameter("ritorno", ritorno)
				.setParameter("andata",partenza).getResultList();
		int camereOccupate = prenotazioniPac.size() + prenotazioniViaggi.size();
		return (hotel.getCamere_Disponibili()-camereOccupate)>0;
	}

	/**
	 * Metodo che preso in ingresso il luogo in cui il cliente vuole effettuare l'escursione e la data restituisce la lista di escursioni che soddisfano la ricerca
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni(String destinazione, Date andata) {
		Date inizioGiorno = andata;
		Date fineGiorno = andata;
		inizioGiorno= new Date(andata.getYear(),andata.getMonth(), andata.getDate());
		fineGiorno.setHours(23);
		fineGiorno.setMinutes(59);
		fineGiorno.setSeconds(59);
		
		List<Escursione> escursioni = em.createQuery("SELECT a FROM Escursione a WHERE a.luogo =:nome and a.valido= 1 and a.data BETWEEN :startDate and :endDate")
				.setParameter("nome", destinazione)
			    .setParameter("startDate", inizioGiorno, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineGiorno, TemporalType.TIMESTAMP)
			    .getResultList();
	   	List<EscursioneDTO> listaEscursioni = ConverterDTO.convertListaEscursioniToDTO(escursioni);
    	return listaEscursioni;
	}
	
	/**
	 * Metodo che preso in ingresso la destinazione e la data del viaggio restituisce la lista di aerei che soddisfano la ricerca
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String destinazione, Date andata, String cittaPartenza) {
		Date inizioGiorno = andata;
		Date fineGiorno = andata;
		inizioGiorno= new Date(andata.getYear(),andata.getMonth(), andata.getDate());
		fineGiorno.setHours(23);
		fineGiorno.setMinutes(59);
		fineGiorno.setSeconds(59);
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.decollo =:partenza and a.valido = 1 and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", destinazione)
			    .setParameter("startDate", inizioGiorno, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineGiorno, TemporalType.TIMESTAMP)
			    .setParameter("partenza", cittaPartenza)
			    .getResultList();
		List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiAndataToDTO(aerei);
		return listaAerei;
	}

	
	/**
	 * Metodo che preso in ingresso la destinazione e la data dell'aereo di ritorno restituisce la lista di aerei che soddisfano la ricerca
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date andata, String cittaArrivo) {
		Date inizioGiorno = andata;
		Date fineGiorno = andata;
		inizioGiorno= new Date(andata.getYear(),andata.getMonth(), andata.getDate());
		fineGiorno.setHours(23);
		fineGiorno.setMinutes(59);
		fineGiorno.setSeconds(59);
		
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.decollo =:partenza and a.atterraggio =:nome and a.valido = 1 and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", cittaDecollo)
			    .setParameter("partenza", cittaArrivo)
			    .setParameter("startDate", inizioGiorno, TemporalType.TIMESTAMP)
			    .setParameter("endDate",fineGiorno, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = ConverterDTO.convertListaAereiRitornoToDTO(aerei);
		return listaAerei;
	}
	
	/**
	 * Metodo che data la tipologia del viaggio e il viaggio si occupa di creare il viaggio compilando i campi appropriati all'interno del database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public void creaViaggio(Prenotazione_ViaggioDTO viaggio, int modalita)
	{
		
		Prenotazione_Viaggio nuovoViaggio = new Prenotazione_Viaggio();
		if ((modalita == 1)|| (modalita == 3)||(modalita == 2)||(modalita == 7)||(modalita == 8)||(modalita == 9)||(modalita == 10)||(modalita == 11))
		{
			Aereo aereiAndata = em.find(Aereo.class,viaggio.getAereoAndata().getId());
			nuovoViaggio.setAereo1(aereiAndata);	
		}
		if ((modalita == 8)||(modalita == 9)||(modalita == 10)||(modalita == 11))
		{
			Aereo aereiRitorno = em.find(Aereo.class,viaggio.getAereoRitorno().getId());
			nuovoViaggio.setAereo2(aereiRitorno);
		}
		if((modalita == 6)||(modalita == 4)||(modalita == 2)||( modalita == 7)||(modalita == 9)||(modalita == 11))
		{
			List<Escursione> escursioni = ConverterDTO.convertListaEscursioni(viaggio.getEscursioni());
			nuovoViaggio.setEscursioni(escursioni);
		}
		
		if((modalita == 5)||(modalita == 3)||(modalita == 4)||(modalita == 7)||(modalita ==10)||(modalita ==11))
		{
			Hotel hotel = em.find(Hotel.class, viaggio.getHotel().getId());
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
	
}
