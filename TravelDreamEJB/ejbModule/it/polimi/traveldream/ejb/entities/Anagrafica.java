package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Anagrafica database table.
 * 
 */
@Entity
@NamedQuery(name="Anagrafica.findAll", query="SELECT a FROM Anagrafica a")
public class Anagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CF")
	private String cf;

	@Column(name="Cognome")
	private String cognome;

	@Temporal(TemporalType.DATE)
	@Column(name="Data_Nascita")
	private Date data_Nascita;

	@Column(name="Luogo_Nascita")
	private String luogo_Nascita;

	@Column(name="Nome")
	private String nome;

	@Column(name="Residenza")
	private String residenza;

	//bi-directional many-to-one association to Amministratore
	@OneToMany(mappedBy="anagrafica")
	private List<Amministratore> amministratores;

	//bi-directional many-to-one association to Utente
	@OneToMany(mappedBy="anagrafica")
	private List<Utente> utentes;

	public Anagrafica() {
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getData_Nascita() {
		return this.data_Nascita;
	}

	public void setData_Nascita(Date data_Nascita) {
		this.data_Nascita = data_Nascita;
	}

	public String getLuogo_Nascita() {
		return this.luogo_Nascita;
	}

	public void setLuogo_Nascita(String luogo_Nascita) {
		this.luogo_Nascita = luogo_Nascita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResidenza() {
		return this.residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public List<Amministratore> getAmministratores() {
		return this.amministratores;
	}

	public void setAmministratores(List<Amministratore> amministratores) {
		this.amministratores = amministratores;
	}

	public Amministratore addAmministratore(Amministratore amministratore) {
		getAmministratores().add(amministratore);
		amministratore.setAnagrafica(this);

		return amministratore;
	}

	public Amministratore removeAmministratore(Amministratore amministratore) {
		getAmministratores().remove(amministratore);
		amministratore.setAnagrafica(null);

		return amministratore;
	}

	public List<Utente> getUtentes() {
		return this.utentes;
	}

	public void setUtentes(List<Utente> utentes) {
		this.utentes = utentes;
	}

	public Utente addUtente(Utente utente) {
		getUtentes().add(utente);
		utente.setAnagrafica(this);

		return utente;
	}

	public Utente removeUtente(Utente utente) {
		getUtentes().remove(utente);
		utente.setAnagrafica(null);

		return utente;
	}

}