package it.polimi.traveldream.webBeans;


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

@ManagedBean(name="utente")
@RequestScoped
public class UtenteManagedBean {

	@EJB
	private GestioneUtenteBean gestioneUtente;
	
	private UtenteDTO utente;
	
	private AnagraficaDTO anagrafica;
	
	static private String username;
	
	static private String cf;
	
	public UtenteManagedBean(){
		
		setUtente(new UtenteDTO());
		setAnagrafica(new AnagraficaDTO());
		
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	
	public AnagraficaDTO getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(AnagraficaDTO anagrafica) {
		this.anagrafica = anagrafica;
	}
	
	public String getUsername(){
		//return gestioneUtente.getUtenteDTO().getUsername();
		return utente.getUsername();
	}
	
	public void caricaProfiloUtente(){
		utente = gestioneUtente.getUtenteDTO();
		username = utente.getUsername();
		anagrafica = gestioneUtente.getAnagraficaDTO(utente.getIdAnagrafica());
		cf = anagrafica.getCodiceFiscale();
	}
	
	/*Metodi che comunicano con l'ejb*/
	
	public String registra(){
		gestioneUtente.aggiungiNuovoUtente(utente,anagrafica);
		return "home?faces-redirect=true";
	}
	
	public String modificaProfilo(){
		gestioneUtente.modificaProfiloUtente(utente, anagrafica);
		return "index?faces-redirect=true";
		 
	}
	
	public String eliminaProfilo(){
		gestioneUtente.eliminaProfilo(anagrafica.getCodiceFiscale());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	}
	
	/*Validatori dei dati inseriti*/
	
	public void validaUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestioneUtente.esisteUsername((String)value)){
                throw new ValidatorException(new FacesMessage("Username già utilizzato. Scegline un altro"));
        }
	}
	
	public void validaCodiceFiscale(FacesContext context,UIComponent component,Object value) throws ValidatorException{ 
		if (!isCodiceFiscaleCorretto((String)value)){
            throw new ValidatorException(new FacesMessage("Codice Fiscale errato"));
		}
		if (gestioneUtente.esisteCodiceFiscale((String)value)){
			 throw new ValidatorException(new FacesMessage("Codice Fiscale già presente."));
		}
	}
	
	public void validaUpdateUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestioneUtente.esisteUsername((String)value) && username.compareTo((String)value)!=0){
                throw new ValidatorException(new FacesMessage("Username già utilizzato. Scegline un altro"));
        }
	}
	
	public void validaUpdateCodFis(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isCodiceFiscaleCorretto((String)value)  && cf.compareTo((String)value)!=0){
            throw new ValidatorException(new FacesMessage("Codice Fiscale errato"));
		}
	}
	
	
	
	
	private boolean isCodiceFiscaleCorretto(String codiceFiscale){
		Pattern p1 = Pattern.compile("[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]");
		Matcher m1 = p1.matcher(codiceFiscale);
		return  m1.find();
	}

	public void setUsername(String uname) {
		username = uname;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cod) {
		cf = cod;
	}
	
	
	
}
