package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Prenotazione_Viaggio database table.
 * 
 */
@Entity
@Table(name="Prenotazione_Viaggio")
@NamedQuery(name="Prenotazione_Viaggio.findAll", query="SELECT p FROM Prenotazione_Viaggio p")
public class Prenotazione_Viaggio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	//uni-directional many-to-one association to Aereo
	@ManyToOne
	@JoinColumn(name="id_Aereo_Andata")
	private Aereo aereo1;

	//uni-directional many-to-one association to Aereo
	@ManyToOne
	@JoinColumn(name="id_Aereo_Ritorno")
	private Aereo aereo2;

	//uni-directional many-to-many association to Escursione
	@ManyToMany
	@JoinTable(
		name="Escursioni_in_Prenotazione_Viaggio"
		, joinColumns={
			@JoinColumn(name="id_Prenotazione")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_Escursione")
			}
		)
	private List<Escursione> escursioni;

	//uni-directional many-to-one association to Hotel
	@ManyToOne
	@JoinColumn(name="id_Hotel")
	private Hotel hotel;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Utente")
	private Utente utente;

	public Prenotazione_Viaggio() {
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

	public Aereo getAereo1() {
		return this.aereo1;
	}

	public void setAereo1(Aereo aereo1) {
		this.aereo1 = aereo1;
	}

	public Aereo getAereo2() {
		return this.aereo2;
	}

	public void setAereo2(Aereo aereo2) {
		this.aereo2 = aereo2;
	}

	public List<Escursione> getEscursioni() {
		return this.escursioni;
	}

	public void setEscursioni(List<Escursione> escursioni) {
		this.escursioni = escursioni;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}