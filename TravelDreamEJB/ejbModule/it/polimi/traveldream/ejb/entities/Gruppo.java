package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the Gruppo database table.
 * 
 */
@Entity
@Table(name="Gruppo")
@NamedQuery(name="Gruppo.findAll", query="SELECT g FROM Gruppo g")
public class Gruppo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String nome;

	public Gruppo() {
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}