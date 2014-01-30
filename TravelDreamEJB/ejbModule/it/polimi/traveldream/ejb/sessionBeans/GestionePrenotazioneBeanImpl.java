package it.polimi.traveldream.ejb.sessionBeans;



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











import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Session bean che implementa le funzioni per la gestione della prenotazione di un viaggio o di un
 * pacchetto offerto dall'agenzia.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Stateless
public class GestionePrenotazioneBeanImpl implements it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean {


	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	@EJB
	private GestioneUtenteBean gestioneUtente;
    
	
    /**
     * Default constructor. 
     */
    public GestionePrenotazioneBeanImpl() {
    	
    }

    /**
     * getter della lista delle prenotazioni
     */
	@Override
	public List<Prenotazione_PacchettoDTO> getPrenotazioni() {
		return null;
	}

	/**
	 * Metodo che si occupa data la prenotazione di costruire la prenotazione del pacchetto 
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public void inserisciPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione) {
		Prenotazione_Pacchetto nuova = new Prenotazione_Pacchetto();
		nuova.setAereo1(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo2(em.find(Aereo.class,prenotazione.getAereoRitorno().getId()));
		nuova.setData(prenotazione.getData());
		nuova.setHotel(em.find(Hotel.class,prenotazione.getHotel().getId()));
		nuova.setPacchetto(em.find(Pacchetto.class,prenotazione.getPacchetto().getId()));
		nuova.setUtente(em.find(Utente.class, prenotazione.getUtente().getUsername()));
		nuova.setEscursioni(ConverterDTO.convertListaEscursioni(prenotazione.getEscursioni()));
		nuova.setDataCheckInHotel(prenotazione.getCheckInHotel());
		nuova.setDataCheckOutHotel(prenotazione.getCheckOutHotel());
		nuova.setNumeroPersone(prenotazione.getNumeroPersone());
		em.persist(nuova);
		em.flush();
		nuova = em.find(Prenotazione_Pacchetto.class, nuova.getId());
		prenotazione.setId(nuova.getId());
		AcquistoPacchettoMailThread mail = new AcquistoPacchettoMailThread(nuova, false);
		Thread mailThread = new Thread(mail);
		mailThread.start();
	}
	/**
	 * Il metodo aggiorna la prenotazione di un pacchetto
	 */
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public void aggiornaPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione) {
		Prenotazione_Pacchetto nuova = em.find(Prenotazione_Pacchetto.class,prenotazione.getId());
		System.out.println(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo1(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo2(em.find(Aereo.class,prenotazione.getAereoRitorno().getId()));
		nuova.setData(prenotazione.getData());
		nuova.setHotel(em.find(Hotel.class,prenotazione.getHotel().getId()));
		nuova.setPacchetto(em.find(Pacchetto.class,prenotazione.getPacchetto().getId()));
		nuova.setUtente(em.find(Utente.class, prenotazione.getUtente().getUsername()));
		nuova.setEscursioni(ConverterDTO.convertListaEscursioni(prenotazione.getEscursioni()));
		nuova.setDataCheckInHotel(prenotazione.getCheckInHotel());
		nuova.setDataCheckOutHotel(prenotazione.getCheckOutHotel());
		nuova.setNumeroPersone(prenotazione.getNumeroPersone());
		em.merge(nuova);
		AcquistoPacchettoMailThread mail = new AcquistoPacchettoMailThread(nuova, true);
		Thread mailThread = new Thread(mail);
		mailThread.start();
	
	}
	
	/**
	 * metodo che permette di recuperare le prenotazioni relative ai pacchetti prenotati dell'utente che richiama il metodo chiamante
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_PacchettoDTO> getListaPrenotazioni() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		Utente utenteOnline = em.find(Utente.class, idUtenteOnline);
		List<Prenotazione_Pacchetto> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Pacchetto a WHERE a.utente =:nome")
			    .setParameter("nome", utenteOnline)
			    .getResultList();
		List<Prenotazione_PacchettoDTO> listaUtenti = ConverterDTO.convertListaUtentiOnlineToDTO(prenotazioniUtente, utenteOnline.getUsername());
		return listaUtenti;
	}
	
	/**
	 * metodo che permette di recuperare le prenotazioni relative ai viaggi prenotati dell'utente che richiama il metodo chiamante
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_ViaggioDTO> getListaPrenotazioniViaggio() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		Utente utenteOnline = em.find(Utente.class, idUtenteOnline);
		List<Prenotazione_Viaggio> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Viaggio a WHERE a.utente =:nome")
			    .setParameter("nome", utenteOnline)
			    .getResultList();
		List<Prenotazione_ViaggioDTO> listaUtenti = ConverterDTO.convertListaUtentiOnlineViaggiToDTO(prenotazioniUtente, utenteOnline.getUsername());
		return listaUtenti;
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public boolean esistePrenotazione(int idPrenotazione, PacchettoDTO pacchetto) {
		Pacchetto pacchettoDB = em.find(Pacchetto.class, pacchetto.getId());
		Utente utente = em.find(Utente.class, gestioneUtente.getUtenteDTO().getUsername());
		boolean trovata = true;
		try{
			@SuppressWarnings("unused")
			Prenotazione_Pacchetto prenotazione = (Prenotazione_Pacchetto) em.createQuery("SELECT p FROM Prenotazione_Pacchetto p WHERE p.id =:id and p.pacchetto =:pacchetto "
				+ "and p.utente =:utente")
				.setParameter("id", idPrenotazione)
				.setParameter("pacchetto", pacchettoDB).setParameter("utente", utente).getSingleResult();
		
		}catch(NoResultException e){
			trovata = false;
		}
		return trovata;
	}

	/**
	 * Inner class che implementa un oggetto Runnable in grado di inviare una mail di notifica
	 * a seguito dell'acquisto di un pacchetto.
	 * @author Alessandro Brunitti - Andrea Corna
	 *
	 */
	class AcquistoPacchettoMailThread implements Runnable{

		private Prenotazione_Pacchetto prenotazione;
		
		private boolean modifica;
		
		public AcquistoPacchettoMailThread(){
			
		}
		
		public AcquistoPacchettoMailThread(Prenotazione_Pacchetto prenotazione, boolean modifica){
			this.prenotazione = prenotazione;
			this.modifica = modifica;
		}
		
		@Override
		public void run() {
			Session mailSession = createSmtpSession();
			mailSession.setDebug (true);
			float costo = 0;
			try {
			    Transport transport = mailSession.getTransport ();

			    MimeMessage message = new MimeMessage (mailSession);
			    String escursioni = "Escursioni: \n";
			    for(Escursione escursione:prenotazione.getEscursioni()){
			    	String add = "\nEscursione: "+escursione.getId()+
			    			", Luogo: "+escursione.getLuogo()+
			    			", Descrizione: "+escursione.getDescrizione();
			    	escursioni = escursioni + add;
			    	costo += (escursione.getPrezzo()*prenotazione.getNumeroPersone());
			    }
			    Date dataCheckIn = prenotazione.getAereo1().getData();
				Date dataCheckOut = prenotazione.getAereo2().getData();
				@SuppressWarnings("deprecation")
				Date in = new Date(dataCheckIn.getYear(), dataCheckIn.getMonth(), dataCheckIn.getDate());
				@SuppressWarnings("deprecation")
				Date out = new Date(dataCheckOut.getYear(), dataCheckOut.getMonth(), dataCheckOut.getDate());
				int diffInDays = (int)( (out.getTime() - in.getTime()) 
		                 / (1000 * 60 * 60 * 24.0) );
			    costo = costo + prenotazione.getNumeroPersone()*
			    		(prenotazione.getAereo1().getCosto()+prenotazione.getAereo2().getCosto()+
			    				prenotazione.getHotel().getCostoGiornaliero()*diffInDays);
			    message.setSubject ("Conferma acquisto pacchetto");
			    message.setFrom (new InternetAddress ("traveldream.com"));
			    String inizio;
			    if(!modifica){
			    	inizio = "Hai appena acquistato un nostro pacchetto \n";
			    }else{
			    	inizio = "Hai appena modificato una tua prenotazione\n";
			    }
			    message.setContent ("<h1>Caro "+prenotazione.getUtente().getUsername()+"</h1>\n "
			    		+ inizio
			    		+ "Di seguito viene riportato il report del tuo acquisto\n "
			    		+ "<h1>Destinazione "+prenotazione.getPacchetto().getDestinazione()
			    		+ "</h1>\n "
			    		+ "<h1>Partenza "+prenotazione.getAereo1().getData()+" con aereo "+prenotazione.getAereo1().getId()
			    		+ "</h1>\n "
			    		+ "<h1>Ritorno "+prenotazione.getAereo2().getData()+" con aereo "+prenotazione.getAereo2().getId()
			    		+ "</h1>\n "
			    		+ "<h1>Hotel "+prenotazione.getHotel().getNome()+" a "+prenotazione.getHotel().getStelle()+" stelle</h1>\n"
			    		+ "<h1>"+escursioni
			    		+ "</h1>\n"
			    		+ "<h1>Numero Persone: "+prenotazione.getNumeroPersone()
			    		+ "</h1>\n"
			    		+ "<h1>Costo: "+costo
			    		+ "</h1>\n"
			    		+ "<h1>Descrizione Pacchetto: "+prenotazione.getPacchetto().getDescrizione()
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
