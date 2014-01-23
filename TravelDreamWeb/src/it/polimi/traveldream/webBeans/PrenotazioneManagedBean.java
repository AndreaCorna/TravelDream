package it.polimi.traveldream.webBeans;


import java.util.List;

import it.polimi.traveldream.dataModels.PrenotazioneDataModel;
import it.polimi.traveldream.dataModels.PrenotazioneViaggioDataModel;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean;

import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

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
	
	private PacchettoManagedBean bean = new PacchettoManagedBean();
	
	@PostConstruct
	public void init(){
		setPrenotazionePacchetto(new Prenotazione_PacchettoDTO());
		setPrenotazioneViaggio(new Prenotazione_ViaggioDTO());
	}
		public void mostraPrenotazioni(){
		listaPrenotazioni = gestionePrenotazione.getListaPrenotazioni();
		datiPrenotazione = new PrenotazioneDataModel(listaPrenotazioni);
		
		listaPrenotazioniViaggi = gestionePrenotazione.getListaPrenotazioniViaggio();
		datiPrenotazioneViaggio = new PrenotazioneViaggioDataModel(listaPrenotazioniViaggi);
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
}
