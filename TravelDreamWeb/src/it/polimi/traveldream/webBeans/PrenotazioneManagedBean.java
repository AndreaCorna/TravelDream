package it.polimi.traveldream.webBeans;


import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.dataModels.PrenotazioneDataModel;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean;

import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="prenotazione")
@SessionScoped
public class PrenotazioneManagedBean {

	@EJB
	private GestionePrenotazioneBean gestionePrenotazione;
	
	private List<Prenotazione_PacchettoDTO> listaPrenotazioni;
	
	private List<Prenotazione_PacchettoDTO> prenotazioniSelezionate;
	
	private Prenotazione_PacchettoDTO prenotazionePacchetto;
	
	private PrenotazioneDataModel datiPrenotazione;
	
	@PostConstruct
	public void init(){
		setPrenotazionePacchetto(new Prenotazione_PacchettoDTO());
	}
	public void mostraPrenotazioni(){
		listaPrenotazioni = gestionePrenotazione.getListaPrenotazioni();
		datiPrenotazione = new PrenotazioneDataModel(listaPrenotazioni);
		}
	
	public Prenotazione_PacchettoDTO getPrenotazionePacchetto() {
		return prenotazionePacchetto;
	}
	
	public void setPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazionePacchetto) {
		this.prenotazionePacchetto = prenotazionePacchetto;
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
}
