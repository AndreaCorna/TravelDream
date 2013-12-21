package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Gruppo_Utente database table.
 * 
 */
@Entity
@NamedQuery(name="Gruppo_Utente.findAll", query="SELECT g FROM Gruppo_Utente g")
public class Gruppo_Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Gruppo_UtentePK id;

	//bi-directional many-to-one association to Amministratore
	@ManyToOne
	@JoinColumn(name="id_Utente")
	private Amministratore amministratore;

	//bi-directional many-to-one association to Gruppo
	@ManyToOne
	@JoinColumn(name="id_Gruppo")
	private Gruppo gruppo;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Utente")
	private Utente utente;

	public Gruppo_Utente() {
	}

	public Gruppo_UtentePK getId() {
		return this.id;
	}

	public void setId(Gruppo_UtentePK id) {
		this.id = id;
	}

	public Amministratore getAmministratore() {
		return this.amministratore;
	}

	public void setAmministratore(Amministratore amministratore) {
		this.amministratore = amministratore;
	}

	public Gruppo getGruppo() {
		return this.gruppo;
	}

	public void setGruppo(Gruppo gruppo) {
		this.gruppo = gruppo;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}