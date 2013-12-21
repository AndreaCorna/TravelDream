package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Gruppo database table.
 * 
 */
@Entity
@NamedQuery(name="Gruppo.findAll", query="SELECT g FROM Gruppo g")
public class Gruppo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String nome;

	//bi-directional many-to-one association to Gruppo_Utente
	@OneToMany(mappedBy="gruppo")
	private List<Gruppo_Utente> gruppoUtentes;

	public Gruppo() {
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Gruppo_Utente> getGruppoUtentes() {
		return this.gruppoUtentes;
	}

	public void setGruppoUtentes(List<Gruppo_Utente> gruppoUtentes) {
		this.gruppoUtentes = gruppoUtentes;
	}

	public Gruppo_Utente addGruppoUtente(Gruppo_Utente gruppoUtente) {
		getGruppoUtentes().add(gruppoUtente);
		gruppoUtente.setGruppo(this);

		return gruppoUtente;
	}

	public Gruppo_Utente removeGruppoUtente(Gruppo_Utente gruppoUtente) {
		getGruppoUtentes().remove(gruppoUtente);
		gruppoUtente.setGruppo(null);

		return gruppoUtente;
	}

}