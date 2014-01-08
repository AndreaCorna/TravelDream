package it.polimi.traveldream.webBeans;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestioneComponenteBean;
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;



@ManagedBean(name="componente")
@ViewScoped
public class ComponenteManagedBean {
	
	@EJB
	private GestioneComponenteBean gestioneComp;
	@EJB
	private GestionePacchettoBean gestionePacchetto;
	
	private AereoDTO aereo;
	
	private HotelDTO hotel;
	
	private EscursioneDTO escursione;
	
	private List<AereoDTO> listaAereiDB;
	
	private List<AereoDTO> aereiSelezionati;
	
	private List<HotelDTO> listaHotelDB;
	
	private List<HotelDTO> hotelSelezionati;
	
	public ComponenteManagedBean(){
		
	}
	
	@PostConstruct
	public void init(){
		setAereo(new AereoDTO());
		setHotel(new HotelDTO());
		setEscursione(new EscursioneDTO());
	}

	public AereoDTO getAereo() {
		return aereo;
	}

	public void setAereo(AereoDTO aereo) {
		this.aereo = aereo;
	}
	
	public HotelDTO getHotel(){
		return hotel;
	}
	
	public void setHotel(HotelDTO hotel){
		this.hotel = hotel;
	}
	
	public EscursioneDTO getEscursione() {
		return escursione;
	}

	public void setEscursione(EscursioneDTO escursione) {
		this.escursione = escursione;
	}
	
	public String aggiungiAereoDB(){
		gestioneComp.aggiungiAereoDB(aereo);
		return "index?faces-redirect=true";
	}
	
	public String aggiungiHotelDB(){
		gestioneComp.aggiungiHotelDB(hotel);
		return "index?faces-redirect=true";
	}
	
	public String aggiungiEscursioneDB(){
		gestioneComp.aggiungiEscursioneDB(escursione);
		return "index?faces-redirect=true";
	}
	
	public String selezionaAereo(){
		aereo = aereiSelezionati.get(0);
		return "modificaAereo?faces-redirect=true";
	}
	
	public void caricaListaAerei(){
		listaAereiDB = gestionePacchetto.getListaAerei();
	}
	
	public void caricaListaHotel(){
		listaHotelDB = gestionePacchetto.getListaHotel();
	}
	
	public void initModificaHotel(String id){
		hotel = gestioneComp.getHotelById(id);
	}
	
	public String modificaHotel(){
		gestioneComp.modificaHotel(hotel);
		return "index?faces-redirect=true";
	}
	
	public String modificaAereo(){
		gestioneComp.modificaAereo(aereo);
		return "index?faces-redirect=true";
	}
	
	public void initModificaAereo(String id){
		aereo = gestioneComp.getAereoById(id);
	}
	
	
	/*
	 * Validatori dei dati inseriti
	 */
	
	public void validaIdAereo(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("L'id può contenere solo numeri"));
		}
		if (gestioneComp.esisteAereo(value.toString())){
            throw new ValidatorException(new FacesMessage("Identificativo già utilizzato"));
		}
		
	}
	
	public void validaIdHotel(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("L'id può contenere solo numeri"));
		}
		if (gestioneComp.esisteHotel(value.toString())){
            throw new ValidatorException(new FacesMessage("Identificativo già utilizzato"));
		}
		
	}
	
	public void validaIdEscursione(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("L'id può contenere solo numeri"));
		}
		if (gestioneComp.esisteEscursione(value.toString())){
            throw new ValidatorException(new FacesMessage("Identificativo già utilizzato"));
		}
		
	}
	
	
	public void validaNumero(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("Il campo può contenere solo numeri"));
		}
	}
	
	/*public void aereoVola(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if(aereo.getCittaDecollo().compareTo((String)value) == 0){
			throw new ValidatorException(new FacesMessage("Il decollo deve essere diverso dall'atterraggio"));
		}
	}*/
	
	private boolean isNumeroCorretto(String id){
		Pattern p1 = Pattern.compile("[0-9]+");
		Matcher m1 = p1.matcher(id);
		return  m1.find();
	}

	
	public List<AereoDTO> getListaAereiDB() {
		return listaAereiDB;
	}

	public void setListaAereiDB(List<AereoDTO> listaAerei) {
		this.listaAereiDB = listaAerei;
	}

	public List<AereoDTO> getAereiSelezionati() {
		return aereiSelezionati;
	}

	public void setAereiSelezionati(List<AereoDTO> aereiSelezionati) {
		this.aereiSelezionati = aereiSelezionati;
	}

	public List<HotelDTO> getListaHotelDB() {
		return listaHotelDB;
	}

	public void setListaHotelDB(List<HotelDTO> listaHotelDB) {
		this.listaHotelDB = listaHotelDB;
	}

	public List<HotelDTO> getHotelSelezionati() {
		return hotelSelezionati;
	}

	public void setHotelSelezionati(List<HotelDTO> hotelSelezionati) {
		this.hotelSelezionati = hotelSelezionati;
	}

	
}




