package it.polimi.traveldream.webBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polimi.traveldream.dataModels.AereoDataModel;
import it.polimi.traveldream.dataModels.EscursioneDataModel;
import it.polimi.traveldream.dataModels.HotelDataModel;
import it.polimi.traveldream.dataModels.PacchettoDataModel;
import it.polimi.traveldream.dataModels.PrenotazioneViaggioDataModel;
import it.polimi.traveldream.dataModels.ViaggioDataModel;
import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;
import it.polimi.traveldream.ejb.sessionBeans.GestioneViaggioBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;


@ManagedBean(name="viaggio")
@SessionScoped
public class ViaggioManagedBean {

	@EJB
	private GestioneViaggioBean gestioneViaggio;
	
	private List<AereoDTO> listaAereiAndata;
	
	private List<AereoDTO> listaAereiRitorno;
	
	private List<AereoDTO> listaAereiAndataDB;
	
	private List<AereoDTO> listaAereiRitornoDB;
	
	private AereoDataModel datiAereiAndata;
	
	private AereoDataModel datiAereiRitorno;
	
	private List<HotelDTO> listaHotelDB;
	
	private List<HotelDTO> listaHotel;
	
	private HotelDataModel datiHotel;
	
	private List<EscursioneDTO> listaEscursioni;
	
	private List<EscursioneDTO> listaEscursioniDB;
	
	private EscursioneDataModel datiEscursioni;
	
	private Prenotazione_ViaggioDTO viaggio;
	
	private List<PacchettoDTO> listaPacchetti;
	
	private List<PacchettoDTO> listaPacchettiSelezionati;
	
	private PacchettoDataModel datiPacchetti;
	
	private SelectItem[] destinazioni;
	
	private AereoDTO aereoAndata;
	
	private HotelDTO hotel;
	
	private AereoDTO aereoRitorno;
	
	private EscursioneDTO escursione;
	
	private int modalita;
	/*	LEGENDA DELLE MODALIT‡
	 * 1		solo aereo
 	   2		aereo escursioni
	   3		aereo hotel
	   4		hotel escursioni
	   5		solo hotel
	   6		solo escursioni
	   7		viaggio completo
	   8		doppio aereo
	   9		doppio aereo escursione
	   10		doppio aereo hotel
	   11		viaggio completo con doppio aereo
	 */
	
	
	
	public AereoDTO getAereoRitorno() {
		return aereoRitorno;
	}
	

	public AereoDTO getAereoAndata() {
		return aereoAndata;
	}
	
	public HotelDTO getHotel() {
		return hotel;
	}
	
	public EscursioneDTO getEscursione(){
		return escursione;
	}
	
	public Prenotazione_ViaggioDTO getViaggio() {
		return viaggio;
	}

	public void setViaggio(Prenotazione_ViaggioDTO viaggio) {
		this.viaggio = viaggio;
	}
	
	@PostConstruct
	public void init(){
		aereoAndata = new AereoDTO();
		hotel = new HotelDTO();
		viaggio = new Prenotazione_ViaggioDTO();
		aereoRitorno = new AereoDTO();
		escursione = new EscursioneDTO();
		modalita = 0;
	}
	

	public String aggiungiDestinazioneDateAereo(){
		String destinazione = aereoAndata.getCittaAtterraggio().toLowerCase();
		Date dataPartenza = aereoAndata.getData();
		listaAereiAndataDB = gestioneViaggio.getListaAereiAndata(destinazione, dataPartenza);
		setDatiAereiAndata(new AereoDataModel(listaAereiAndataDB));
		
		return "mostraAereiScelti?faces-redirect=true";
	}
	
	public String aggiungiDestinazioneDateAereoRitorno(){				
		String destinazioneRitorno = aereoRitorno.getCittaAtterraggio().toLowerCase();
		Date dataPartenzaRitorno = aereoRitorno.getData();
		listaAereiRitornoDB = gestioneViaggio.getListaAereiRitorno(destinazioneRitorno, dataPartenzaRitorno);
		setDatiAereiRitorno(new AereoDataModel(listaAereiRitornoDB));
		
		return "mostraAereiSceltiRitorno?faces-redirect=true";
	}
	
	public String passaAHotel(){
		return "acquistaHotelViaggio?faces-redirect=true";
	}

	public String aggiungiDestinazioneDateHotel(){
		
		String destinazione = hotel.getCitta().toLowerCase();
		listaHotelDB = gestioneViaggio.getListaHotel(destinazione);
		setDatiHotel(new HotelDataModel(listaHotelDB));
		
		return "mostraHotelScelti?faces-redirect=true";
	}
	
