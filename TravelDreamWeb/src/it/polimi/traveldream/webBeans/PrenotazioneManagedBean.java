package it.polimi.traveldream.webBeans;


import it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean;

import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="prenotazione")
@SessionScoped
public class PrenotazioneManagedBean {

	@EJB
	private GestionePrenotazioneBean gestionePrenotazione;

}
