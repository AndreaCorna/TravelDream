package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Anagrafica;
import it.polimi.traveldream.ejb.entities.Condivisione;
import it.polimi.traveldream.ejb.entities.Gruppo;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Viaggio;
import it.polimi.traveldream.ejb.entities.Utente;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
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
import javax.persistence.PersistenceContext;

/**
 * Session bean per la gestione dell'utente.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Stateless
public class GestioneUtenteBeanImpl implements GestioneUtenteBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    
	/**
	 *  Metodo che si occupa dato un utente di aggiungerlo al database
	 */
	@Override
	public void aggiungiNuovoUtente(UtenteDTO utente) {
		Anagrafica nuovaAnagrafica = new Anagrafica(utente);
		em.persist(nuovaAnagrafica);
		Utente nuovoUtente = new Utente(utente, nuovaAnagrafica);
		List<Gruppo> gruppi = new ArrayList<Gruppo>();
		Gruppo nuovoGruppo = new Gruppo();
		nuovoGruppo.setNome("UTENTE");
		gruppi.add(nuovoGruppo);
		nuovoUtente.setGruppi(gruppi);
		em.persist(nuovoUtente);
		inviaEmail(nuovoUtente);
	}

	/**
	 *  Metodo che si occupa di verificare dato lo username di un utente se questo esiste gi� nel database ritorna true false altrimenti
	 */
	@Override
	public boolean esisteUsername(String username) {
		if (em.find(Utente.class,username)!=null){
           return true;
        }
        return false;
	}
	
	/**
	 * Metodo che dato un codice fiscale si occupa di verificare se esiste nel database ritorna true altrimenti false
	 */
	@Override
	public boolean esisteCodiceFiscale(String codiceFiscale) {
		if (em.find(Anagrafica.class, codiceFiscale)!=null){
			return true;
		}
		return false;
	}


	/**
	 * Metodo che dato un utente si occupa di integrare le eventuali modifiche all'interno del database
	 */
	
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public void modificaProfiloUtente(UtenteDTO utente) {
		Anagrafica modifiche = new Anagrafica(utente);
		em.merge(modifiche);
		Utente vecchio = em.find(Utente.class, utente.getUsername());
		Utente nuovo = new Utente(utente, modifiche);
		nuovo.setAmministratoreCreatore(vecchio.getAmministratoreCreatore());
		nuovo.setCondivisioni(vecchio.getCondivisioni());
		nuovo.setDipendentiAggiunti(vecchio.getDipendentiAggiunti());
		nuovo.setPrenotazioniPacchetti(vecchio.getPrenotazioniPacchetti());
		nuovo.setPrenotazioniViaggi(vecchio.getPrenotazioniViaggi());
		nuovo.setGruppi(vecchio.getGruppi());
		em.merge(nuovo);
		
	}

	/**
	 *  Metodo che si occupa dato un codice fiscale di eliminarne il profilo all'interno del database
	 */
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public void eliminaProfilo(String cf) {
		eliminaPrenotazioni(cf);
		Anagrafica old = em.find(Anagrafica.class, cf);
		rimuovi(getUtenteAttivo());
		em.remove(old);
	}
	
	/**
	 * Metodo che viene chiamato sull'utente attivo per richiamarne il profilo
	 */
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public UtenteDTO getUtenteDTO() {
		UtenteDTO userDTO = ConverterDTO.convertToDTO(getUtenteAttivo());
		
		return userDTO;
		
	}
	
	/**
	 *  Metodo che viene chiamato sul dipendente attivo per richiamarne il profilo
	 */
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public UtenteDTO getUtenteDTO(String dipendente) {
		Utente dip = em.find(Utente.class, dipendente);
		return ConverterDTO.convertToDTO(dip);
	}
	
	
	/**
	 * Metodo che dato lo username di un utente lo ricerca all'interno del database
	 * @param username dell'utente da ricercare
	 * @return il profilo dell'utente ricercato
	 */
    public Utente cerca(String username) {
    	return em.find(Utente.class, username);
    }
    
    /**
     * Metodo che recupera la lista degli utenti e dei dipendenti all'interno del database
     */
    @Override
	@RolesAllowed({"AMMINISTRATORE","DIPENDENTE"})
    public List<UtenteDTO> getListaUtenti() {
    	List<Utente> utentiDB = em.createNamedQuery("Utente.findAll", Utente.class)
                .getResultList();
    	ArrayList<UtenteDTO> utenti = new ArrayList<UtenteDTO>();
    	for(int i=0; i<utentiDB.size();i++){
    		UtenteDTO user = ConverterDTO.convertToDTO(utentiDB.get(i));
    		utenti.add(user);
    	}
    	List<UtenteDTO> listaUtenti = utenti;
    	return listaUtenti;
    }
    
    /**
     * Metodo che recupera la lista degli utenti standard all'interno del database
     */
    @SuppressWarnings("unchecked")
	@Override
    @RolesAllowed({"AMMINISTRATORE","DIPENDENTE"})
	public List<UtenteDTO> getListaUtentiBase() {
    	List<Utente> utentiDB = em.createQuery("SELECT u FROM Utente u, IN (u.gruppi) g WHERE g.nome =:nome")
			    .setParameter("nome", "UTENTE").getResultList();
    	ArrayList<UtenteDTO> utenti = new ArrayList<UtenteDTO>();
    	for(int i=0; i<utentiDB.size();i++){
    		UtenteDTO user = ConverterDTO.convertToDTO(utentiDB.get(i));
    		utenti.add(user);
    	}
    	List<UtenteDTO> listaUtenti = utenti;
    	return listaUtenti;
	}
    
	/**
	 * Metodo che dato lo username di un utente lo elimina dal database
	 * @param username dell'utente che va eliminato
	 */
    public void rimuovi(String username) {
		Utente utente = cerca(username);
        em.remove(utente);
	}
    
    /**
     * Metodo che dato il profilo di un utente si occupa di rimuoverlo dal database 
     * @param utente profilo dell'utente che va eliminato 
     */
    public void rimuovi(Utente utente) {
    	em.remove(utente);
	}
    
    /**
     * Metodo che viene chiamato su un utente e restituisce il profilo
     * @return il profilo dell'utente attiv
     */
    public Utente getUtenteAttivo() {
    	return cerca(getUsernameAttivo());
    }
	
    /**
     * Metodo che viene chiamato sull'utente attivo e ne ritrna lo username
     * @return
     */
    public String getUsernameAttivo() {
    	return context.getCallerPrincipal().getName();
    }

    /**
     * Metodo che verifica se un dato utente � attivo 
     */
	@Override
	public boolean isUtente() {
		Utente utente = getUtenteAttivo();
		for(Gruppo gruppo:utente.getGruppi())
			if(gruppo.getNome().equals("DIPENDENTE"))
				return false;
		return true;
	}

	/**
	 * Il metodo elimina tutte le prenotazioni e le condivisioni di un utente che
	 * ha richiesto di eliminare il proprio profilo
	 * @param username - lo username dell'utente
	 */
   @SuppressWarnings("unchecked")
   private void eliminaPrenotazioni(String username){
	   Utente utente = em.find(Utente.class, username);
	   List<Condivisione> condivisioni = em.createQuery("SELECT c FROM Condivisione c WHERE c.utente =:user")
			   .setParameter("user", utente).getResultList();
	   List<Prenotazione_Pacchetto> prenotazioniPacchetto = 
			   em.createQuery("SELECT p FROM Prenotazione_Pacchetto p where p.utente =:utente")
			   .setParameter("utente", utente).getResultList();
	   List<Prenotazione_Viaggio> prenotazioniViaggio = 
			   em.createQuery("SELECT p FROM Prenotazione_Viaggio p where p.utente =:utente")
			   .setParameter("utente", utente).getResultList();
	   for(Condivisione condivisione:condivisioni){
		   em.remove(condivisione);
	   }
	   for(Prenotazione_Pacchetto prenotazione:prenotazioniPacchetto){
		   em.remove(prenotazione);
	   }
	   for(Prenotazione_Viaggio prenotazione:prenotazioniViaggio){
		   em.remove(prenotazione);
	   }
   }

   /**
    * Il metodo invia una mail di notifica nel momento della registrazione di un nuovo utente
    * @param utente - l'utente a cui inviare la mail di notifica
    */
	private void inviaEmail(Utente utente){
		Session mailSession = createSmtpSession();
		mailSession.setDebug (true);

		try {
		    Transport transport = mailSession.getTransport ();

		    MimeMessage message = new MimeMessage (mailSession);

		    message.setSubject ("Welcome");
		    message.setFrom (new InternetAddress ("traveldream.com"));
		    message.setContent ("<h1>Benvenuto in TravelDream "+utente.getUsername()+"</h1>\n"
		    		+ "Utilizza le tue credenziali per cercare il tuo viaggio dei sogni nella "
		    		+ "nostra agenzia.\n"
		    		+ "<h1>Il Team di TravelDream</h1>", "text/html");
		    message.addRecipient (Message.RecipientType.TO, new InternetAddress (utente.getEmail()));

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
