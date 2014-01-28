package it.polimi.traveldream.webBeans;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import it.polimi.traveldream.dataModels.PrenotazioneDataModel;
import it.polimi.traveldream.dataModels.PrenotazioneViaggioDataModel;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;


@ManagedBean(name="prenotazione")
@SessionScoped
public class PrenotazioneManagedBean {

	@EJB
	private GestionePrenotazioneBean gestionePrenotazione;
	
	private List<Prenotazione_PacchettoDTO> listaPrenotazioni;
	
	private List<Prenotazione_PacchettoDTO> prenotazioniSelezionate;
	
	private List<Prenotazione_ViaggioDTO> prenotazioniViaggioSelezionate;
	
	private List<Prenotazione_ViaggioDTO> listaPrenotazioniViaggi;
	
	private Prenotazione_PacchettoDTO prenotazionePacchetto;
	
	private PrenotazioneDataModel datiPrenotazione;
	
	private PrenotazioneViaggioDataModel datiPrenotazioneViaggio;
	
	private Prenotazione_ViaggioDTO prenotazioneViaggio;
	
	private Prenotazione_PacchettoDTO selezione;
	
	
		
	@PostConstruct
	public void init(){
		setPrenotazionePacchetto(new Prenotazione_PacchettoDTO());
		setPrenotazioneViaggio(new Prenotazione_ViaggioDTO());
		
	}
	/**
	 * Costrutture di default
	 */
		public void mostraPrenotazioni(){
		listaPrenotazioni = gestionePrenotazione.getListaPrenotazioni();
		datiPrenotazione = new PrenotazioneDataModel(listaPrenotazioni);
		
		listaPrenotazioniViaggi = gestionePrenotazione.getListaPrenotazioniViaggio();
		datiPrenotazioneViaggio = new PrenotazioneViaggioDataModel(listaPrenotazioniViaggi);
		}
	
	/**
	 * Metodo che viene richiamato dalla home per mostrare le prenotazioni 
	 * @return la pagina alla quale effettuare il redirect
	 */
	@SuppressWarnings("deprecation")
	public String modificaPrenotazione(){
		Date date = new Date();
		Date oggi = new Date(date.getYear(),date.getMonth(),date.getDate());
		Date dataPrenotazione = new Date(selezione.getAereoAndata().getData().getYear(),selezione.getAereoAndata().getData().getMonth(),selezione.getAereoAndata().getData().getDate());
		int diffInDays = (int)( (dataPrenotazione.getTime() - oggi.getTime())
                / (1000 * 60 * 60 * 24.0) );
		if(selezione.getPacchetto().getValido() == 0){
			String message = "Non puoi modificare questa prenotazione, il pacchetto non e piu valido";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return null;
		}else if(diffInDays < 7){
			String message = "Non puoi modificare questa prenotazione, tra pochi giorni parti!";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return null;
		}
		else
			return "buyPacchetto?id="+selezione.getPacchetto().getId()+"&pre="+selezione.getId()+"&faces-redirect=true";
	}
	
//METODI GETTER E SETTER
	
	public void settaPrenotazione(String id){
		System.out.println("In setta selezione id "+id);
		int value = (new Integer(id)).intValue();
		boolean found = false;
		for(int i=0;i<listaPrenotazioni.size() && !found;i++){
			if(listaPrenotazioni.get(i).getId() == value){
				selezione = listaPrenotazioni.get(i);
				found = true;
			}
		}
	}
	
	public Prenotazione_PacchettoDTO getPrenotazionePacchetto() {
		return prenotazionePacchetto;
	}
	
	public void setPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazionePacchetto) {
		this.prenotazionePacchetto = prenotazionePacchetto;
	}
	
	public void setPrenotazioneViaggio(Prenotazione_ViaggioDTO prenotazioneViaggio) {
		this.prenotazioneViaggio = prenotazioneViaggio;
	}
	public PrenotazioneDataModel getDatiPrenotazione() {
		return datiPrenotazione;
	}
	
	public void setDatiPrenotazione(PrenotazioneDataModel datiPrenotazione) {
		this.datiPrenotazione = datiPrenotazione;
	}
	public List<Prenotazione_PacchettoDTO> getPrenotazioniSelezionate() {
		return prenotazioniSelezionate;
	}
	public void setPrenotazioniSelezionate(List<Prenotazione_PacchettoDTO> prenotazioniSelezionate) {
		this.prenotazioniSelezionate = prenotazioniSelezionate;
	}
	public PrenotazioneViaggioDataModel getDatiPrenotazioneViaggio() {
		return datiPrenotazioneViaggio;
	}
	public void setDatiPrenotazioneViaggio(PrenotazioneViaggioDataModel datiPrenotazioneViaggio) {
		this.datiPrenotazioneViaggio = datiPrenotazioneViaggio;
	}
	public List<Prenotazione_ViaggioDTO> getPrenotazioniViaggioSelezionate() {
		return prenotazioniViaggioSelezionate;
	}
	public void setPrenotazioniViaggioSelezionate(
			List<Prenotazione_ViaggioDTO> prenotazioniViaggioSelezionate) {
		this.prenotazioniViaggioSelezionate = prenotazioniViaggioSelezionate;
	}
	public Prenotazione_PacchettoDTO getSelezione() {
		return selezione;
	}
	public void setSelezione(Prenotazione_PacchettoDTO selezione) {
		this.selezione = selezione;
	}
	
}
