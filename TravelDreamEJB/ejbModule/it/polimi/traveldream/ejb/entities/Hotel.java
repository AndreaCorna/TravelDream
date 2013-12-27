package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.HotelDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the Hotel database table.
 * 
 */
@Entity
@Table(name="Hotel")
@NamedQuery(name="Hotel.findAll", query="SELECT h FROM Hotel h")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="Camere_Disponibili")
	private int camere_Disponibili;

	@Column(name="Città")
	private String città;

	//bi-directional many-to-one association to Camera
	@OneToMany(mappedBy="hotel")
	private List<Camera> camere;

	public Hotel() {
	}

	public Hotel(HotelDTO hotel) {
		this.id = hotel.getId();
		this.camere_Disponibili = hotel.getCamereDisponibili();
		this.città = hotel.getCitta();
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

	public List<Camera> getCamere() {
		return this.camere;
	}

	public void setCamere(List<Camera> camere) {
		this.camere = camere;
	}

	public Camera addCamere(Camera camere) {
		getCamere().add(camere);
		camere.setHotel(this);

		return camere;
	}

	public Camera removeCamere(Camera camere) {
		getCamere().remove(camere);
		camere.setHotel(null);

		return camere;
	}

}