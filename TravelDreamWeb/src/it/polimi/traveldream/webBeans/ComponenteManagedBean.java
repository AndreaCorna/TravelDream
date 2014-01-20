package it.polimi.traveldream.webBeans;

import java.util.Date;
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
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 * Managed bean per la gestione dei componenti di un pacchetto
 * @author Alessandro Brunitti - Andrea Corna
 */
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
	
	private List<EscursioneDTO> listaEscursioniDB;
	
	private List<EscursioneDTO> escursioniSelezionate;
	
	/**
	 * Costruttore di default
	 */
	public ComponenteManagedBean(){
		
	}
	
	/**
	 * Il metodo istanzia gli attributi aereo, hotel e escursione.
	 */
	@PostConstruct
	public void init(){
		setAereo(new AereoDTO());
		setHotel(new HotelDTO());
		setEscursione(new EscursioneDTO());
	}

	/**
	 * Il metodo chiama l'ejb per l'aggiunta di un aereo nel database
	 * @return redirect to index page
	 */
	public String aggiungiAereoDB(){
		gestioneComp.aggiungiAereoDB(aereo);
		String message = "Aereo Aggiunto";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		return "index?faces-redirect=true";
	}
	
	/**
	 * Il metodo chiama l'ejb per l'aggiunta di un hotel nel database
	 * @return redirect to index page
	 */
	public String aggiungiHotelDB(){
		gestioneComp.aggiungiHotelDB(hotel);
		String message = "Hotel Aggiunto";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		return "index?faces-redirect=true";
	}
	
	/**
	 * Il metodo chiama l'ejb per l'aggiunta di un'escursione nel database
	 * @return redirect to index page
	 */
	public String aggiungiEscursioneDB(){
		gestioneComp.aggiungiEscursioneDB(escursione);
		String message = "Escursione Aggiunta";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		return "index?faces-redirect=true";
	}
	
	/**
	 * Il metodo salva la selezione dell'aereo realizzata nell'interfaccia utente
	 * @return redirect alla pagina per la modifica
	 */
	public String selezionaAereo(){
		aereo = aereiSelezionati.get(0);
		return "modificaAereo?faces-redirect=true";
	}
	
	/**
	 * Il metodo carica dal database la lista degli aerei
	 */
	public void caricaListaAerei(){
		listaAereiDB = gestionePacchetto.getListaAerei();
	}
	
	/**
	 * Il metodo carica dal database la lista degli hotel
	 */
	public void caricaListaHotel(){
		listaHotelDB = gestionePacchetto.getListaHotel();
	}
	
	/**
	 * Il metodo carica dal database la lista delle escursioni
	 */
	public void caricaListaEscursioni(){
		listaEscursioniDB = gestionePacchetto.getListaEscursioni();
	}
	
	/**
	 * Il metodo carica dal database l'escursione che deve essere modificata
	 * @param id - id dell'escursione
	 */
	public void initModificaEscursione(String id){
		escursione = gestioneComp.getEscursioneById(id);
	}
	/**
	 * Il metodo carica dal database l'hotel che deve essere modificato
	 * @param id - id dell'hotel
	 */
	public void initModificaHotel(String id){
		hotel = gestioneComp.getHotelById(id);
	}
	/**
	 * Metodo per salvare le modifiche all'hotel selezionato
	 * @return redirect alla index
	 */
	public String modificaHotel(){
		gestioneComp.modificaHotel(hotel);
		return "index?faces-redirect=true";
	}
	/**
	 * Metodo per salvare le modifiche all'aereo selezionato
	 * @return redirect alla index
	 */
	public String modificaAereo(){
		gestioneComp.modificaAereo(aereo);
		return "index?faces-redirect=true";
	}
	/**
	 * Metodo per salvare le modifiche all'escursione selezionata
	 * @return redirect alla index
	 */
	public String modificaEscursione(){
		gestioneComp.modificaEscursione(escursione);
		return "index?faces-redirect=true";
	}
	
	public String eliminaAereo(){
		gestioneComp.eliminaAereo(aereo);
		return "index?faces-redirect=true";
	}
	
	public String eliminaHotel(){
		gestioneComp.eliminaHotel(hotel);
		return "index?faces-redirect=true";
	}
	
	public String eliminaEscursione(){
		gestioneComp.eliminaEscursione(escursione);
		return "index?faces-redirect=true";
	}
	/**
	 * Il metodo setta l'aereo che è stato selezionato per la modifica
	 * @param id - id aereo selezionato
	 */
	public void initModificaAereo(String id){
		aereo = gestioneComp.getAereoById(id);
	}
	
	
	/*
	 * Validatori dei dati inseriti
	 */
	
	
	/**
	 * Validator chiamato per la verifica del campo numerico
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validaNumero(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("Il campo può contenere solo numeri"));
		}
	}
	/*/**
	 * Validator chiamato per controllare la coerenza tra le date dell'hotel
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 
	public void validaDateHotel(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		UIInput datainizio = (UIInput)component.getAttributes().get("data_inizio");
		Date dataInizio = (Date)datainizio.getValue();
		Date dataFine = (Date)value;
		if (dataFine.before(dataInizio)){
                throw new ValidatorException(new FacesMessage("La data di fine validità deve essere successiva a quella di inizio"));
        }
	}*/
	
	/**
	 * Il metodo verifica che la stringa contenga solo numeri
	 * @param id - la stringa da verificare
	 * @return <true> se contiene solo numeri, <false> altrimenti
	 */
	private boolean isNumeroCorretto(String id){
		Pattern p1 = Pattern.compile("[0-9]+");
		Matcher m1 = p1.matcher(id);
		return  m1.find();
	}

	/*METODI GETTER E SETTER */
	
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

	public List<EscursioneDTO> getListaEscursioniDB() {
		return listaEscursioniDB;
	}

	public void setListaEscursioniDB(List<EscursioneDTO> listaEscursioniDB) {
		this.listaEscursioniDB = listaEscursioniDB;
	}

	public List<EscursioneDTO> getEscursioniSelezionate() {
		return escursioniSelezionate;
	}

	public void setEscursioniSelezionate(List<EscursioneDTO> escursioniSelezionate) {
		this.escursioniSelezionate = escursioniSelezionate;
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
	

	
}




