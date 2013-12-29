package it.polimi.traveldream.webBeans;

import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestioneDipendenteBean;
import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name="dipendente")
@ViewScoped
public class DipendenteManagedBean {
	
	@EJB
	private GestioneDipendenteBean gestioneDip;
	@EJB
	private GestioneUtenteBean gestioneUtente;
	
	private UtenteDTO utente;
	
	
	private List<UtenteDTO> utentiTrovati;
	
	private List<UtenteDTO> listaUtenti;
	

	public DipendenteManagedBean(){
		setUtente(new UtenteDTO());
		listaUtenti = new ArrayList<UtenteDTO>();  
		
	}
	
	public void initUtenti(){
		listaUtenti = gestioneUtente.getListaUtenti();
	}
	
	public void initDipendenti(){
		listaUtenti = gestioneDip.getListaDipendenti();
	}

	
	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	
	public List<UtenteDTO> getUtentiTrovati() {
		return utentiTrovati;
	}

	public void setUtentiTrovati(List<UtenteDTO> utentiTrovati) {
		this.utentiTrovati = utentiTrovati;
	}

	public List<UtenteDTO> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<UtenteDTO> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}
	
	public String creaDipendente(){
		gestioneDip.creaDipendente(utentiTrovati.get(0));
		return "index?faces-redirect=true";
	}
	
	public String eliminaDipendente(){
		gestioneDip.eliminaDipendente(utentiTrovati.get(0));
		return "index?faces-redirect=true";
	}
	
	
	

}
