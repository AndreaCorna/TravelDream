package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.EscursioneDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * Classe rappresentante l'entity Escursione presente nel database.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Entity
@Table(name="Escursione")
@NamedQueries({
	@NamedQuery(name="Escursione.findAll", query="SELECT e FROM Escursione e"),
	@NamedQuery(name="Escursione.findValidi", query="SELECT e FROM Escursione e WHERE e.valido=1")
})

public class Escursione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Luogo")
	private String luogo;

	@Column(name="Prezzo")
	private float prezzo;
	
	@Column(name="Descrizione")
	private String descrizione;
	
	@Column(name="Valido")
	private byte valido;

	public Escursione() {
	}

	public Escursione(EscursioneDTO escursione) {
		this.id = escursione.getId();
		this.luogo = escursione.getLuogo();
		this.data = escursione.getData();
		this.prezzo = escursione.getPrezzo();
		this.setDescrizione(escursione.getDescrizione());
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLuogo() {
		return this.luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public float getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public byte getValido() {
		return valido;
	}

	public void setValido(byte valido) {
		this.valido = valido;
	}

}