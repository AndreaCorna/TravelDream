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
	
	
	
}
