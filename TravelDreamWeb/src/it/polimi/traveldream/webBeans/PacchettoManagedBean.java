package it.polimi.traveldream.webBeans;

import java.util.List;

import it.polimi.traveldream.dataModels.AereoDataModel;
import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean(name="pacchetto")
@RequestScoped
public class PacchettoManagedBean {

	@EJB
	private GestionePacchettoBean gestionePacchetto;
	
	private List<AereoDTO> listaAereiAndata;
	
	private List<AereoDTO> listaAereiRitorno;
	
	private List<AereoDTO> listaAereiDB;
	
	private AereoDataModel datiAerei;
	
	private PacchettoDTO pacchetto;
	
	private String destinazione;
	
	@PostConstruct
	public void init(){
		listaAereiDB = gestionePacchetto.getListaAerei();
		setDatiAerei(new AereoDataModel(listaAereiDB));
	}
	
	public List<AereoDTO> getListaAereiAndata() {
		return listaAereiAndata;
	}

	public void setListaAereiAndata(List<AereoDTO> listaAereiAndata) {
		this.listaAereiAndata = listaAereiAndata;
	}

	public List<AereoDTO> getListaAereiRitorno() {
		return listaAereiRitorno;
	}

	public void setListaAereiRitorno(List<AereoDTO> listaAereiRitorno) {
		this.listaAereiRitorno = listaAereiRitorno;
	}

	public List<AereoDTO> getListaAereiDB() {
		return listaAereiDB;
	}

	public void setListaAereiDB(List<AereoDTO> listaAereiDB) {
		this.listaAereiDB = listaAereiDB;
	}

	public AereoDataModel getDatiAerei() {
		return datiAerei;
	}

	public void setDatiAerei(AereoDataModel datiAerei) {
		this.datiAerei = datiAerei;
	}
	
	public String aggiungiAerei(){
		pacchetto = new PacchettoDTO();
		if( verificaAtterraggioAerei() && verificaDecolloAerei() ){
			
			return "/insertHotel?faces-redirect=true";
		}
		return "/index?faces-redirect=true";
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	
	private boolean verificaAtterraggioAerei(){
		for(AereoDTO aereo:listaAereiAndata){
			if(!aereo.getCittaAtterraggio().toLowerCase().equals(destinazione.toLowerCase())){
				return false;
			}
		}
		return true;
	}
	
	private boolean verificaDecolloAerei(){
		for(AereoDTO aereo:listaAereiRitorno){
			if(!aereo.getCittaDecollo().toLowerCase().equals(destinazione.toLowerCase())){
				return false;
			}
		}
		return true;
	}
	
	
}
