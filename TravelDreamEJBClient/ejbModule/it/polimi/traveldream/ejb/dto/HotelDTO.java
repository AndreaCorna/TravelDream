package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Classe per creare oggetti HotelDTO necessari per il passaggio di informazioni tra la business logic e il client tier
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
public class HotelDTO {

	
	private int id;
	@NotNull
	private String citta;
	@NotNull
	@Min(1)
	private int camereDisponibili;
	@NotNull
	private String nome;
	@NotNull
	private Integer rating;
	@NotNull
	private float costoGiornaliero;
	
	private Date dataInizio;
	
	private Date dataFine;
	
	private byte valido;
	
	
	
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public float getCostoGiornaliero() {
		return costoGiornaliero;
	}
	public void setCostoGiornaliero(float costoGiornaliero) {
		this.costoGiornaliero = costoGiornaliero;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public byte getValido() {
		return valido;
	}
	public void setValido(byte valido) {
		this.valido = valido;
	}
	
	
}
