package it.polimi.traveldream.webBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polimi.traveldream.dataModels.AereoDataModel;
import it.polimi.traveldream.dataModels.EscursioneDataModel;
import it.polimi.traveldream.dataModels.HotelDataModel;
import it.polimi.traveldream.dataModels.PacchettoDataModel;
import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;
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

import org.primefaces.event.TransferEvent;


@ManagedBean(name="viaggio")
@SessionScoped
public class ViaggioManagedBean {

	@EJB
	private GestioneViaggioBean gestioneViaggio;
	@EJB
	private GestioneUtenteBean	gestioneUtente;
	
	private AereoDTO listaAereiAndata;
	
	private AereoDTO listaAereiRitorno;
	
	private List<AereoDTO> listaAereiAndataDB;
	
	private List<AereoDTO> listaAereiRitornoDB;
	
	private AereoDataModel datiAereiAndata;
	
	private AereoDataModel datiAereiRitorno;
	
	private List<HotelDTO> listaHotelDB;
	
	private HotelDTO listaHotel;
	
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
	
	private Date minData;
	
	private Date maxData;
	
	private int modalita;
	
	private String errore;
	
	private Boolean checkPrivacy;
	
	
	/*	LEGENDA DELLE MODALIT�
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
		minData= new Date();
		aereoAndata = new AereoDTO();
		hotel = new HotelDTO();
		viaggio = new Prenotazione_ViaggioDTO();
		aereoRitorno = new AereoDTO();
		escursione = new EscursioneDTO();
		modalita = 0;
		setErrore("");
		checkPrivacy = false;
		}
	

	public String aggiungiDestinazioneDateAereo(){
		String destinazione = aereoAndata.getCittaAtterraggio().toLowerCase();
		String cittaPartenza = aereoAndata.getCittaDecollo().toLowerCase();
		Date dataPartenza = aereoAndata.getData();
		listaAereiAndataDB = gestioneViaggio.getListaAereiAndata(destinazione, dataPartenza, cittaPartenza);
		if (listaAereiAndataDB.size()!=0)
		{
			setDatiAereiAndata(new AereoDataModel(listaAereiAndataDB));
			errore ="";
			return "mostraAereiScelti?faces-redirect=true";
		}
		else
		{
			errore = " La tua ricerca non ha conseguito alcun risultato per favore rieseguila";
			return "acquistaViaggio?faces-redirect=true";
		}
	}
	
	public String aggiungiDestinazioneDateAereoRitorno(){				
		String destinazioneRitorno = aereoRitorno.getCittaAtterraggio().toLowerCase();
		String cittaPartenza = aereoRitorno.getCittaDecollo().toLowerCase();
		Date dataPartenzaRitorno = aereoRitorno.getData();
		listaAereiRitornoDB = gestioneViaggio.getListaAereiRitorno(destinazioneRitorno, dataPartenzaRitorno, cittaPartenza);
		if(listaAereiRitornoDB.size()!=0)
		{
			setDatiAereiRitorno(new AereoDataModel(listaAereiRitornoDB));
			errore ="";
			return "mostraAereiSceltiRitorno?faces-redirect=true";
		}
		else
		{
			errore = " La tua ricerca non ha conseguito alcun risultato per favore rieseguila";
			return "acquistaRitorno?faces-redirect=true";
		
		}
	}
	
	public String passaAHotel(){
		return "acquistaHotelViaggio?faces-redirect=true";
	}

	public String aggiungiDestinazioneDateHotel(){
		
		String destinazione = hotel.getCitta().toLowerCase();
		Date dataAndata = hotel.getDataInizio();
		Date dataRitorno = hotel.getDataFine();
		listaHotelDB = gestioneViaggio.getListaHotel(destinazione, dataAndata, dataRitorno);
		if(listaHotelDB.size()!=0)
		{
		setDatiHotel(new HotelDataModel(listaHotelDB));	
		errore ="";
		return "mostraHotelScelti?faces-redirect=true";
		}
		else
		{
			errore = " La tua ricerca non ha conseguito alcun risultato per favore rieseguila";
			return "acquistaHotelViaggio?faces-redirect=true";
		
		}
	}
	
	public String aggiungiDestinazioneDateEscursione(){
		
		String destinazione = escursione.getLuogo().toLowerCase();
		Date dataPartenza = escursione.getData();
		listaEscursioniDB = gestioneViaggio.getListaEscursioni(destinazione,dataPartenza);
		if(listaEscursioniDB.size()!=0)
		{
			setDatiEscursioni(new EscursioneDataModel(listaEscursioniDB));
			errore = "";
			return "mostraEscursioniScelte?faces-redirect=true";
		}
		else
		{
			errore = " La tua ricerca non ha conseguito alcun risultato per favore rieseguila";
			return "acquistaEscursione?faces-redirect=true";
		
		}
	}
	
	
	public String settaAereoScelto(int scelta){
		
		if (modalita == 1)
		{
			if(listaAereiRitorno==null)
				return "mostraAereiSceltiRitorno?faces-redirect=true";
			viaggio.setAereoRitorno(listaAereiRitorno);
			modalita = 8;
		}
		if(modalita == 0)
			{
			if(listaAereiAndata==null)
				return "mostraAereiScelti?faces-redirect=true";
			viaggio.setAereo(listaAereiAndata);
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
	
		if(listaHotel==null)
			return "mostraHotelScelti?faces-redirect=true";
	
		listaHotel.setDataInizio(hotel.getDataInizio());
		listaHotel.setDataFine(hotel.getDataFine());
		viaggio.setHotel(listaHotel);
		if(modalita == 0)
			modalita = 5;
		else if(modalita == 1)
			modalita = 3;
		else if (modalita == 8)
			modalita = 10;
		
		if (scelta==1)
			return "riepilogo?faces-redirect=true";
		if (scelta==2)
			return "acquistaEscursione?faces-redirect=true";
		else
			return null;
	}

public String settaEscursioneScelta(int scelta){
	
	if(listaEscursioni.size()==0)
		return "mostraEscursioniScelte?faces-redirect=true";
	
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
		return "riepilogo?faces-redirect=true";
	else
		return null;
}

public String acquistaViaggio(){
	if(checkPrivacy){
	gestioneViaggio.creaViaggio(viaggio, modalita);
	errore = "";
	return "complimenti?faces-redirect=true";
	}
	return "riepilogo?faces-redirect=true";
	
}

public String richiamaHome(){
	resettaTutto();
	if(gestioneUtente.isUtente())
		return "index?faces-redirect=true";
	else
		return "/employee/index?faces-redirect=true";
}
	
	public String aggiungiEscursioni(){
		if ( listaEscursioni.size()>0 ){
			viaggio.setEscursioni(listaEscursioni);
			return "riepilogo?faces-redirect=true";
		}
		else
			return "insertEscursione?faces-redirect=true";
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
	
	public AereoDTO getListaAereiAndata() {
		return listaAereiAndata;
	}

	public void setListaAereiAndata(AereoDTO listaAereiAndata) {
		this.listaAereiAndata = listaAereiAndata;
	}

	public AereoDTO getListaAereiRitorno() {
		return listaAereiRitorno;
	}

	public void setListaAereiRitorno(AereoDTO listaAereiRitorno) {
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

	public HotelDTO getListaHotel() {
		return listaHotel;
	}

	public void setListaHotel(HotelDTO listaHotel) {
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

	public SelectItem[] getDestinazioni() {
		return destinazioni;
	}

	public void setDestinazioni(SelectItem[] destinazioni) {
		this.destinazioni = destinazioni;
	}


	public Date getMinData() {
		return minData;
	}


	public void setMinData(Date minData) {
		this.minData = minData;
	}


	public String getErrore() {
		return errore;
	}


	public void setErrore(String errore) {
		this.errore = errore;
	}


	//Metodi Resetter
	
	
	public void resetErrore(){
		
		if (errore.isEmpty())
			errore = "";
		
	}
	
	public void resetAndata(){
		
		aereoAndata.setCittaAtterraggio(null);
		aereoAndata.setCittaDecollo(null);
		aereoAndata.setData(null);
		
		resetErrore();
		
	}
	
	private void resetViaggio(){
		viaggio.setAereo(null);
		viaggio.setAereoRitorno(null);
		viaggio.setData(null);
		viaggio.setHotel(null);
		viaggio.setCheckInHotel(null);
		viaggio.setCheckOutHotel(null);
		viaggio.setEscursioni(null);
		
	}
	private void resetRitorno(){
		
		aereoRitorno.setCittaAtterraggio(null);
		aereoRitorno.setCittaDecollo(null);
		aereoRitorno.setData(null);
		resetErrore();
		
	}
	
	private void resetHotel(){

		hotel.setCitta(null);
		hotel.setDataInizio(null);
		hotel.setDataFine(null);
		resetErrore();
	}
	
	private void resetEscursione(){
		
		
		escursione.setLuogo(null);
		escursione.setData(null);
		resetErrore();
		
	}
	
	public void resettaTutto(){
		
		resetAndata();
		resetRitorno();
		resetHotel();
		resetEscursione();
		resetErrore();
		resetViaggio();
		modalita = 0;
		checkPrivacy = false;
		
	}


	public Boolean getCheckPrivacy() {
		return checkPrivacy;
	}


	public void setCheckPrivacy(Boolean checkPrivacy) {
		this.checkPrivacy = checkPrivacy;
	}
	
	public void listenerData(){
		
} 
	public void listenerDataRitorno(){
		
		Date arrivo = hotel.getDataInizio();
		Date ritorno = hotel.getDataFine();
		
		if (ritorno.before(arrivo))
		{
			hotel.setDataInizio(null);
		}

	
	
	
} 
}
