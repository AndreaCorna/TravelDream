package it.polimi.traveldream.webBeans;

import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.dataModels.UtenteDataModel;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestioneDipendenteBean;
import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;

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
	
	
	private UtenteDTO utenteTrovato;
	
	private List<UtenteDTO> listaUtenti;
	
	private UtenteDataModel datiUtenti;
	

	public DipendenteManagedBean(){
		setUtente(new UtenteDTO());
		listaUtenti = new ArrayList<UtenteDTO>();  
		
	}
	
	public void initUtenti(){
		listaUtenti = gestioneUtente.getListaUtentiBase();
		datiUtenti = new UtenteDataModel(listaUtenti);
	}
	
	public void initDipendenti(){
		listaUtenti = gestioneDip.getListaDipendenti();
		datiUtenti = new UtenteDataModel(listaUtenti);
	}

	
	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	
	public UtenteDTO getUtenteTrovato() {
		return utenteTrovato;
	}

	public void setUtenteTrovato(UtenteDTO utenteTrovato) {
		this.utenteTrovato = utenteTrovato;
	}

	public List<UtenteDTO> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<UtenteDTO> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}
	
	public String creaDipendente(){
		gestioneDip.creaDipendente(utenteTrovato);
		return "index?faces-redirect=true";
	}
	
	public String eliminaDipendente(){
		gestioneDip.eliminaDipendente(utenteTrovato);
		return "index?faces-redirect=true";
	}

	public UtenteDataModel getDatiUtenti() {
		return datiUtenti;
	}

	public void setDatiUtenti(UtenteDataModel datiUtenti) {
		this.datiUtenti = datiUtenti;
	}
	
	
	

}
