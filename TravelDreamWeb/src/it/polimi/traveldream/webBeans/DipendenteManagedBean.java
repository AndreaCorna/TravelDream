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

/**
 * Il managed bean fornisce alla user interface i metodi necessari per l'interazione con l'application business 
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
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
	

	/**
	 * Costruttore di default
	 */
	public DipendenteManagedBean(){
		setUtente(new UtenteDTO());
		listaUtenti = new ArrayList<UtenteDTO>();  
		
	}
	
	/**
	 * Metodo che si occupa dell'inizializzazione della pagina utente
	 */
	public void initUtenti(){
		listaUtenti = gestioneUtente.getListaUtentiBase();
		datiUtenti = new UtenteDataModel(listaUtenti);
	}
	

	/**
	 * Metodo che si occupa dell'inizializzazione della pagina dipendente
	 */
	public void initDipendenti(){
		listaUtenti = gestioneDip.getListaDipendenti();
		datiUtenti = new UtenteDataModel(listaUtenti);
	}

	/**
	 * Metodo che elimina un dipendente dal database 
	 * @return la pagina alla quale si viene rediretti
	 */
	public String eliminaDipendente(){
		gestioneDip.eliminaDipendente(utenteTrovato);
		return "index?faces-redirect=true";
	}
	
	/**
	 * Il metodo chiama il metodo del session bean per elevare un utente al ruolo di dipendente.
	 * @return redirect alla homepage
	 */
	public String creaDipendente(){
		gestioneDip.creaDipendente(utenteTrovato);
		return "index?faces-redirect=true";
	}
	
	/*
	 * Metodi GETTER E SETTER
	 */
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
	
	public UtenteDataModel getDatiUtenti() {
		return datiUtenti;
	}

	public void setDatiUtenti(UtenteDataModel datiUtenti) {
		this.datiUtenti = datiUtenti;
	}
	
	
	

}
