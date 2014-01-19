package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.NotNull;
/**
 * Classe che rappresenta un DTO che permette il trasferimento di informazioni tra la logica di business e il tier client
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
public class AnagraficaDTO {

	@NotEmpty
	private String codiceFiscale;
	@NotEmpty
	private String nome;
	@NotEmpty
	private String cognome;
	@NotEmpty
	private String luogoNascita;
	@NotNull
	private Date dataNascita;
	@NotEmpty
	private String residenza;
	/*
	 * METODI GETTER E SETTER DEI VARI CAMPI
	 */
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getLuogoNascita() {
		return luogoNascita;
	}
	
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	
	public Date getDataNascita() {
		return dataNascita;
	}
	
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public String getResidenza() {
		return residenza;
	}
	
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
}
