package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Utente database table.
 * 
 */
@Entity
@NamedQuery(name="Utente.findAll", query="SELECT u FROM Utente u")
public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Email")
	private String email;

	@Column(name="Password")
	private String password;

	@Column(name="Telefono")
	private int telefono;

	@Column(name="Username")
	private String username;

	//bi-directional many-to-one association to Condivisione
	@OneToMany(mappedBy="utente")
	private List<Condivisione> condivisiones;

	//bi-directional many-to-one association to Gruppo_Utente
	@OneToMany(mappedBy="utente")
	private List<Gruppo_Utente> gruppoUtentes;

	//bi-directional many-to-one association to Pacchetto
	@OneToMany(mappedBy="utente")
	private List<Pacchetto> pacchettos;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@OneToMany(mappedBy="utente")
	private List<Prenotazione_Pacchetto> prenotazionePacchettos;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@OneToMany(mappedBy="utente")
	private List<Prenotazione_Viaggio> prenotazioneViaggios;

	//bi-directional many-to-one association to Amministratore
	@ManyToOne
	@JoinColumn(name="id_Amministratore")
	private Amministratore amministratore;

	//bi-directional many-to-one association to Anagrafica
	@ManyToOne
	@JoinColumn(name="id_Anagrafica")
	private Anagrafica anagrafica;

	public Utente() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Condivisione> getCondivisiones() {
		return this.condivisiones;
	}

	public void setCondivisiones(List<Condivisione> condivisiones) {
		this.condivisiones = condivisiones;
	}

	public Condivisione addCondivisione(Condivisione condivisione) {
		getCondivisiones().add(condivisione);
		condivisione.setUtente(this);

		return condivisione;
	}

	public Condivisione removeCondivisione(Condivisione condivisione) {
		getCondivisiones().remove(condivisione);
		condivisione.setUtente(null);

		return condivisione;
	}

	public List<Gruppo_Utente> getGruppoUtentes() {
		return this.gruppoUtentes;
	}

	public void setGruppoUtentes(List<Gruppo_Utente> gruppoUtentes) {
		this.gruppoUtentes = gruppoUtentes;
	}

	public Gruppo_Utente addGruppoUtente(Gruppo_Utente gruppoUtente) {
		getGruppoUtentes().add(gruppoUtente);
		gruppoUtente.setUtente(this);

		return gruppoUtente;
	}

	public Gruppo_Utente removeGruppoUtente(Gruppo_Utente gruppoUtente) {
		getGruppoUtentes().remove(gruppoUtente);
		gruppoUtente.setUtente(null);

		return gruppoUtente;
	}

	public List<Pacchetto> getPacchettos() {
		return this.pacchettos;
	}

	public void setPacchettos(List<Pacchetto> pacchettos) {
		this.pacchettos = pacchettos;
	}

	public Pacchetto addPacchetto(Pacchetto pacchetto) {
		getPacchettos().add(pacchetto);
		pacchetto.setUtente(this);

		return pacchetto;
	}

	public Pacchetto removePacchetto(Pacchetto pacchetto) {
		getPacchettos().remove(pacchetto);
		pacchetto.setUtente(null);

		return pacchetto;
	}

	public List<Prenotazione_Pacchetto> getPrenotazionePacchettos() {
		return this.prenotazionePacchettos;
	}

	public void setPrenotazionePacchettos(List<Prenotazione_Pacchetto> prenotazionePacchettos) {
		this.prenotazionePacchettos = prenotazionePacchettos;
	}

	public Prenotazione_Pacchetto addPrenotazionePacchetto(Prenotazione_Pacchetto prenotazionePacchetto) {
		getPrenotazionePacchettos().add(prenotazionePacchetto);
		prenotazionePacchetto.setUtente(this);

		return prenotazionePacchetto;
	}

	public Prenotazione_Pacchetto removePrenotazionePacchetto(Prenotazione_Pacchetto prenotazionePacchetto) {
		getPrenotazionePacchettos().remove(prenotazionePacchetto);
		prenotazionePacchetto.setUtente(null);

		return prenotazionePacchetto;
	}

	public List<Prenotazione_Viaggio> getPrenotazioneViaggios() {
		return this.prenotazioneViaggios;
	}

	public void setPrenotazioneViaggios(List<Prenotazione_Viaggio> prenotazioneViaggios) {
		this.prenotazioneViaggios = prenotazioneViaggios;
	}

	public Prenotazione_Viaggio addPrenotazioneViaggio(Prenotazione_Viaggio prenotazioneViaggio) {
		getPrenotazioneViaggios().add(prenotazioneViaggio);
		prenotazioneViaggio.setUtente(this);

		return prenotazioneViaggio;
	}

	public Prenotazione_Viaggio removePrenotazioneViaggio(Prenotazione_Viaggio prenotazioneViaggio) {
		getPrenotazioneViaggios().remove(prenotazioneViaggio);
		prenotazioneViaggio.setUtente(null);

		return prenotazioneViaggio;
	}

	public Amministratore getAmministratore() {
		return this.amministratore;
	}

	public void setAmministratore(Amministratore amministratore) {
		this.amministratore = amministratore;
	}

	public Anagrafica getAnagrafica() {
		return this.anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

}