package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.EscursioneDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the Escursione database table.
 * 
 */
@Entity
@Table(name="Escursione")
@NamedQuery(name="Escursione.findAll", query="SELECT e FROM Escursione e")
public class Escursione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Luogo")
	private String luogo;

	@Column(name="Prezzo")
	private float prezzo;
	
	@Column(name="Descrizione")
	private String descrizione;

	public Escursione() {
	}

	public Escursione(EscursioneDTO escursione) {
		this.id = escursione.getId();
		this.luogo = escursione.getLuogo();
		this.data = escursione.getData();
		this.prezzo = escursione.getPrezzo();
		this.setDescrizione(escursione.getDescrizione());
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLuogo() {
		return this.luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public float getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}