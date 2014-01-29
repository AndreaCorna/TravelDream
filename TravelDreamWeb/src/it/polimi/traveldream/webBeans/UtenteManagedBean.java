package it.polimi.traveldream.webBeans;


import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;
import it.polimi.traveldream.ejb.dto.*;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Il managed bean fornisce tutte le funzioni necessarie per la gestione dell'utente
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@ManagedBean(name="utente")
@RequestScoped
public class UtenteManagedBean {

	@EJB
	private GestioneUtenteBean gestioneUtente;
	
	private UtenteDTO utente;
	
	static private String username;
	
	static private String cf;
	
	private Date oggi = new Date();
	
	/**
	 * Costruttore
	 */
	public UtenteManagedBean(){
		setUtente(new UtenteDTO());
	}	
	
	/**
	 * Il metodo ritorna lo username dell'utente attivo 
	 * @return lo username dell'utente attivo
	 */
	public String getUsername(){
		return utente.getUsername();
	}
	
	/**
	 * Il metodo carica le informazioni del profilo dell'utente attivo
	 */
	public void caricaProfiloUtente(){
		utente = gestioneUtente.getUtenteDTO();
		username = utente.getUsername();
		cf = utente.getCodiceFiscale();
	}
	
	/**
	 * Il metodo permette di inserire un nuovo utente, prese tutte le informazioni inserite dall'utente
	 * @return redirect alla homepage principale del sito
	 */
	public String registra(){
		gestioneUtente.aggiungiNuovoUtente(utente);
		return "home?faces-redirect=true";
	}
	
	/**
	 * Il metodo permette di aggiornare il profilo dell'utente attivo
	 * @return redirect alla homepage dell'utente
	 */
	public String modificaProfilo(){
		utente.setUsername(username);
		gestioneUtente.modificaProfiloUtente(utente);
		return "index?faces-redirect=true";
		 
	}
	
	/**
	 * Il metodo elimina il profilo dell'utente corrente e chiude la sessione
	 * @return redirect alla homepage principale
	 */
	public String eliminaProfilo(){
		gestioneUtente.eliminaProfilo(utente.getCodiceFiscale());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	}
	
	/**
	 * Il metodo verifica se lo username inserito è disponibile
	 * @param context - il contesto attivo
	 * @param component - il componente selezionato
	 * @param value - il valore del componente
	 * @throws ValidatorException - eccezione generata se lo username esiste già nel database
	 */
	public void validaUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestioneUtente.esisteUsername((String)value)){
                throw new ValidatorException(new FacesMessage("Username già utilizzato. Scegline un altro"));
        }
	}
	
	/**
	 * Il metodo verifica il codice fiscale rispetta il patter specificato
	 * @param context - il contesto attivo
	 * @param component - il componente selezionato
	 * @param value - il valore del componente
	 * @throws ValidatorException - eccezione generata se il codice fiscale non rispetta il patter
	 */
	public void validaCodiceFiscale(FacesContext context,UIComponent component,Object value) throws ValidatorException{ 
		if (!isCodiceFiscaleCorretto((String)value)){
            throw new ValidatorException(new FacesMessage("Codice Fiscale errato"));
		}
		if (gestioneUtente.esisteCodiceFiscale((String)value)){
			 throw new ValidatorException(new FacesMessage("Codice Fiscale già presente."));
		}
	}
	
	/**
	 * Il metodo verifica se lo username inserito è disponibile
	 * @param context - il contesto attivo
	 * @param component - il componente selezionato
	 * @param value - il valore del componente
	 * @throws ValidatorException - eccezione generata se lo username esiste già nel database
	 */
	public void validaUpdateUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestioneUtente.esisteUsername((String)value) && username.compareTo((String)value)!=0){
                throw new ValidatorException(new FacesMessage("Username già utilizzato. Scegline un altro"));
        }
	}
	
	/**
	 * Il metodo verifica il codice fiscale rispetta il patter specificato
	 * @param context - il contesto attivo
	 * @param component - il componente selezionato
	 * @param value - il valore del componente
	 * @throws ValidatorException - eccezione generata se il codice fiscale non rispetta il patter
	 */
	public void validaUpdateCodFis(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isCodiceFiscaleCorretto((String)value)  && cf.compareTo((String)value)!=0){
            throw new ValidatorException(new FacesMessage("Codice Fiscale errato"));
		}
	}
	
	/**
	 * Il metodo verifica il patter del codice fiscale
	 * @param codiceFiscale - il codice fiscale da controllare
	 * @return <true> se il codice fiscale rispetta il patter, <false> altrimenti
	 */
	private boolean isCodiceFiscaleCorretto(String codiceFiscale){
		Pattern p1 = Pattern.compile("[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]");
		Matcher m1 = p1.matcher(codiceFiscale);
		return  m1.find();
	}

	/*METODI GETTER E SETTER*/
	
	public void setUsername(String uname) {
		username = uname;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cod) {
		cf = cod;
	}

	public Date getOggi() {
		return oggi;
	}

	public void setOggi(Date oggi) {
		this.oggi = oggi;
	}
	
	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	
	
	
}
