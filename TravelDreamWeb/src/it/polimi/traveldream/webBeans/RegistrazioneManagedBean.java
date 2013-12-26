package it.polimi.traveldream.webBeans;


import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;
import it.polimi.traveldream.ejb.dto.*;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean(name="registra")
@RequestScoped
public class RegistrazioneManagedBean {

	@EJB
	private GestioneUtenteBean gestioneUtente;
	
	private UtenteDTO utente;
	
	private AnagraficaDTO anagrafica;
	
	public RegistrazioneManagedBean(){
		setUtente(new UtenteDTO());
		setAnagrafica(new AnagraficaDTO());
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	
	public String registra(){
		gestioneUtente.aggiungiNuovoUtente(utente,anagrafica);
		return "home?faces-redirect=true";
	}

	public AnagraficaDTO getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(AnagraficaDTO anagrafica) {
		this.anagrafica = anagrafica;
	}
	
	public void validaUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestioneUtente.esisteUsername((String)value)){
                throw new ValidatorException(new FacesMessage("Username già utilizzato. Scegline un altro"));
        }
	}
	
	public void validaCodiceFiscale(FacesContext context,UIComponent component,Object value) throws ValidatorException{ 
		if (!isCodiceFiscaleCorretto((String)value)){
            throw new ValidatorException(new FacesMessage("Codice Fiscale non coerente con i dati inseriti"));
		}
	}
	
	private boolean isCodiceFiscaleCorretto(String codiceFiscale){
		
		return true;
	}
	
	
	
}
