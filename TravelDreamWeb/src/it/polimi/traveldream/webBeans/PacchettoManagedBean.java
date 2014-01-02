package it.polimi.traveldream.webBeans;

import java.util.List;

import it.polimi.traveldream.dataModels.AereoDataModel;
import it.polimi.traveldream.dataModels.HotelDataModel;
import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="pacchetto")
@SessionScoped
public class PacchettoManagedBean {

	@EJB
	private GestionePacchettoBean gestionePacchetto;
	
	private List<AereoDTO> listaAereiAndata;
	
	private List<AereoDTO> listaAereiRitorno;
	
	private List<AereoDTO> listaAereiDB;
	
	private AereoDataModel datiAerei;
	
	private PacchettoDTO pacchetto;
	
	private String destinazione;
	
	private List<HotelDTO> listaHotelDB;
	
	private List<HotelDTO> listaHotel;
	
	private HotelDataModel datiHotel;
	
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
	
	public List<HotelDTO> getListaHotel() {
		return listaHotel;
	}

	public void setListaHotel(List<HotelDTO> listaHotel) {
		this.listaHotel = listaHotel;
	}
	
	public List<HotelDTO> getListaHotelDB() {
		return listaHotelDB;
	}

	public void setListaHotelDB(List<HotelDTO> listaHotelDB) {
		this.listaHotelDB = listaHotelDB;
	}
	
	public HotelDataModel getDatiHotel() {
		return datiHotel;
	}

	public void setDatiHotel(HotelDataModel datiHotel) {
		this.datiHotel = datiHotel;
	}

	public String aggiungiAerei(){
		pacchetto = new PacchettoDTO();
		if( verificaAtterraggioAerei() && verificaDecolloAerei() ){
			pacchetto.setAereiAndata(listaAereiAndata);
			pacchetto.setAereiRitorno(listaAereiRitorno);
			listaHotelDB = gestionePacchetto.getListaHotel(destinazione);
			setDatiHotel(new HotelDataModel(listaHotelDB));
			return "insertHotel?faces-redirect=true";
		}
		return "index?faces-redirect=true";
	}
	
	public String aggiungiHotel(){
		if ( verificaHotel() ){
			pacchetto.setHotels(listaHotel);
			return "insertEscursione?faces-redirect=true";
		}
		return "insertHotel?faces-redirect=true";
	}

	private boolean verificaAtterraggioAerei(){
		for(AereoDTO aereo:listaAereiAndata){
			System.out.println(listaAereiAndata.size());
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
	
	private boolean verificaHotel(){
		for(HotelDTO hotel:listaHotel){
			if (!hotel.getCitta().equals(destinazione)){
				return false;
			}
		}
		return true;
	}

	
	
	
}
