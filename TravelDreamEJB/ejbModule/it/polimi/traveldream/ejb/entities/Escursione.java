package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Escursione database table.
 * 
 */
@Entity
@NamedQuery(name="Escursione.findAll", query="SELECT e FROM Escursione e")
public class Escursione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Luogo")
	private String luogo;

	@Column(name="Prezzo")
	private int prezzo;

	//bi-directional many-to-many association to Pacchetto
	@ManyToMany(mappedBy="escursioni")
	private List<Pacchetto> pacchetti;

	public Escursione() {
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

	public int getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public List<Pacchetto> getPacchetti() {
		return this.pacchetti;
	}

	public void setPacchetti(List<Pacchetto> pacchetti) {
		this.pacchetti = pacchetti;
	}

}