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
import java.util.Properties;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
		String messaggio = "Di seguito viene riportato il report del tuo acquisto\n";
		float costo = 0;
		Prenotazione_Viaggio nuovoViaggio = new Prenotazione_Viaggio();
		if ((modalita == 1)|| (modalita == 3)||(modalita == 2)||(modalita == 7)||(modalita == 8)||(modalita == 9)||(modalita == 10)||(modalita == 11))
		{
			Aereo aereiAndata = em.find(Aereo.class,viaggio.getAereoAndata().getId());
			nuovoViaggio.setAereo1(aereiAndata);
			messaggio = messaggio+"<h1>Partenza "+aereiAndata.getData()+" con aereo "+aereiAndata.getId()+"</h1>\n ";
			costo = costo+aereiAndata.getCosto();
		}
		if ((modalita == 8)||(modalita == 9)||(modalita == 10)||(modalita == 11))
		{
			Aereo aereiRitorno = em.find(Aereo.class,viaggio.getAereoRitorno().getId());
			nuovoViaggio.setAereo2(aereiRitorno);
			messaggio = messaggio+"<h1>Ritorno "+aereiRitorno.getData()+" con aereo "+aereiRitorno.getId()+"</h1>\n ";
			costo = costo+aereiRitorno.getCosto();
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
			Date dataCheckIn =viaggio.getHotel().getDataInizio();
			Date dataCheckOut = viaggio.getHotel().getDataFine();
			@SuppressWarnings("deprecation")
			Date in = new Date(dataCheckIn.getYear(), dataCheckIn.getMonth(), dataCheckIn.getDate());
			@SuppressWarnings("deprecation")
			Date out = new Date(dataCheckOut.getYear(), dataCheckOut.getMonth(), dataCheckOut.getDate());
			int diffInDays = (int)( (out.getTime() - in.getTime()) 
	                 / (1000 * 60 * 60 * 24.0) );
			costo = hotel.getCostoGiornaliero()*diffInDays;
			messaggio = messaggio+"<h1>Hotel "+hotel.getNome()+" a "+hotel.getStelle()+" stelle</h1>\n";
		}
		Utente utente = em.find(Utente.class, context.getCallerPrincipal().getName());
		nuovoViaggio.setData(new Date());
		nuovoViaggio.setUtente(utente);
		em.persist(nuovoViaggio);
		em.flush();
		nuovoViaggio = em.find(Prenotazione_Viaggio.class, nuovoViaggio.getId());
		viaggio.setId(nuovoViaggio.getId());
		AcquistoViaggioThread mail = new AcquistoViaggioThread(nuovoViaggio, messaggio, costo);
		Thread mailThread = new Thread(mail);
		mailThread.start();
				
	}
	
	
	/**
	 * Inner class che implementa un thread per l'invio della mail di riepilogo dell'acquisto di un viaggio.
	 * @author Alessandro Brunitti - Andrea Corna
	 *
	 */
	class AcquistoViaggioThread implements Runnable{
		
		private Prenotazione_Viaggio prenotazione;
		
		private String messaggio;
		
		private float costo;
		
		public AcquistoViaggioThread(){
			
		}
		
		public AcquistoViaggioThread(Prenotazione_Viaggio prenotazione, String messaggio, float costo){
			this.prenotazione = prenotazione;
			this.messaggio = messaggio;
			this.costo = costo;
		}
		@Override
		public void run() {
			Session mailSession = createSmtpSession();
			mailSession.setDebug (true);
			try {
			    Transport transport = mailSession.getTransport ();
			    
			    MimeMessage message = new MimeMessage (mailSession);
			    messaggio = messaggio+ "<h1>Escursioni: \n";
			    for(Escursione escursione:prenotazione.getEscursioni()){
			    	String add = "\nEscursione: "+escursione.getId()+
			    			", Luogo: "+escursione.getLuogo()+
			    			", Descrizione: "+escursione.getDescrizione()+"</h1>\n";
			    	messaggio = messaggio + add;
			    	costo += escursione.getPrezzo();
			    }
			    message.setSubject ("Conferma acquisto viaggio");
			    message.setFrom (new InternetAddress ("traveldream.com"));
			    String inizio = "Hai appena creato un tuo viaggio \n";
			    message.setContent ("<h1>Caro "+prenotazione.getUtente().getUsername()+"</h1>\n "
			    		+ inizio+messaggio
			    		+ "<h1>Costo: "+costo
			    		+ "</h1>\n"
			    		+ "A Presto.\n"
			    		+ "<h1>Il Team di TravelDream</h1>\n ", "text/html");
			    message.addRecipient (Message.RecipientType.TO, new InternetAddress (prenotazione.getUtente().getEmail()));

			    transport.connect ();
			    transport.sendMessage (message, message.getRecipients (Message.RecipientType.TO));  
			}
			catch (MessagingException e) {
			    System.err.println("Cannot Send email");
			    e.printStackTrace();
			}
			}

			private Session createSmtpSession() {
			final Properties props = new Properties();
			props.setProperty ("mail.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.port", "" + 587);
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty ("mail.transport.protocol", "smtp");
			
			return Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
			    return new PasswordAuthentication("info.traveldream.aa@gmail.com", "traveldreampolimi");
					}
				});
			
		}
	
	}
}
