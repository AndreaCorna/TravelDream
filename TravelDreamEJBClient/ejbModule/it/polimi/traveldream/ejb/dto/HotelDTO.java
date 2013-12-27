package it.polimi.traveldream.ejb.dto;

import javax.validation.constraints.NotNull;

public class HotelDTO {

	@NotNull
	private int id;
	@NotNull
	private String citta;
	@NotNull
	private int camereDisponibili;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public int getCamereDisponibili() {
		return camereDisponibili;
	}
	public void setCamereDisponibili(int camereDisponibili) {
		this.camereDisponibili = camereDisponibili;
	}
	
	
}
