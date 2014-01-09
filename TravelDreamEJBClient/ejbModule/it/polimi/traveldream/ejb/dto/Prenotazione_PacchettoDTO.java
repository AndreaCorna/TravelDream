package it.polimi.traveldream.ejb.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class Prenotazione_PacchettoDTO {

	@NotNull
	private int id;
	@NotNull
	private Date data;
	@NotEmpty
	private List<CondivisioneDTO> condivisioni;
	@NotEmpty
	private List<EscursioneDTO> escursioni;
	@NotNull
	private AereoDTO aereoAndata;
	@NotNull
	private AereoDTO aereoRitorno;
	@NotNull
	private HotelDTO hotel;
	@NotNull
	private PacchettoDTO pacchetto;
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
	
	public List<CondivisioneDTO> getCondivisione(){
		return condivisioni;
	}
	
	public void setCondivisioni(List<CondivisioneDTO> condivisioni){
		this.condivisioni = condivisioni;
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
	
	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public void setData(Date data2) {
		// TODO Auto-generated method stub
		
	}
}