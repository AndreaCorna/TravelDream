package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the Aereo database table.
 * 
 */
@Entity
@Table(name="Aereo")
@NamedQuery(name="Aereo.findAll", query="SELECT a FROM Aereo a")
public class Aereo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="Atterraggio")
	private String atterraggio;

	@Column(name="Costo")
	private int costo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Decollo")
	private String decollo;

	@Column(name="Posti_Disponibili")
	private int posti_Disponibili;

	public Aereo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAtterraggio() {
		return this.atterraggio;
	}

	public void setAtterraggio(String atterraggio) {
		this.atterraggio = atterraggio;
	}

	public int getCosto() {
		return this.costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDecollo() {
		return this.decollo;
	}

	public void setDecollo(String decollo) {
		this.decollo = decollo;
	}

	public int getPosti_Disponibili() {
		return this.posti_Disponibili;
	}

	public void setPosti_Disponibili(int posti_Disponibili) {
		this.posti_Disponibili = posti_Disponibili;
	}

}