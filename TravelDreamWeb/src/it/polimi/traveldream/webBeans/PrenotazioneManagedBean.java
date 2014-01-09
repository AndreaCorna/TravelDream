package it.polimi.traveldream.webBeans;


import java.util.ArrayList;
import java.util.List;

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
	
	private List<Prenotazione_PacchettoDTO> listaPacchetti;
	
	private Prenotazione_PacchettoDTO prenotazionePacchetto;
	
	@PostConstruct
	public void init(){
		prenotazionePacchetto = new Prenotazione_PacchettoDTO();
	}
	public void mostraPrenotazioni(){
		listaPacchetti = gestionePrenotazione.getListaPrenotazioni();
		caricaPrenotazioni();
	}
	
	private void caricaPrenotazioni(){
		ArrayList<String> listaPrenotazioni = new ArrayList<String>();
		for(Prenotazione_PacchettoDTO prenotazione:listaPacchetti){
				listaPrenotazioni.add(prenotazione.getAereoAndata().getCittaAtterraggio().toUpperCase());
		}
		}
}
