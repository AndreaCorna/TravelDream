package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Prenotazione_Pacchetto database table.
 * 
 */
@Entity
@NamedQuery(name="Prenotazione_Pacchetto.findAll", query="SELECT p FROM Prenotazione_Pacchetto p")
public class Prenotazione_Pacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	//bi-directional many-to-one association to Condivisione
	@OneToMany(mappedBy="prenotazionePacchetto")
	private List<Condivisione> condivisiones;

	//bi-directional many-to-one association to Escursioni_in_Prenotazione
	@OneToMany(mappedBy="prenotazionePacchetto")
	private List<Escursioni_in_Prenotazione> escursioniInPrenotaziones;

	//bi-directional many-to-one association to Aereo
	@ManyToOne
	@JoinColumn(name="id_Aereo_Andata")
	private Aereo aereo1;

	//bi-directional many-to-one association to Aereo
	@ManyToOne
	@JoinColumn(name="id_Aereo_Ritorno")
	private Aereo aereo2;

	//bi-directional many-to-one association to Hotel
	@ManyToOne
	@JoinColumn(name="id_Hotel")
	private Hotel hotel;

	//bi-directional many-to-one association to Pacchetto
	@ManyToOne
	@JoinColumn(name="id_Pacchetto")
	private Pacchetto pacchetto;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Utente")
	private Utente utente;

	public Prenotazione_Pacchetto() {
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

	public List<Condivisione> getCondivisiones() {
		return this.condivisiones;
	}

	public void setCondivisiones(List<Condivisione> condivisiones) {
		this.condivisiones = condivisiones;
	}

	public Condivisione addCondivisione(Condivisione condivisione) {
		getCondivisiones().add(condivisione);
		condivisione.setPrenotazionePacchetto(this);

		return condivisione;
	}

	public Condivisione removeCondivisione(Condivisione condivisione) {
		getCondivisiones().remove(condivisione);
		condivisione.setPrenotazionePacchetto(null);

		return condivisione;
	}

	public List<Escursioni_in_Prenotazione> getEscursioniInPrenotaziones() {
		return this.escursioniInPrenotaziones;
	}

	public void setEscursioniInPrenotaziones(List<Escursioni_in_Prenotazione> escursioniInPrenotaziones) {
		this.escursioniInPrenotaziones = escursioniInPrenotaziones;
	}

	public Escursioni_in_Prenotazione addEscursioniInPrenotazione(Escursioni_in_Prenotazione escursioniInPrenotazione) {
		getEscursioniInPrenotaziones().add(escursioniInPrenotazione);
		escursioniInPrenotazione.setPrenotazionePacchetto(this);

		return escursioniInPrenotazione;
	}

	public Escursioni_in_Prenotazione removeEscursioniInPrenotazione(Escursioni_in_Prenotazione escursioniInPrenotazione) {
		getEscursioniInPrenotaziones().remove(escursioniInPrenotazione);
		escursioniInPrenotazione.setPrenotazionePacchetto(null);

		return escursioniInPrenotazione;
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

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Pacchetto getPacchetto() {
		return this.pacchetto;
	}

	public void setPacchetto(Pacchetto pacchetto) {
		this.pacchetto = pacchetto;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}