package it.polimi.traveldream.webBeans;

import java.util.Date;
import java.util.List;

import it.polimi.traveldream.dataModels.AereoDataModel;
import it.polimi.traveldream.dataModels.EscursioneDataModel;
import it.polimi.traveldream.dataModels.HotelDataModel;
import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


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
	
	private Date dataInizio;
	
	private Date dataFine;
	
	private List<HotelDTO> listaHotelDB;
	
	private List<HotelDTO> listaHotel;
	
	private HotelDataModel datiHotel;
	
	private List<EscursioneDTO> listaEscursioni;
	
	private List<EscursioneDTO> listaEscursioniDB;
	
	private EscursioneDataModel datiEscursioni;
	
	//METTERE I METODI PER L'ESCURSIONE E METTERE PAGINA PRIMA DEGLI AEREI
		
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
	
	public List<EscursioneDTO> getListaEscursioni() {
		return listaEscursioni;
	}

	public void setListaEscursioni(List<EscursioneDTO> listaEscursioni) {
		this.listaEscursioni = listaEscursioni;
	}

	public List<EscursioneDTO> getListaEscursioniDB() {
		return listaEscursioniDB;
	}

	public void setListaEscursioniDB(List<EscursioneDTO> listaEscursioniDB) {
		this.listaEscursioniDB = listaEscursioniDB;
	}

	public EscursioneDataModel getDatiEscursioni() {
		return datiEscursioni;
	}

	public void setDatiEscursioni(EscursioneDataModel datiEscursioni) {
		this.datiEscursioni = datiEscursioni;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	@PostConstruct
	public void init(){
		pacchetto = new PacchettoDTO();
		
	}

	public String aggiungiDestinazioneDate(){
			listaAereiDB = gestionePacchetto.getListaAerei();
			setDatiAerei(new AereoDataModel(listaAereiDB));
			return "insertAereo?faces-redirect=true";
	}

	
	public String aggiungiAerei(){
		
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
	
	public void validaDate(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		Object inizioData = component.getAttributes().get("data_inizio");
		Date dataInizio = (Date)inizioData;
		Date dataFine = (Date)value;
		if (dataFine.before(dataInizio)){
                throw new ValidatorException(new FacesMessage("La data di fine validità deve essere successiva a quella di inizio"));
        }
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
