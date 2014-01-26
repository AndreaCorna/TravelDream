package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.HotelDTO;

import java.io.Serializable;

import javax.persistence.*;





/**
 * Classe rappresentante l'entity Hotel presente nel database.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Entity
@Table(name="Hotel")
@NamedQueries({
	@NamedQuery(name="Hotel.findAll", query="SELECT h FROM Hotel h") ,
	@NamedQuery(name="Hotel.findValidi", query="SELECT h FROM Hotel h WHERE h.valido=1")
})

public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="Camere_Disponibili")
	private int camere_Disponibili;

	@Column(name="Citta")
	private String citta;
	
	@Column(name="Nome")
	private String nome;
	
	@Column(name="Stelle")
	private int stelle;
	
	@Column(name="Costo_Giornaliero")
	private float costoGiornaliero;
	
	@Column(name="Valido")
	private byte valido;
	
	public Hotel() {
	}

	public Hotel(HotelDTO hotel) {
		this.id = hotel.getId();
		this.camere_Disponibili = hotel.getCamereDisponibili();
		this.citta= hotel.getCitta();
		this.nome = hotel.getNome();
		this.stelle = hotel.getRating().intValue();
		this.costoGiornaliero = hotel.getCostoGiornaliero();
		
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

	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta= citta;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getStelle() {
		return stelle;
	}

	public void setStelle(int stelle) {
		this.stelle = stelle;
	}

	public float getCostoGiornaliero() {
		return costoGiornaliero;
	}

	public void setCostoGiornaliero(float costoGiornaliero) {
		this.costoGiornaliero = costoGiornaliero;
	}

	
	public byte getValido() {
		return valido;
	}

	public void setValido(byte valido) {
		this.valido = valido;
	}

}
