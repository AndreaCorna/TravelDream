package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Camera database table.
 * 
 */
@Entity
@NamedQuery(name="Camera.findAll", query="SELECT c FROM Camera c")
public class Camera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Costo")
	private int costo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data_Checkin")
	private Date data_Checkin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data_Checkout")
	private Date data_Checkout;

	@Column(name="Occupata")
	private byte occupata;

	@Column(name="Posti")
	private int posti;

	//bi-directional many-to-one association to Hotel
	@ManyToOne
	@JoinColumn(name="id_Hotel")
	private Hotel hotel;

	public Camera() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCosto() {
		return this.costo;
	}

	public void setCosto(int costo) {
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

	public byte getOccupata() {
		return this.occupata;
	}

	public void setOccupata(byte occupata) {
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