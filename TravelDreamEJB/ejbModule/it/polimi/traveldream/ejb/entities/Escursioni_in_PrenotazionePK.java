package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Escursioni_in_Prenotazione database table.
 * 
 */
@Embeddable
public class Escursioni_in_PrenotazionePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int id_Prenotazione;

	@Column(insertable=false, updatable=false)
	private int id_Escursione;

	public Escursioni_in_PrenotazionePK() {
	}
	public int getId_Prenotazione() {
		return this.id_Prenotazione;
	}
	public void setId_Prenotazione(int id_Prenotazione) {
		this.id_Prenotazione = id_Prenotazione;
	}
	public int getId_Escursione() {
		return this.id_Escursione;
	}
	public void setId_Escursione(int id_Escursione) {
		this.id_Escursione = id_Escursione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Escursioni_in_PrenotazionePK)) {
			return false;
		}
		Escursioni_in_PrenotazionePK castOther = (Escursioni_in_PrenotazionePK)other;
		return 
			(this.id_Prenotazione == castOther.id_Prenotazione)
			&& (this.id_Escursione == castOther.id_Escursione);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id_Prenotazione;
		hash = hash * prime + this.id_Escursione;
		
		return hash;
	}
}