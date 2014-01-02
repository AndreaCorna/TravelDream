package it.polimi.traveldream.ejb.dto;

import java.util.Date;


public class CameraDTO {

	private int id;

	private float costo;

	private Date data_Checkin;

	private Date data_Checkout;

	private byte occupata;

	private int posti;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public Date getData_Checkin() {
		return data_Checkin;
	}

	public void setData_Checkin(Date data_Checkin) {
		this.data_Checkin = data_Checkin;
	}

	public Date getData_Checkout() {
		return data_Checkout;
	}

	public void setData_Checkout(Date data_Checkout) {
		this.data_Checkout = data_Checkout;
	}

	public byte getOccupata() {
		return occupata;
	}

	public void setOccupata(byte occupata) {
		this.occupata = occupata;
	}

	public int getPosti() {
		return posti;
	}

	public void setPosti(int posti) {
		this.posti = posti;
	}
}