	public String aggiungiDestinazioneDateEscursione(){
		
		String destinazione = escursione.getLuogo().toLowerCase();
		Date dataPartenza = escursione.getData();
		listaEscursioniDB = gestioneViaggio.getListaEscursioni(destinazione,dataPartenza);
		setDatiEscursioni(new EscursioneDataModel(listaEscursioniDB));
		
		return "mostraEscursioniScelte?faces-redirect=true";
	}
	
	
	public String settaAereoScelto(int scelta){
		
		if (modalita == 1)
		{
			viaggio.setAereoRitorno(listaAereiRitorno.get(0));
			modalita = 8;
		}
		if(modalita == 0)
			{
			viaggio.setAereo(listaAereiAndata.get(0));
			modalita = 1;
			}
		
		if (scelta==1)
			return "riepilogo?faces-redirect=true";
		if (scelta==2)
			return "acquistaRitorno?faces-redirect=true";
		if (scelta==3)
			return "acquistaHotelViaggio?faces-redirect=true";
		else
			return null;
	}
	
public String settaHotelScelto(int scelta){
		
		viaggio.setHotel(listaHotel.get(0));
		if(modalita == 0)
			modalita = 5;
		else if(modalita == 1)
			modalita = 3;
		else if (modalita == 8)
			modalita = 10;
		
		if (scelta==1)
			return "proseguiAcquisto?faces-redirect=true";
		if (scelta==2)
			return "acquistaRitorno?faces-redirect=true";
		else
			return null;
	}

public String settaEscursioneScelta(int scelta){
	
	viaggio.setEscursioni(listaEscursioni);
	if(modalita == 0)
		modalita = 6;
	else if (modalita == 1)
		modalita = 2;
	else if (modalita == 5)
		modalita = 4;
	else if (modalita == 3)
		modalita = 7;
	else if (modalita == 8)
		modalita = 9;
	else if (modalita == 10)
		modalita = 11;
	
	if (scelta==1)
		return "proseguiAcquisto?faces-redirect=true";
	if (scelta==2)
		return "acquistaRitorno?faces-redirect=true";
	else
		return null;
}

public String acquistaViaggio(){
	
	gestioneViaggio.creaViaggio(viaggio, modalita);
	//qua va aggiunto modalita = 0 per resettare
	return "complimenti?faces-redirect=true";
	
}

public String richiamaHome(){
	return "home?faces-redirect=true";
}
	/*
	public void mostraOfferte(){
		listaPacchetti = gestioneViaggio.getListaPacchetti();
		caricaDestinazioni();
		datiPacchetti = new PacchettoDataModel(listaPacchetti);
	}
	*/


	
	public String aggiungiEscursioni(){
		if ( listaEscursioni.size()>0 ){
			viaggio.setEscursioni(listaEscursioni);
			return "riepilogo?faces-redirect=true";
		}
		else
			return "insertEscursione?faces-redirect=true";
	}

	
	public void validaDate(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		UIInput datainizio = (UIInput)component.getAttributes().get("data_inizio");
		Date dataInizio = (Date)datainizio.getValue();
		Date dataFine = (Date)value;
		if (dataFine.before(dataInizio)){
                throw new ValidatorException(new FacesMessage("La data di fine validit√† deve essere successiva a quella di inizio"));
        }
	}

	public List<AereoDTO> getListaAereiAndataDB() {
		return listaAereiAndataDB;
	}

	public void setListaAereiAndataDB(List<AereoDTO> listaAereiAndataDB) {
		this.listaAereiAndataDB = listaAereiAndataDB;
	}

	public List<AereoDTO> getListaAereiRitornoDB() {
		return listaAereiRitornoDB;
	}

	public void setListaAereiRitornoDB(List<AereoDTO> listaAereiRitornoDB) {
		this.listaAereiRitornoDB = listaAereiRitornoDB;
	}

	public AereoDataModel getDatiAereiRitorno() {
		return datiAereiRitorno;
	}

	public void setDatiAereiRitorno(AereoDataModel datiAereiRitorno) {
		this.datiAereiRitorno = datiAereiRitorno;
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

	public AereoDataModel getDatiAereiAndata() {
		return datiAereiAndata;
	}

	public void setDatiAereiAndata(AereoDataModel datiAerei) {
		this.datiAereiAndata = datiAerei;
	}
	
	public Prenotazione_ViaggioDTO getPacchetto() {
		return viaggio;
	}

	public void setPacchetto(Prenotazione_ViaggioDTO pacchetto) {
		this.viaggio = pacchetto;
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


	public List<PacchettoDTO> getListaPacchetti() {
		return listaPacchetti;
	}


	public void setListaPacchetti(List<PacchettoDTO> listaPacchetti) {
		this.listaPacchetti = listaPacchetti;
	}

	public List<PacchettoDTO> getListaPacchettiSelezionati() {
		return listaPacchettiSelezionati;
	}

	public void setListaPacchettiSelezionati(
			List<PacchettoDTO> listaPacchettiSelezionati) {
		this.listaPacchettiSelezionati = listaPacchettiSelezionati;
	}

	public PacchettoDataModel getDatiPacchetti() {
		return datiPacchetti;
	}

	public void setDatiPacchetti(PacchettoDataModel datiPacchetti) {
		this.datiPacchetti = datiPacchetti;
	}

	private void caricaDestinazioni(){
		ArrayList<String> listaDestinazioni = new ArrayList<String>();
		for(PacchettoDTO pacchetto:listaPacchetti){
			if (!listaDestinazioni.contains(pacchetto.getDestinazione().toUpperCase())){
				listaDestinazioni.add(pacchetto.getDestinazione().toUpperCase());
			}
		}
		destinazioni = new SelectItem[listaDestinazioni.size()+1];
		destinazioni[0]=new SelectItem("", "Seleziona");
		for(int i=0;i<listaDestinazioni.size();i++){
			String dest = listaDestinazioni.get(i);
			destinazioni[i+1] = new SelectItem(dest, dest);
		}
	}

	public SelectItem[] getDestinazioni() {
		return destinazioni;
	}

	public void setDestinazioni(SelectItem[] destinazioni) {
		this.destinazioni = destinazioni;
	}




	
	
	
}
