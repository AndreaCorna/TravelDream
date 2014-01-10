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
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;

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

import org.primefaces.model.DualListModel;


@ManagedBean(name="pacchetto")
@SessionScoped
public class PacchettoManagedBean {

	@EJB
	private GestionePacchettoBean gestionePacchetto;
	
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
	
	private PacchettoDTO pacchetto;
	
	private List<PacchettoDTO> listaPacchetti;
	
	private List<PacchettoDTO> listaPacchettiSelezionati;
	
	private PacchettoDataModel datiPacchetti;
	
	private SelectItem[] destinazioni;
	
	private DualListModel<String> listaSelezioneAereoAndata;
	
	private DualListModel<String> listaSelezioneAereoRitorno;
	
	private DualListModel<String> listaSelezioneHotel;
	
	private DualListModel<String> listaSelezioneEscursioni;
	
	private int id;
	
	
	@PostConstruct
	public void init(){
		pacchetto = new PacchettoDTO();
		
	}
	
	public void initPersonalizza(String id){
		if(	!id.equals("")){
			Integer value = new Integer(id);
			this.id = value.intValue();
		}
		boolean found = false;
		for(int i=0; i<listaPacchetti.size() && !found; i++){
			if(listaPacchetti.get(i).getId() == this.id){
				pacchetto = listaPacchetti.get(i);
				found = true;
				loadListeSelezione(pacchetto);
			}
		}
		
	}
	
	public void initModifica(String id){
		initPersonalizza(id);
		datiAereiAndata = new AereoDataModel(pacchetto.getAereiAndata());
		datiAereiRitorno = new AereoDataModel(pacchetto.getAereiRitorno());
		datiHotel = new HotelDataModel(pacchetto.getHotels());
		datiEscursioni = new EscursioneDataModel(pacchetto.getEscursioni());
		resetSelezioni();
	}
	
	public void mostraOfferte(){
		listaPacchetti = gestionePacchetto.getListaPacchetti();
		caricaDestinazioni();
		datiPacchetti = new PacchettoDataModel(listaPacchetti);
	}
	

	public String aggiungiDestinazioneDate(){
		String destinazione = pacchetto.getDestinazione();
		Date inizioValidita = pacchetto.getInizio_Validita();
		Date fineValidita = pacchetto.getFine_Validita();
		listaAereiAndataDB = gestionePacchetto.getListaAereiAndata(destinazione, inizioValidita, fineValidita);
		listaAereiRitornoDB = gestionePacchetto.getListaAereiRitorno(destinazione, inizioValidita, fineValidita);
		setDatiAereiAndata(new AereoDataModel(listaAereiAndataDB));
		setDatiAereiRitorno(new AereoDataModel(listaAereiRitornoDB));
		return "insertAereo?faces-redirect=true";
	}

	
	public String aggiungiAerei(){
		
		if (validaAerei()){
			pacchetto.setAereiAndata(listaAereiAndata);
			pacchetto.setAereiRitorno(listaAereiRitorno);
			listaHotelDB = gestionePacchetto.getListaHotel(pacchetto.getDestinazione());
			setDatiHotel(new HotelDataModel(listaHotelDB));
			return "insertHotel?faces-redirect=true";
		}
		else
			return null;
	}
	
	
	
	public String aggiungiHotel(){
		if ( listaHotel.size()>0 ){
			pacchetto.setHotels(listaHotel);
			listaEscursioniDB = gestionePacchetto.getListaEscursioni(pacchetto.getDestinazione(), pacchetto.getInizio_Validita(),
							pacchetto.getFine_Validita());
			setDatiEscursioni(new EscursioneDataModel(listaEscursioniDB));
			return "insertEscursione?faces-redirect=true";
		}
		else
			return  null;
		
	}
	
	public String aggiungiEscursioni(){
		if ( listaEscursioni.size()>0 ){
			pacchetto.setEscursioni(listaEscursioni);
			return "riepilogo?faces-redirect=true";
		}
		else
			return  null;
	}
	
	public String creaPacchetto(){
		gestionePacchetto.creaPacchetto(pacchetto);
		return "index?faces-redirect=true";
	}
	
	public String modificaPacchetto(){
		if(validaModifiche()){
			pacchetto.setAereiAndata(listaAereiAndata);
			pacchetto.setAereiRitorno(listaAereiRitorno);
			pacchetto.setHotels(listaHotel);
			pacchetto.setEscursioni(listaEscursioni);
			pacchetto.setId(id);
			gestionePacchetto.modificaPacchetto(pacchetto);
			return "index?faces-redirect=true";
		}
		return "modificaPacchetto.xhtml?id="+id;
	}
	
	public String eliminaPacchetto(){
		pacchetto.setId(id);
		gestionePacchetto.eliminaPacchetto(pacchetto);
		return "index?faces-redirect=true";
	}
	
