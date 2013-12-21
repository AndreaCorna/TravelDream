package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Gruppo_Utente database table.
 * 
 */
@Embeddable
public class Gruppo_UtentePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int id_Utente;

	@Column(insertable=false, updatable=false)
	private String id_Gruppo;

	public Gruppo_UtentePK() {
	}
	public int getId_Utente() {
		return this.id_Utente;
	}
	public void setId_Utente(int id_Utente) {
		this.id_Utente = id_Utente;
	}
	public String getId_Gruppo() {
		return this.id_Gruppo;
	}
	public void setId_Gruppo(String id_Gruppo) {
		this.id_Gruppo = id_Gruppo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Gruppo_UtentePK)) {
			return false;
		}
		Gruppo_UtentePK castOther = (Gruppo_UtentePK)other;
		return 
			(this.id_Utente == castOther.id_Utente)
			&& this.id_Gruppo.equals(castOther.id_Gruppo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id_Utente;
		hash = hash * prime + this.id_Gruppo.hashCode();
		
		return hash;
	}
}