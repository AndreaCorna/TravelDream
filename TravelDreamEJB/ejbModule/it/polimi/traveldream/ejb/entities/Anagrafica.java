package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.AnagraficaDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the Anagrafica database table.
 * 
 */
@Entity
@NamedQuery(name="Anagrafica.findAll", query="SELECT a FROM Anagrafica a")
public class Anagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	public Anagrafica(AnagraficaDTO anagrafica){
		
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