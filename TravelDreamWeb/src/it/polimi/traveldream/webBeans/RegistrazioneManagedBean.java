package it.polimi.traveldream.webBeans;


import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;
import it.polimi.traveldream.ejb.dto.*;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="registra")
@RequestScoped
public class RegistrazioneManagedBean {

	@EJB
	private GestioneUtenteBean gestioneUtente;
	
	private UtenteDTO utente;
	
	public RegistrazioneManagedBean(){
		setUtente(new UtenteDTO());
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	
	public String registra(){
		gestioneUtente.aggiungiNuovoUtente(utente);
		return "home?faces-redirect=true";
	}
	
	
	
}
