package it.polimi.traveldream.ejb.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class Prenotazione_ViaggioDTO {

	@NotNull
	private int id;
	@NotNull
	private Date data;
	@NotNull
	private AereoDTO aereoAndata;
	@NotNull
	private AereoDTO aereoRitorno;
	@NotEmpty
	private List<EscursioneDTO> escursioni;
	@NotNull
	private HotelDTO hotel;
	@NotNull
	private UtenteDTO utente;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}
	
	public List<EscursioneDTO> getEscursioni() {
		return escursioni;
	}

	public void setEscursioni(List<EscursioneDTO> escursioni) {
		this.escursioni = escursioni;
	}
	
	public AereoDTO getAereoAndata() {
		return aereoAndata;
	}

	public void setAereo(AereoDTO aereoAndata) {
		this.aereoAndata = aereoAndata;
	}
	
	public AereoDTO getAereoRitorno() {
		return aereoRitorno;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	public void setAereoRitorno(AereoDTO aereoRitorno) {
		this.aereoRitorno = aereoRitorno;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
}