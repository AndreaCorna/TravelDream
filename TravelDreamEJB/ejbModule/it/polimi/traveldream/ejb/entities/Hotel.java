package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Hotel database table.
 * 
 */
@Entity
@NamedQuery(name="Hotel.findAll", query="SELECT h FROM Hotel h")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Camere_Disponibili")
	private int camere_Disponibili;

	@Column(name="Città")
	private String città;

	//bi-directional many-to-one association to Camera
	@OneToMany(mappedBy="hotel")
	private List<Camera> cameras;

	//bi-directional many-to-many association to Pacchetto
	@ManyToMany(mappedBy="hotels")
	private List<Pacchetto> pacchettos;

	//bi-directional many-to-one association to Prenotazione_Pacchetto
	@OneToMany(mappedBy="hotel")
	private List<Prenotazione_Pacchetto> prenotazionePacchettos;

	//bi-directional many-to-one association to Prenotazione_Viaggio
	@OneToMany(mappedBy="hotel")
	private List<Prenotazione_Viaggio> prenotazioneViaggios;

	public Hotel() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCamere_Disponibili() {
		return this.camere_Disponibili;
	}

	public void setCamere_Disponibili(int camere_Disponibili) {
		this.camere_Disponibili = camere_Disponibili;
	}

	public String getCittà() {
		return this.città;
	}

	public void setCittà(String città) {
		this.città = città;
	}

	public List<Camera> getCameras() {
		return this.cameras;
	}

	public void setCameras(List<Camera> cameras) {
		this.cameras = cameras;
	}

	public Camera addCamera(Camera camera) {
		getCameras().add(camera);
		camera.setHotel(this);

		return camera;
	}

	public Camera removeCamera(Camera camera) {
		getCameras().remove(camera);
		camera.setHotel(null);

		return camera;
	}

	public List<Pacchetto> getPacchettos() {
		return this.pacchettos;
	}

	public void setPacchettos(List<Pacchetto> pacchettos) {
		this.pacchettos = pacchettos;
	}

	public List<Prenotazione_Pacchetto> getPrenotazionePacchettos() {
		return this.prenotazionePacchettos;
	}

	public void setPrenotazionePacchettos(List<Prenotazione_Pacchetto> prenotazionePacchettos) {
		this.prenotazionePacchettos = prenotazionePacchettos;
	}

	public Prenotazione_Pacchetto addPrenotazionePacchetto(Prenotazione_Pacchetto prenotazionePacchetto) {
		getPrenotazionePacchettos().add(prenotazionePacchetto);
		prenotazionePacchetto.setHotel(this);

		return prenotazionePacchetto;
	}

	public Prenotazione_Pacchetto removePrenotazionePacchetto(Prenotazione_Pacchetto prenotazionePacchetto) {
		getPrenotazionePacchettos().remove(prenotazionePacchetto);
		prenotazionePacchetto.setHotel(null);

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
		prenotazioneViaggio.setHotel(this);

		return prenotazioneViaggio;
	}

	public Prenotazione_Viaggio removePrenotazioneViaggio(Prenotazione_Viaggio prenotazioneViaggio) {
		getPrenotazioneViaggios().remove(prenotazioneViaggio);
		prenotazioneViaggio.setHotel(null);

		return prenotazioneViaggio;
	}

}