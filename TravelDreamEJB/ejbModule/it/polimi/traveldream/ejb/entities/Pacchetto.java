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

@NamedQueries({
	@NamedQuery(name="Pacchetto.findAll", query="SELECT p FROM Pacchetto p"),
	@NamedQuery(name="Pacchetto.findValidi", query="SELECT p FROM Pacchetto p WHERE p.valido=1")
})
public class Pacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="Destinazione")
	private String destinazione;

	@Temporal(TemporalType.DATE)
	@Column(name="Fine_Validita")
	private Date fine_Validita;

	@Temporal(TemporalType.DATE)
	@Column(name="Inizio_Validita")
	private Date inizio_Validita;
	
	@Column(name="Descrizione")
	private String descrizione;
	
	@Column(name="Numero_Persone")
	private int numeroPersone;
	
	@Column(name="Valido")
	private byte valido;

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


	public Date getFine_Validita() {
		return this.fine_Validita;
	}

	public void setFine_Validita(Date fine_Validita) {
		this.fine_Validita= fine_Validita;
	}

	public Date getInizio_Validita() {
		return this.inizio_Validita;
	}

	public void setInizio_Validita(Date inizio_Validita) {
		this.inizio_Validita= inizio_Validita;
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

	public int getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public byte getValido() {
		return valido;
	}

	public void setValido(byte valido) {
		this.valido = valido;
	}

}