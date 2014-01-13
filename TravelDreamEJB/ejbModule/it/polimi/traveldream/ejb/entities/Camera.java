package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.CameraDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the Camera database table.
 * 
 */
@Entity
@Table(name="Camera")
@NamedQuery(name="Camera.findAll", query="SELECT c FROM Camera c")
public class Camera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="Costo")
	private float costo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data_Checkin")
	private Date data_Checkin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data_Checkout")
	private Date data_Checkout;

	@Column(name="Occupata",columnDefinition = "TINYINT(1)")
	private boolean occupata;

	@Column(name="Posti")
	private int posti;

	//bi-directional many-to-one association to Hotel
	@ManyToOne
	@JoinColumn(name="id_Hotel")
	private Hotel hotel;

	public Camera() {
	}
	
	public Camera(CameraDTO camera){
		this.costo = camera.getCosto();
		this.posti = camera.getPosti();
		this.occupata = camera.getOccupata();
		this.data_Checkin = camera.getData_Checkin();
		this.data_Checkout = camera.getData_Checkout();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public Date getData_Checkin() {
		return this.data_Checkin;
	}

	public void setData_Checkin(Date data_Checkin) {
		this.data_Checkin = data_Checkin;
	}

	public Date getData_Checkout() {
		return this.data_Checkout;
	}

	public void setData_Checkout(Date data_Checkout) {
		this.data_Checkout = data_Checkout;
	}

	public boolean getOccupata() {
		return this.occupata;
	}

	public void setOccupata(boolean occupata) {
		this.occupata = occupata;
	}

	public int getPosti() {
		return this.posti;
	}

	public void setPosti(int posti) {
		this.posti = posti;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}