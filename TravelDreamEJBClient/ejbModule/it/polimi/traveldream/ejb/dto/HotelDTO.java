package it.polimi.traveldream.ejb.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class HotelDTO {

	
	private int id;
	@NotNull
	private String città;
	@NotNull
	@Min(1)
	private int camereDisponibili;
	@NotNull
	private String nome;
	@NotNull
	private List<CameraDTO> camere;
	@NotNull
	private Integer rating;
	@NotNull
	private float costoGiornaliero;
	
	private Date dataInizio;
	
	private Date dataFine;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCittà() {
		return città;
	}
	public void setCitta(String città) {
		this.città = città;
	}
	public int getCamereDisponibili() {
		return camereDisponibili;
	}
	public void setCamereDisponibili(int camereDisponibili) {
		this.camereDisponibili = camereDisponibili;
	}
	public List<CameraDTO> getCamere() {
		return camere;
	}
	public void setCamere(List<CameraDTO> camere) {
		this.camere = camere;
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
	
	
}
