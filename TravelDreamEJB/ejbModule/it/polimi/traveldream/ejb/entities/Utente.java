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
	private List<Condivisione> condivisioni;

	//uni-directional many-to-many association to Gruppo
	@ManyToMany
	@JoinTable(
		name="Gruppo_Utente"
		, joinColumns={
			@JoinColumn(name="id_Gruppo")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_Gruppo")
			}
		)
	private List<Gruppo> gruppi;

	//bi-directional many-to-one association to Pacchetto
	@OneToMany(mappedBy="utente")
	private List<Pacchetto> pacchettiCreati;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@OneToMany(mappedBy="utente")
	private List<Prenotazione_Pacchetto> prenotazioniPacchetto;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@OneToMany(mappedBy="utente")
	private List<Prenotazione_Viaggio> prenotazioniViaggio;

	//bi-directional many-to-one association to Amministratore
	@ManyToOne
	@JoinColumn(name="id_Amministratore")
	private Amministratore amministratore;

	//uni-directional one-to-one association to Anagrafica
	@OneToOne
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

	public List<Condivisione> getCondivisioni() {
		return this.condivisioni;
	}

	public void setCondivisioni(List<Condivisione> condivisioni) {
		this.condivisioni = condivisioni;
	}

	public Condivisione addCondivisioni(Condivisione condivisioni) {
		getCondivisioni().add(condivisioni);
		condivisioni.setUtente(this);

		return condivisioni;
	}

	public Condivisione removeCondivisioni(Condivisione condivisioni) {
		getCondivisioni().remove(condivisioni);
		condivisioni.setUtente(null);

		return condivisioni;
	}

	public List<Gruppo> getGruppi() {
		return this.gruppi;
	}

	public void setGruppi(List<Gruppo> gruppi) {
		this.gruppi = gruppi;
	}

	public List<Pacchetto> getPacchettiCreati() {
		return this.pacchettiCreati;
	}

	public void setPacchettiCreati(List<Pacchetto> pacchettiCreati) {
		this.pacchettiCreati = pacchettiCreati;
	}

	public Pacchetto addPacchettiCreati(Pacchetto pacchettiCreati) {
		getPacchettiCreati().add(pacchettiCreati);
		pacchettiCreati.setUtente(this);

		return pacchettiCreati;
	}

	public Pacchetto removePacchettiCreati(Pacchetto pacchettiCreati) {
		getPacchettiCreati().remove(pacchettiCreati);
		pacchettiCreati.setUtente(null);

		return pacchettiCreati;
	}

	public List<Prenotazione_Pacchetto> getPrenotazioniPacchetto() {
		return this.prenotazioniPacchetto;
	}

	public void setPrenotazioniPacchetto(List<Prenotazione_Pacchetto> prenotazioniPacchetto) {
		this.prenotazioniPacchetto = prenotazioniPacchetto;
	}

	public Prenotazione_Pacchetto addPrenotazioniPacchetto(Prenotazione_Pacchetto prenotazioniPacchetto) {
		getPrenotazioniPacchetto().add(prenotazioniPacchetto);
		prenotazioniPacchetto.setUtente(this);

		return prenotazioniPacchetto;
	}

	public Prenotazione_Pacchetto removePrenotazioniPacchetto(Prenotazione_Pacchetto prenotazioniPacchetto) {
		getPrenotazioniPacchetto().remove(prenotazioniPacchetto);
		prenotazioniPacchetto.setUtente(null);

		return prenotazioniPacchetto;
	}

	public List<Prenotazione_Viaggio> getPrenotazioniViaggio() {
		return this.prenotazioniViaggio;
	}

	public void setPrenotazioniViaggio(List<Prenotazione_Viaggio> prenotazioniViaggio) {
		this.prenotazioniViaggio = prenotazioniViaggio;
	}

	public Prenotazione_Viaggio addPrenotazioniViaggio(Prenotazione_Viaggio prenotazioniViaggio) {
		getPrenotazioniViaggio().add(prenotazioniViaggio);
		prenotazioniViaggio.setUtente(this);

		return prenotazioniViaggio;
	}

	public Prenotazione_Viaggio removePrenotazioniViaggio(Prenotazione_Viaggio prenotazioniViaggio) {
		getPrenotazioniViaggio().remove(prenotazioniViaggio);
		prenotazioniViaggio.setUtente(null);

		return prenotazioniViaggio;
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