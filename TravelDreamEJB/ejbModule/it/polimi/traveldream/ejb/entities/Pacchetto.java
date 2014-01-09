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
@Table(name="Pacchetto")
@NamedQuery(name="Pacchetto.findAll", query="SELECT p FROM Pacchetto p")
public class Pacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="Destinazione")
	private String destinazione;

	@Temporal(TemporalType.DATE)
	@Column(name="Fine_Validità")
	private Date fine_Validità;

	@Temporal(TemporalType.DATE)
	@Column(name="Inizio_Validità")
	private Date inizio_Validità;
	
	@Column(name="Descrizione")
	private String descrizione;

	//uni-directional many-to-many association to Aereo
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
	private List<Aereo> aerei;

	//uni-directional many-to-many association to Escursione
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
	private List<Escursione> escursioni;

	//uni-directional many-to-many association to Hotel
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
	private List<Hotel> hotels;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Dipendente")
	private Utente dipendente;

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


	public Date getFine_Validità() {
		return this.fine_Validità;
	}

	public void setFine_Validità(Date fine_Validità) {
		this.fine_Validità= fine_Validità;
	}

	public Date getInizio_Validità() {
		return this.inizio_Validità;
	}

	public void setInizio_Validità(Date inizio_Validità) {
		this.inizio_Validità= inizio_Validità;
	}

	public List<Aereo> getAerei() {
		return this.aerei;
	}

	public void setAerei(List<Aereo> aerei) {
		this.aerei = aerei;
	}

	public List<Escursione> getEscursioni() {
		return this.escursioni;
	}

	public void setEscursioni(List<Escursione> escursioni) {
		this.escursioni = escursioni;
	}

	public List<Hotel> getHotels() {
		return this.hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public Utente getDipendente() {
		return this.dipendente;
	}

	public void setDipendente(Utente dipendente) {
		this.dipendente = dipendente;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}