package it.polimi.traveldream.ejb.dto;

import java.util.Date;



public class CondivisioneDTO {
	
	private String link;
	
	private Date data;
	
	private int id_Prenotazione;

	private UtenteDTO utente;

		
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

}