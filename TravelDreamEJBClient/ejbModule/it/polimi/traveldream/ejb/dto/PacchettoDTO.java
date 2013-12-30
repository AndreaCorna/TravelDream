package it.polimi.traveldream.ejb.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class PacchettoDTO {

	@NotNull
	private int id;
	@NotEmpty
	private String destinazione;
	@NotNull
	private int fine_Validita;
	@NotNull
	private Date inizio_Validita;
	@NotEmpty
	private List<AereoDTO> aerei;
	@NotEmpty
	private List<EscursioneDTO> escursioni;
	@NotEmpty
	private List<HotelDTO> hotels;
	@NotEmpty
	private UtenteDTO dipendente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public int getFine_Validita() {
		return fine_Validita;
	}

	public void setFine_Validita(int fine_Validita) {
		this.fine_Validita = fine_Validita;
	}

	public Date getInizio_Validita() {
		return inizio_Validita;
	}

	public void setInizio_Validita(Date inizio_Validita) {
		this.inizio_Validita = inizio_Validita;
	}

	public List<AereoDTO> getAerei() {
		return aerei;
	}

	public void setAerei(List<AereoDTO> aerei) {
		this.aerei = aerei;
	}

	public List<EscursioneDTO> getEscursioni() {
		return escursioni;
	}

	public void setEscursioni(List<EscursioneDTO> escursioni) {
		this.escursioni = escursioni;
	}

	public List<HotelDTO> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelDTO> hotels) {
		this.hotels = hotels;
	}

	public UtenteDTO getDipendente() {
		return dipendente;
	}

	public void setDipendente(UtenteDTO dipendente) {
		this.dipendente = dipendente;
	}
}
