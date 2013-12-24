package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Pacchetto database table.
 * 
 */
@Entity
@NamedQuery(name="Pacchetto.findAll", query="SELECT p FROM Pacchetto p")
public class Pacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Destinazione")
	private String destinazione;

	@Column(name="Fine_Validità")
	private int fine_Validità;

	@Temporal(TemporalType.DATE)
	@Column(name="Inizio_Validità")
	private Date inizio_Validità;

	//bi-directional many-to-many association to Aereo
	@ManyToMany
	@JoinTable(
		name="Aereo_in_Pacchetto"
		, joinColumns={
			@JoinColumn(name="id_Pacchetto")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_Volo")
			}
		)
	private List<Aereo> aereos;

	//bi-directional many-to-many association to Escursione
	@ManyToMany
	@JoinTable(
		name="Escursione_in_Pacchetto"
		, joinColumns={
			@JoinColumn(name="id_Pacchetto")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_Escursione")
			}
		)
	private List<Escursione> escursiones;

	//bi-directional many-to-many association to Hotel
	@ManyToMany
	@JoinTable(
		name="Hotel_in_Pacchetto"
		, joinColumns={
			@JoinColumn(name="id_Pacchetto")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_Hotel")
			}
		)
	private List<Hotel> hotelInPacchetto;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Dipendente")
	private Utente utente;

	public Pacchetto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestinazione() {
		return this.destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public int getFine_Validità() {
		return this.fine_Validità;
	}

	public void setFine_Validità(int fine_Validità) {
		this.fine_Validità = fine_Validità;
	}

	public Date getInizio_Validità() {
		return this.inizio_Validità;
	}

	public void setInizio_Validità(Date inizio_Validità) {
		this.inizio_Validità = inizio_Validità;
	}

	public List<Aereo> getAereos() {
		return this.aereos;
	}

	public void setAereos(List<Aereo> aereos) {
		this.aereos = aereos;
	}

	public List<Escursione> getEscursiones() {
		return this.escursiones;
	}

	public void setEscursiones(List<Escursione> escursiones) {
		this.escursiones = escursiones;
	}

	public List<Hotel> getHotelInPacchetto() {
		return this.hotelInPacchetto;
	}

	public void setHotelInPacchetto(List<Hotel> hotelInPacchetto) {
		this.hotelInPacchetto = hotelInPacchetto;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}