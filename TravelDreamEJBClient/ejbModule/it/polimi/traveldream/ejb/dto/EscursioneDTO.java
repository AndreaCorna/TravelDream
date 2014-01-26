package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * Classe per creare oggetti EscursioneDTO necessari per il passaggio di informazioni tra la business logic e il client tier
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
public class EscursioneDTO {
	
	
	private int id;
	@NotEmpty
	private String luogo;
	@NotNull
	@Min(0)
	private float prezzo;
	@NotNull
	private Date data;
	@NotEmpty
	private String descrizione;
	
	private byte valido;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