	public void validaDate(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		UIInput datainizio = (UIInput)component.getAttributes().get("data_inizio");
		Date dataInizio = (Date)datainizio.getValue();
		Date dataFine = (Date)value;
		if (dataFine.before(dataInizio)){
                throw new ValidatorException(new FacesMessage("La data di fine validità deve essere successiva a quella di inizio"));
        }
	}
	
	public void validaId(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestionePacchetto.esisteIdPacchetto(value.toString())){
                throw new ValidatorException(new FacesMessage("Id già utilizzato. Scegline un altro"));
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
	
	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
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

	public DualListModel<String> getListaSelezioneAereoAndata() {
		return listaSelezioneAereoAndata;
	}

	public void setListaSelezioneAereoAndata(
			DualListModel<String> listaSelezioneAereoAndata) {
		this.listaSelezioneAereoAndata = listaSelezioneAereoAndata;
	}
	
	private void loadListeSelezione(PacchettoDTO pacchetto){
		loadListaAereiAndata();
		loadListaAereiRitorno();
		loadListaHotel();
		loadListaEscursioni();
	}
	
	private void loadListaAereiAndata(){
		ArrayList<String> aereiAndata = new ArrayList<String>();
		for(AereoDTO aereo:pacchetto.getAereiAndata()){
			Integer id = new Integer(aereo.getId());
			aereiAndata.add(id.toString());
		}
		ArrayList<String> aereiAndataTarget = new ArrayList<String>();
		listaSelezioneAereoAndata = new DualListModel<>(aereiAndata, aereiAndataTarget);
	}
	
	private void loadListaAereiRitorno(){
		ArrayList<String> aereiRitorno = new ArrayList<String>();
		for(AereoDTO aereo:pacchetto.getAereiRitorno()){
			Integer id = new Integer(aereo.getId());
			aereiRitorno.add(id.toString());
		}
		ArrayList<String> aereiRitornoTarget = new ArrayList<String>();
		setListaSelezioneAereoRitorno(new DualListModel<>(aereiRitorno, aereiRitornoTarget));
	}
	
	private void loadListaHotel(){
		ArrayList<String> hotels = new ArrayList<String>();
		for(HotelDTO hotel:pacchetto.getHotels()){
			Integer id = new Integer(hotel.getId());
			hotels.add(id.toString());
		}
		ArrayList<String> hotelsTarget = new ArrayList<String>();
		setListaSelezioneHotel(new DualListModel<>(hotels, hotelsTarget));
	
	}
	
	private void loadListaEscursioni(){
		ArrayList<String> escursioni = new ArrayList<String>();
		for(EscursioneDTO escursione:pacchetto.getEscursioni()){
			Integer id = new Integer(escursione.getId());
			escursioni.add(id.toString());
		}
		ArrayList<String> escursioniTarget = new ArrayList<String>();
		setListaSelezioneEscursioni(new DualListModel<>(escursioni, escursioniTarget));
	}

	public DualListModel<String> getListaSelezioneAereoRitorno() {
		return listaSelezioneAereoRitorno;
	}

	public void setListaSelezioneAereoRitorno(
			DualListModel<String> listaSelezioneAereoRitorno) {
		this.listaSelezioneAereoRitorno = listaSelezioneAereoRitorno;
	}

	public DualListModel<String> getListaSelezioneHotel() {
		return listaSelezioneHotel;
	}

	public void setListaSelezioneHotel(DualListModel<String> listaSelezioneHotel) {
		this.listaSelezioneHotel = listaSelezioneHotel;
	}

	public DualListModel<String> getListaSelezioneEscursioni() {
		return listaSelezioneEscursioni;
	}

	public void setListaSelezioneEscursioni(DualListModel<String> listaSelezioneEscursioni) {
		this.listaSelezioneEscursioni = listaSelezioneEscursioni;
	}
	
	private boolean validaAerei(){
		boolean corretto = false;
		if(listaAereiAndata.size()==0 || listaAereiRitorno.size()==0)
			return false;
		else{
			for(int i=0;i<listaAereiAndata.size();i++){
				corretto = false;
				AereoDTO andata = listaAereiAndata.get(i);
				for(int j=0;j<listaAereiRitorno.size() && !corretto;j++){
					AereoDTO ritorno = listaAereiRitorno.get(j);
					if(andata.getData().before(ritorno.getData()) && 
							andata.getCittaDecollo().equals(ritorno.getCittaAtterraggio()))
						corretto=true;
				}
				if(!corretto)
					return corretto;
			}
		}
		return corretto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private boolean validaModifiche(){
		return validaAerei() && listaHotel.size()>0 && listaEscursioni.size()>=0;
	}
	
	private void resetSelezioni(){
		if(listaAereiAndata != null)
			listaAereiAndata.clear();
		if(listaAereiRitorno != null)
			listaAereiRitorno.clear();
		if(listaEscursioni != null)
			listaEscursioni.clear();
		if(listaHotel != null)
			listaHotel.clear();
	}



	
	
	
}
