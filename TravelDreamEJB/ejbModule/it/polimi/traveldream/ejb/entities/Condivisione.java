package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Condivisione database table.
 * 
 */
@Entity
@NamedQuery(name="Condivisione.findAll", query="SELECT c FROM Condivisione c")
public class Condivisione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Link")
	private String link;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@ManyToOne
	@JoinColumn(name="id_Prenotazione")
	private Prenotazione_Pacchetto prenotazionePacchetto;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Utente")
	private Utente utente;

	public Condivisione() {
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

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Prenotazione_Pacchetto getPrenotazionePacchetto() {
		return this.prenotazionePacchetto;
	}

	public void setPrenotazionePacchetto(Prenotazione_Pacchetto prenotazionePacchetto) {
		this.prenotazionePacchetto = prenotazionePacchetto;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}