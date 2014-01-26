package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * Classe rappresentante l'entity Condivisione presente nel database.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Entity
@Table(name="Condivisione")
@NamedQuery(name="Condivisione.findAll", query="SELECT c FROM Condivisione c")
public class Condivisione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Link")
	private String link;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

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

	public Condivisione(CondivisioneDTO condivisione) {
		this.data = condivisione.getData();
		this.link = condivisione.getLink();
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
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