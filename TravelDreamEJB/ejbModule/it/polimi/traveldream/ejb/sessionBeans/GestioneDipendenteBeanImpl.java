package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Gruppo;
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
 * Session bean per la gestione dei dipendenti.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Stateless
public class GestioneDipendenteBeanImpl implements GestioneDipendenteBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public GestioneDipendenteBeanImpl() {
       
    }
    
    /**
     * Metodo che dato un utente lo rende dipendente
     */
	@Override
	@RolesAllowed({"AMMINISTRATORE"})
	public void creaDipendente(UtenteDTO utenteDTO) {
		Utente nuovoDip = em.find(Utente.class, utenteDTO.getUsername());
		List<Gruppo> gruppi = new ArrayList<Gruppo>();
		Gruppo nuovoGruppo = new Gruppo();
		nuovoGruppo.setNome("DIPENDENTE");
		gruppi.add(nuovoGruppo);
		nuovoDip.setGruppi(gruppi);
		Utente amministratore = em.find(Utente.class, context.getCallerPrincipal().getName());
		nuovoDip.setAmministratoreCreatore(amministratore);
		em.merge(nuovoDip);
		MailThread mail = new MailThread(nuovoDip);
		Thread threadMail = new Thread(mail);
		threadMail.start();
		
	}
	
	/**
	 * Metodo che permette di restituire la lista dei dipendenti 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"AMMINISTRATORE"})
	public List<UtenteDTO> getListaDipendenti() {
		List<Utente> dipendentiDB = em.createQuery("SELECT u FROM Utente u, IN (u.gruppi) g WHERE g.nome =:nome")
			    .setParameter("nome", "DIPENDENTE").getResultList();
    	ArrayList<UtenteDTO> dipendenti = new ArrayList<UtenteDTO>();
    	for(int i=0; i<dipendentiDB.size();i++){
    		UtenteDTO user = ConverterDTO.convertToDTO(dipendentiDB.get(i));
    		dipendenti.add(user);
    	}
    	List<UtenteDTO> listaDip = dipendenti;
    	return listaDip;
	}
	
	/**
	 * Metodo che dato un dipendente permette di eliminarlo e quindi di retrocederlo a utente
	 */
	@Override
	@RolesAllowed({"AMMINISTRATORE"})
	public void eliminaDipendente(UtenteDTO utenteDTO) {
		Utente dip = em.find(Utente.class, utenteDTO.getUsername());
		List<Gruppo> gruppi = new ArrayList<Gruppo>();
		Gruppo nuovoGruppo = new Gruppo();
		nuovoGruppo.setNome("UTENTE");
		gruppi.add(nuovoGruppo);
		dip.setGruppi(gruppi);
		dip.setAmministratoreCreatore(null);
		em.merge(dip);
		
	}
	
	/**
	 * Inner class che implementa il thread per l'invio della mail che notifica all'utente l'upgrade del proprio
	 * account a dipendente
	 * @author Alessandro Brunitti - Andrea Corna
	 *
	 */
	class MailThread implements Runnable{
		   
		   private Utente utente;
		   
		   public MailThread(){
			   
		   }
		   
		   public MailThread(Utente utente){
			   this.utente = utente;
		   }
		   
		   @Override
		   public void run() {
			   Session mailSession = createSmtpSession();
				mailSession.setDebug (true);

				try {
				    Transport transport = mailSession.getTransport ();

				    MimeMessage message = new MimeMessage (mailSession);

				    message.setSubject ("Welcome");
				    message.setFrom (new InternetAddress ("traveldream.com"));
				    message.setContent ("<h1>Caro "+utente.getUsername()+"</h1>\n "
				    		+ "Da questo momento sei un nostro dipendente e il tuo account verra "
				    		+ "arricchito con nuove funzionalita\n."
				    		+ "Buon Lavoro!\n"
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
	
}
