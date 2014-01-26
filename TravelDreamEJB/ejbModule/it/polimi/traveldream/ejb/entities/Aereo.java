package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.AereoDTO;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * Classe rappresentante l'entity Aereo presente nel database.
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@Entity
@Table(name="Aereo")
@NamedQueries({
	@NamedQuery(name="Aereo.findAll", query="SELECT a FROM Aereo a") ,
	@NamedQuery(name="Aereo.findValidi", query="SELECT a FROM Aereo a WHERE a.valido=1")
})
public class Aereo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="Atterraggio")
	private String atterraggio;

	@Column(name="Costo")
	private float costo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Data")
	private Date data;

	@Column(name="Decollo")
	private String decollo;

	@Column(name="Posti_Disponibili")
	private int posti_Disponibili;
	
	@Column(name="Valido")
	private byte valido;

	public Aereo() {
	}
	
	public Aereo(AereoDTO aereo){
		this.atterraggio = aereo.getCittaAtterraggio();
		this.decollo = aereo.getCittaDecollo();
		this.costo = aereo.getCosto();
		this.data = aereo.getData();
		this.posti_Disponibili = aereo.getPostiDisponibili();
		this.valido = 1;
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

	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
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

	public byte getValido() {
		return valido;
	}

	public void setValido(byte valido) {
		this.valido = valido;
	}

}