package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Escursioni_in_Prenotazione database table.
 * 
 */
@Entity
@NamedQuery(name="Escursioni_in_Prenotazione.findAll", query="SELECT e FROM Escursioni_in_Prenotazione e")
public class Escursioni_in_Prenotazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Escursioni_in_PrenotazionePK id;

	//bi-directional many-to-one association to Escursione
	@ManyToOne
	@JoinColumn(name="id_Escursione")
	private Escursione escursione;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@ManyToOne
	@JoinColumn(name="id_Prenotazione")
	private Prenotazione_Pacchetto prenotazionePacchetto;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@ManyToOne
	@JoinColumn(name="id_Prenotazione")
	private Prenotazione_Viaggio prenotazioneViaggio;

	public Escursioni_in_Prenotazione() {
	}

	public Escursioni_in_PrenotazionePK getId() {
		return this.id;
	}

	public void setId(Escursioni_in_PrenotazionePK id) {
		this.id = id;
	}

	public Escursione getEscursione() {
		return this.escursione;
	}

	public void setEscursione(Escursione escursione) {
		this.escursione = escursione;
	}

	public Prenotazione_Pacchetto getPrenotazionePacchetto() {
		return this.prenotazionePacchetto;
	}

	public void setPrenotazionePacchetto(Prenotazione_Pacchetto prenotazionePacchetto) {
		this.prenotazionePacchetto = prenotazionePacchetto;
	}

	public Prenotazione_Viaggio getPrenotazioneViaggio() {
		return this.prenotazioneViaggio;
	}

	public void setPrenotazioneViaggio(Prenotazione_Viaggio prenotazioneViaggio) {
		this.prenotazioneViaggio = prenotazioneViaggio;
	}

}