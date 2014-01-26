package it.polimi.traveldream.ejb.dto;

import java.util.Date;


/**
 * Classe per creare oggetti CondivisioneDTO necessari per il passaggio di informazioni tra la business logic e il client tier
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
public class CondivisioneDTO {
	
	private String link;
	
	private Date data;
	
	private int id_Prenotazione;

	private UtenteDTO utente;
	
	private Prenotazione_PacchettoDTO prenotazione;

		
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getId_Prenotazione() {
		return id_Prenotazione;
	}

	public void setId_Prenotazione(int id_Prenotazione) {
		this.id_Prenotazione = id_Prenotazione;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public Prenotazione_PacchettoDTO getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione_PacchettoDTO prenotazione) {
		this.prenotazione = prenotazione;
	}

}
