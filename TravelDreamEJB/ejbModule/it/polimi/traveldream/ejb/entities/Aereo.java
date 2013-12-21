package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Aereo database table.
 * 
 */
@Entity
@NamedQuery(name="Aereo.findAll", query="SELECT a FROM Aereo a")
public class Aereo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Atterraggio")
	private String atterraggio;

	@Column(name="Costo")
	private int costo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Decollo")
	private String decollo;

	@Column(name="Posti_Disponibili")
	private int posti_Disponibili;

	//bi-directional many-to-many association to Pacchetto
	@ManyToMany(mappedBy="aereos")
	private List<Pacchetto> pacchettos;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@OneToMany(mappedBy="aereo1")
	private List<Prenotazione_Pacchetto> prenotazionePacchettos1;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@OneToMany(mappedBy="aereo2")
	private List<Prenotazione_Pacchetto> prenotazionePacchettos2;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@OneToMany(mappedBy="aereo1")
	private List<Prenotazione_Viaggio> prenotazioneViaggios1;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@OneToMany(mappedBy="aereo2")
	private List<Prenotazione_Viaggio> prenotazioneViaggios2;

	public Aereo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAtterraggio() {
		return this.atterraggio;
	}

	public void setAtterraggio(String atterraggio) {
		this.atterraggio = atterraggio;
	}

	public int getCosto() {
		return this.costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDecollo() {
		return this.decollo;
	}

	public void setDecollo(String decollo) {
		this.decollo = decollo;
	}

	public int getPosti_Disponibili() {
		return this.posti_Disponibili;
	}

	public void setPosti_Disponibili(int posti_Disponibili) {
		this.posti_Disponibili = posti_Disponibili;
	}

	public List<Pacchetto> getPacchettos() {
		return this.pacchettos;
	}

	public void setPacchettos(List<Pacchetto> pacchettos) {
		this.pacchettos = pacchettos;
	}

	public List<Prenotazione_Pacchetto> getPrenotazionePacchettos1() {
		return this.prenotazionePacchettos1;
	}

	public void setPrenotazionePacchettos1(List<Prenotazione_Pacchetto> prenotazionePacchettos1) {
		this.prenotazionePacchettos1 = prenotazionePacchettos1;
	}

	public Prenotazione_Pacchetto addPrenotazionePacchettos1(Prenotazione_Pacchetto prenotazionePacchettos1) {
		getPrenotazionePacchettos1().add(prenotazionePacchettos1);
		prenotazionePacchettos1.setAereo1(this);

		return prenotazionePacchettos1;
	}

	public Prenotazione_Pacchetto removePrenotazionePacchettos1(Prenotazione_Pacchetto prenotazionePacchettos1) {
		getPrenotazionePacchettos1().remove(prenotazionePacchettos1);
		prenotazionePacchettos1.setAereo1(null);

		return prenotazionePacchettos1;
	}

	public List<Prenotazione_Pacchetto> getPrenotazionePacchettos2() {
		return this.prenotazionePacchettos2;
	}

	public void setPrenotazionePacchettos2(List<Prenotazione_Pacchetto> prenotazionePacchettos2) {
		this.prenotazionePacchettos2 = prenotazionePacchettos2;
	}

	public Prenotazione_Pacchetto addPrenotazionePacchettos2(Prenotazione_Pacchetto prenotazionePacchettos2) {
		getPrenotazionePacchettos2().add(prenotazionePacchettos2);
		prenotazionePacchettos2.setAereo2(this);

		return prenotazionePacchettos2;
	}

	public Prenotazione_Pacchetto removePrenotazionePacchettos2(Prenotazione_Pacchetto prenotazionePacchettos2) {
		getPrenotazionePacchettos2().remove(prenotazionePacchettos2);
		prenotazionePacchettos2.setAereo2(null);

		return prenotazionePacchettos2;
	}

	public List<Prenotazione_Viaggio> getPrenotazioneViaggios1() {
		return this.prenotazioneViaggios1;
	}

	public void setPrenotazioneViaggios1(List<Prenotazione_Viaggio> prenotazioneViaggios1) {
		this.prenotazioneViaggios1 = prenotazioneViaggios1;
	}

	public Prenotazione_Viaggio addPrenotazioneViaggios1(Prenotazione_Viaggio prenotazioneViaggios1) {
		getPrenotazioneViaggios1().add(prenotazioneViaggios1);
		prenotazioneViaggios1.setAereo1(this);

		return prenotazioneViaggios1;
	}

	public Prenotazione_Viaggio removePrenotazioneViaggios1(Prenotazione_Viaggio prenotazioneViaggios1) {
		getPrenotazioneViaggios1().remove(prenotazioneViaggios1);
		prenotazioneViaggios1.setAereo1(null);

		return prenotazioneViaggios1;
	}

	public List<Prenotazione_Viaggio> getPrenotazioneViaggios2() {
		return this.prenotazioneViaggios2;
	}

	public void setPrenotazioneViaggios2(List<Prenotazione_Viaggio> prenotazioneViaggios2) {
		this.prenotazioneViaggios2 = prenotazioneViaggios2;
	}

	public Prenotazione_Viaggio addPrenotazioneViaggios2(Prenotazione_Viaggio prenotazioneViaggios2) {
		getPrenotazioneViaggios2().add(prenotazioneViaggios2);
		prenotazioneViaggios2.setAereo2(this);

		return prenotazioneViaggios2;
	}

	public Prenotazione_Viaggio removePrenotazioneViaggios2(Prenotazione_Viaggio prenotazioneViaggios2) {
		getPrenotazioneViaggios2().remove(prenotazioneViaggios2);
		prenotazioneViaggios2.setAereo2(null);

		return prenotazioneViaggios2;
	}

}