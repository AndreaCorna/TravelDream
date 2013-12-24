package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Amministratore database table.
 * 
 */
@Entity
@NamedQuery(name="Amministratore.findAll", query="SELECT a FROM Amministratore a")
public class Amministratore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Password")
	private String password;

	@Column(name="Username")
	private String username;

	//uni-directional one-to-one association to Anagrafica
	@OneToOne
	@JoinColumn(name="id_Anagrafica")
	private Anagrafica anagrafica;

	//bi-directional many-to-one association to Utente
	@OneToMany(mappedBy="amministratore")
	private List<Utente> dipendentiAggiunti;

	public Amministratore() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Anagrafica getAnagrafica() {
		return this.anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public List<Utente> getDipendentiAggiunti() {
		return this.dipendentiAggiunti;
	}

	public void setDipendentiAggiunti(List<Utente> dipendentiAggiunti) {
		this.dipendentiAggiunti = dipendentiAggiunti;
	}

	public Utente addDipendentiAggiunti(Utente dipendentiAggiunti) {
		getDipendentiAggiunti().add(dipendentiAggiunti);
		dipendentiAggiunti.setAmministratore(this);

		return dipendentiAggiunti;
	}

	public Utente removeDipendentiAggiunti(Utente dipendentiAggiunti) {
		getDipendentiAggiunti().remove(dipendentiAggiunti);
		dipendentiAggiunti.setAmministratore(null);

		return dipendentiAggiunti;
	}

}