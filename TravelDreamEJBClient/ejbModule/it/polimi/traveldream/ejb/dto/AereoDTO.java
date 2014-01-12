package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;



public class AereoDTO {

	
	private int id;
	@NotEmpty
	private String cittaDecollo;
	@NotEmpty
	private String cittaAtterraggio;
	@NotNull
	private Date data;
	@NotNull
	@Min(1)
	private int postiDisponibili;
	@NotNull
	@Min(0)
	private float costo;

		
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getCittaDecollo() {
		return cittaDecollo;
	}

	public void setCittaDecollo(String cittaDecollo) {
		this.cittaDecollo = cittaDecollo;
	}

	public String getCittaAtterraggio() {
		return cittaAtterraggio;
	}

	public void setCittaAtterraggio(String cittaAtterraggio) {
		this.cittaAtterraggio = cittaAtterraggio;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getPostiDisponibili() {
		return postiDisponibili;
	}

	public void setPostiDisponibili(int postiDisponibili) {
		this.postiDisponibili = postiDisponibili;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	
	
}
