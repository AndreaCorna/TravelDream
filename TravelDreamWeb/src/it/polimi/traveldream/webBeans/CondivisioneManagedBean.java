package it.polimi.traveldream.webBeans;

import java.util.Date;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.sessionBeans.CondivisioneBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;




@ManagedBean(name="condivisione")
@SessionScoped
public class CondivisioneManagedBean {
	
	@EJB
	private CondivisioneBean gestoreCondivisione;
	
	private UtenteDTO utente;
	
	private CondivisioneDTO condivisione;
	
	private String link;
	
	private Date data;
	
	private String idUtente;
	
	private int idPrenotazione;
	
	
	public CondivisioneManagedBean(){
		setUtente(new UtenteDTO());
		setCondivisione(new CondivisioneDTO());
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public CondivisioneDTO getCondivisione() {
		return condivisione;
	}

	public void setCondivisione(CondivisioneDTO condivisione) {
		this.condivisione = condivisione;
	}
	
	@SuppressWarnings("deprecation")
	public String visualizzaCondivisione(){
		/*condivisione = gestoreCondivisione.mostraCondivisione(link);
		this.data = condivisione.getData();
		this.utente = condivisione.getUtente();
		this.link = condivisione.getLink();
		*/
		this.data = new Date(2013, 12, 5);
		this.idUtente = "hummer";
		this.idPrenotazione = 1;
	
		return "/condiv?link="+this.link+"&faces-redirect=true";
		
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(int idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

}
