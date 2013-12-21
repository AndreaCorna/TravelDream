package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Amministratore database table.
 * 
 */
@Entity
@NamedQuery(name="Amministratore.findAll", query="SELECT a FROM Amministratore a")
public class Amministratore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Password")
	private String password;

	@Column(name="Username")
	private String username;

	//bi-directional many-to-one association to Anagrafica
	@ManyToOne
	@JoinColumn(name="id_Anagrafica")
	private Anagrafica anagrafica;

	//bi-directional many-to-one association to Gruppo_Utente
	@OneToMany(mappedBy="amministratore")
	private List<Gruppo_Utente> gruppoUtentes;

	//bi-directional many-to-one association to Utente
	@OneToMany(mappedBy="amministratore")
	private List<Utente> utentes;

	public Amministratore() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Anagrafica getAnagrafica() {
		return this.anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public List<Gruppo_Utente> getGruppoUtentes() {
		return this.gruppoUtentes;
	}

	public void setGruppoUtentes(List<Gruppo_Utente> gruppoUtentes) {
		this.gruppoUtentes = gruppoUtentes;
	}

	public Gruppo_Utente addGruppoUtente(Gruppo_Utente gruppoUtente) {
		getGruppoUtentes().add(gruppoUtente);
		gruppoUtente.setAmministratore(this);

		return gruppoUtente;
	}

	public Gruppo_Utente removeGruppoUtente(Gruppo_Utente gruppoUtente) {
		getGruppoUtentes().remove(gruppoUtente);
		gruppoUtente.setAmministratore(null);

		return gruppoUtente;
	}

	public List<Utente> getUtentes() {
		return this.utentes;
	}

	public void setUtentes(List<Utente> utentes) {
		this.utentes = utentes;
	}

	public Utente addUtente(Utente utente) {
		getUtentes().add(utente);
		utente.setAmministratore(this);

		return utente;
	}

	public Utente removeUtente(Utente utente) {
		getUtentes().remove(utente);
		utente.setAmministratore(null);

		return utente;
	}

}