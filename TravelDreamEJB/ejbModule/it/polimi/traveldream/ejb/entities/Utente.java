package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;


/**
 * The persistent class for the Utente database table.
 * 
 */
@Entity
@Table(name="Utente")
@NamedQuery(name="Utente.findAll", query="SELECT u FROM Utente u")
public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	
	@Column(name="Username")
	private String username;

	@Column(name="Email")
	private String email;

	@Column(name="Password")
	private String password;

	@Column(name="Telefono")
	private String telefono;

	//bi-directional many-to-one association to Condivisione
	@OneToMany(mappedBy="utente")
	private List<Condivisione> condivisioni;

	//uni-directional many-to-many association to Gruppo
	@ManyToMany
	@JoinTable(
		name="Gruppo_Utente"
		, joinColumns={
			@JoinColumn(name="id_Utente")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_Gruppo")
			}
		)
	private List<Gruppo> gruppi;

	//bi-directional many-to-one association to Pacchetto
	@OneToMany(mappedBy="dipendente")
	private List<Pacchetto> pacchettiCreati;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@OneToMany(mappedBy="utente")
	private List<Prenotazione_Pacchetto> prenotazioniPacchetti;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@OneToMany(mappedBy="utente")
	private List<Prenotazione_Viaggio> prenotazioniViaggi;

	//uni-directional many-to-one association to Anagrafica
	@ManyToOne
	@JoinColumn(name="id_Anagrafica")
	private Anagrafica anagrafica;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_Amministratore")
	private Utente amministratoreCreatore;

	//bi-directional many-to-one association to Utente
	@OneToMany(mappedBy="amministratoreCreatore")
	private List<Utente> dipendentiAggiunti;

	public Utente() {
	}

	public Utente(UtenteDTO utente, Anagrafica anagrafica){
		this.email = utente.getEmail();
		this.username = utente.getUsername();
		this.telefono = utente.getTelefono();
		this.password = DigestUtils.sha256Hex(utente.getPassword());
		this.anagrafica = anagrafica;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
		pacchettiCreati.setDipendente(this);

		return pacchettiCreati;
	}

	public Pacchetto removePacchettiCreati(Pacchetto pacchettiCreati) {
		getPacchettiCreati().remove(pacchettiCreati);
		pacchettiCreati.setDipendente(null);

		return pacchettiCreati;
	}

	public List<Prenotazione_Pacchetto> getPrenotazioniPacchetti() {
		return this.prenotazioniPacchetti;
	}

	public void setPrenotazioniPacchetti(List<Prenotazione_Pacchetto> prenotazioniPacchetti) {
		this.prenotazioniPacchetti = prenotazioniPacchetti;
	}

	public Prenotazione_Pacchetto addPrenotazioniPacchetti(Prenotazione_Pacchetto prenotazioniPacchetti) {
		getPrenotazioniPacchetti().add(prenotazioniPacchetti);
		prenotazioniPacchetti.setUtente(this);

		return prenotazioniPacchetti;
	}

	public Prenotazione_Pacchetto removePrenotazioniPacchetti(Prenotazione_Pacchetto prenotazioniPacchetti) {
		getPrenotazioniPacchetti().remove(prenotazioniPacchetti);
		prenotazioniPacchetti.setUtente(null);

		return prenotazioniPacchetti;
	}

	public List<Prenotazione_Viaggio> getPrenotazioniViaggi() {
		return this.prenotazioniViaggi;
	}

	public void setPrenotazioniViaggi(List<Prenotazione_Viaggio> prenotazioniViaggi) {
		this.prenotazioniViaggi = prenotazioniViaggi;
	}

	public Prenotazione_Viaggio addPrenotazioniViaggi(Prenotazione_Viaggio prenotazioniViaggi) {
		getPrenotazioniViaggi().add(prenotazioniViaggi);
		prenotazioniViaggi.setUtente(this);

		return prenotazioniViaggi;
	}

	public Prenotazione_Viaggio removePrenotazioniViaggi(Prenotazione_Viaggio prenotazioniViaggi) {
		getPrenotazioniViaggi().remove(prenotazioniViaggi);
		prenotazioniViaggi.setUtente(null);

		return prenotazioniViaggi;
	}

	public Anagrafica getAnagrafica() {
		return this.anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public Utente getAmministratoreCreatore() {
		return this.amministratoreCreatore;
	}

	public void setAmministratoreCreatore(Utente amministratoreCreatore) {
		this.amministratoreCreatore = amministratoreCreatore;
	}

	public List<Utente> getDipendentiAggiunti() {
		return this.dipendentiAggiunti;
	}

	public void setDipendentiAggiunti(List<Utente> dipendentiAggiunti) {
		this.dipendentiAggiunti = dipendentiAggiunti;
	}

	public Utente addDipendentiAggiunti(Utente dipendentiAggiunti) {
		getDipendentiAggiunti().add(dipendentiAggiunti);
		dipendentiAggiunti.setAmministratoreCreatore(this);

		return dipendentiAggiunti;
	}

	public Utente removeDipendentiAggiunti(Utente dipendentiAggiunti) {
		getDipendentiAggiunti().remove(dipendentiAggiunti);
		dipendentiAggiunti.setAmministratoreCreatore(null);

		return dipendentiAggiunti;
	}

}