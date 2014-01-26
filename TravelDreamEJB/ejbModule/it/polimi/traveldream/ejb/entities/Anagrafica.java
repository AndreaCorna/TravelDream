package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * Classe rappresentante l'entity Anagrafica presente nel database.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Entity
@Table(name="Anagrafica")
@NamedQuery(name="Anagrafica.findAll", query="SELECT a FROM Anagrafica a")
public class Anagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	
	@Column(name="CF")
	private String cf;

	@Column(name="Cognome")
	private String cognome;

	@Temporal(TemporalType.DATE)
	@Column(name="Data_Nascita")
	private Date data_Nascita;

	@Column(name="Luogo_Nascita")
	private String luogo_Nascita;

	@Column(name="Nome")
	private String nome;

	@Column(name="Residenza")
	private String residenza;

	public Anagrafica() {
	}
	
	public Anagrafica(UtenteDTO anagrafica){
		this.cf = anagrafica.getCodiceFiscale();
		this.cognome = anagrafica.getCognome();
		this.nome = anagrafica.getNome();
		this.data_Nascita = anagrafica.getDataNascita();
		this.luogo_Nascita = anagrafica.getLuogoNascita();
		this.residenza = anagrafica.getResidenza();
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getData_Nascita() {
		return this.data_Nascita;
	}

	public void setData_Nascita(Date data_Nascita) {
		this.data_Nascita = data_Nascita;
	}

	public String getLuogo_Nascita() {
		return this.luogo_Nascita;
	}

	public void setLuogo_Nascita(String luogo_Nascita) {
		this.luogo_Nascita = luogo_Nascita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResidenza() {
		return this.residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

}