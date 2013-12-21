package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Escursione database table.
 * 
 */
@Entity
@NamedQuery(name="Escursione.findAll", query="SELECT e FROM Escursione e")
public class Escursione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Luogo")
	private String luogo;

	@Column(name="Prezzo")
	private int prezzo;

	//bi-directional many-to-one association to Escursioni_in_Prenotazione
	@OneToMany(mappedBy="escursione")
	private List<Escursioni_in_Prenotazione> escursioniInPrenotaziones;

	//bi-directional many-to-many association to Pacchetto
	@ManyToMany(mappedBy="escursiones")
	private List<Pacchetto> pacchettos;

	public Escursione() {
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

	public int getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public List<Escursioni_in_Prenotazione> getEscursioniInPrenotaziones() {
		return this.escursioniInPrenotaziones;
	}

	public void setEscursioniInPrenotaziones(List<Escursioni_in_Prenotazione> escursioniInPrenotaziones) {
		this.escursioniInPrenotaziones = escursioniInPrenotaziones;
	}

	public Escursioni_in_Prenotazione addEscursioniInPrenotazione(Escursioni_in_Prenotazione escursioniInPrenotazione) {
		getEscursioniInPrenotaziones().add(escursioniInPrenotazione);
		escursioniInPrenotazione.setEscursione(this);

		return escursioniInPrenotazione;
	}

	public Escursioni_in_Prenotazione removeEscursioniInPrenotazione(Escursioni_in_Prenotazione escursioniInPrenotazione) {
		getEscursioniInPrenotaziones().remove(escursioniInPrenotazione);
		escursioniInPrenotazione.setEscursione(null);

		return escursioniInPrenotazione;
	}

	public List<Pacchetto> getPacchettos() {
		return this.pacchettos;
	}

	public void setPacchettos(List<Pacchetto> pacchettos) {
		this.pacchettos = pacchettos;
	}

}