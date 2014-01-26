package it.polimi.traveldream.webBeans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import it.polimi.traveldream.dataModels.AereoDataModel;
import it.polimi.traveldream.dataModels.EscursioneDataModel;
import it.polimi.traveldream.dataModels.HotelDataModel;
import it.polimi.traveldream.dataModels.PacchettoDataModel;
import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CondivisioneDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.sessionBeans.CondivisioneBean;
import it.polimi.traveldream.ejb.sessionBeans.GestionePacchettoBean;
import it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean;
import it.polimi.traveldream.ejb.sessionBeans.GestioneUtenteBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;


@ManagedBean(name="pacchetto")
@SessionScoped
public class PacchettoManagedBean {

	@EJB
	private GestionePacchettoBean gestionePacchetto;
	@EJB
	private GestioneUtenteBean gestioneUtente;
	@EJB
	private GestionePrenotazioneBean gestionePrenotazione;
	@EJB
	private CondivisioneBean gestioneCondivisione;
	
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
	
	private PacchettoDataModel datiPacchettiCasuali;
	
	private SelectItem[] destinazioni;
	
	private DualListModel<String> listaSelezioneEscursioni;
	
	private int id;
	
	private Prenotazione_PacchettoDTO prenotazione;
	
	private String idAereoAndata;
	
	private String idAereoRitorno;
	
	private String idHotel;
	
	private CondivisioneDTO condivisione;
	
	private boolean checkCondivisione;
	
	private String linkCondivisione;
	
	private Date partenza;
	
	private Date ritorno;
	
	private float costo;
	
	private List<String> numeroPersone;
	
	private String numero;
	
	private Date dataOdierna;
	
	private boolean modifica;
	
	private int idPrenotazione;
	
	private boolean checkCondizione = false;
	/**
	 * Metodo che si occupa di inizializzare la pagina relativa ai pacchetti 
	 */
	
	
	@PostConstruct
	public void init(){
		pacchetto = new PacchettoDTO();
			
	}
	
	/**
	 * Metodo che si occupa di inizializzare la pagina relativa alla personalizzazione dei pacchetti 
	 * @param id
	 */
	public void initPersonalizza(String id, String pre){
		resetSelezione();
		dataOdierna = new Date();
		prenotazione = new Prenotazione_PacchettoDTO();
		if(	!id.equals("")){
			Integer value = new Integer(id);
			this.id = value.intValue();
		}
		if(!pre.equals("")){
			Integer value = new Integer(pre);
			this.idPrenotazione = value.intValue();
		}
		boolean found = false;
		if(listaPacchetti == null){
			listaPacchetti = gestionePacchetto.getListaPacchetti();
		}
		for(int i=0; i<listaPacchetti.size() && !found; i++){
			if(listaPacchetti.get(i).getId() == this.id){
				pacchetto = listaPacchetti.get(i);
				found = true;
			}
		}
		
		listaAereiAndataDB = pacchetto.getAereiAndata();
		listaAereiRitornoDB = pacchetto.getAereiRitorno();
		listaHotelDB = pacchetto.getHotels();
		listaEscursioniDB = pacchetto.getEscursioni();
		loadListaEscursioni();
		ArrayList<String> lista = new ArrayList<String>();
		for(int i=1;i<=pacchetto.getNumeroPersone();i++){
			Integer value = new Integer(i);
			String numero = value.toString();
			lista.add(numero);
			
		}
		numeroPersone = lista;
	}
	
	/**
	 * Metodo che si occupa di inizializzare la pagina di modifiche del pacchetto 
	 * @param id
	 */
	public void attivaModifica(TabChangeEvent event) {  
		if (event.getTab().getId().equals("tabPrenotazione")) {
		      modifica = true;
		      resetSelezioni();
		}else
			modifica = false;
		System.out.print(modifica);
	}
     
       
	
	public void initModifica(String id){
		initPersonalizza(id,null);
		datiAereiAndata = new AereoDataModel(pacchetto.getAereiAndata());
		datiAereiRitorno = new AereoDataModel(pacchetto.getAereiRitorno());
		datiHotel = new HotelDataModel(pacchetto.getHotels());
		datiEscursioni = new EscursioneDataModel(pacchetto.getEscursioni());
		resetSelezioni();
	}
	
	/**
	 * Metodo che si occupa di mostrare le offerte all'interno delle pagine 
	 */
	public void mostraOfferte(){
		listaPacchetti = gestionePacchetto.getListaPacchetti();
		caricaDestinazioni();
		Random random = new Random();
		int high = listaPacchetti.size();
		int numeroCasuale = random.nextInt(high)+ 0;
		if (numeroCasuale==0)
			numeroCasuale = 1;
		datiPacchettiCasuali = new PacchettoDataModel(listaPacchetti.subList(numeroCasuale-1, numeroCasuale));
		datiPacchetti = new PacchettoDataModel(listaPacchetti);
		
	}
	
	/**
	 * Metodo che si occupa di aggiungere tutte le informazioni relative agli aerei che l'utente ha inserito, all'interno del database
	 * @return
	 */
	

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

	
	/**
	 * Metodo che aggiunge gli aerei all'interno di un pacchetto nel database 
	 * @return
	 */
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
	
	
	
	/**
	 * Metodo che aggiunge gli hotel all'interno di un pacchetto nel database
	 * @return
	 */
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
	
	/**
	 * Metodo che aggiunge le escursione all'interno di un pacchetto nel database
	 * @return
	 */
	public String aggiungiEscursioni(){
		if ( listaEscursioni.size()>0 ){
			pacchetto.setEscursioni(listaEscursioni);
			return "riepilogo?faces-redirect=true";
		}
		else
			return  null;
	}
	
	/**
	 * Metodo che presi tutti i dati che l'utente ha inserito li mette all'interno del pacchetto nel database 
	 * @return
	 */
	public String creaPacchetto(){
		gestionePacchetto.creaPacchetto(pacchetto);
		return "index?faces-redirect=true";
	}
	
	/**
	 * Metodo che viene chiamato per modificare un pacchetto all'interno del database
	 * @return
	 */
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
	
	/**
	 * Metodo che elmina un pacchetto all'interno del database
	 * @return
	 */
	public String eliminaPacchetto(){
		pacchetto.setId(id);
		gestionePacchetto.eliminaPacchetto(pacchetto);
		return "index?faces-redirect=true";
	}
	
	/**
	 * Metodo che viene utilizzato per effettuare la prenotazione di un pacchetto
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String prenotaPacchetto(){
		costo = 0;
		setSelezioneAerei();
		setSelezioneHotel();
		setSelezioneEscursioni();
		if(selezionePossibile()){
			Date date = new Date();
			prenotazione.setPacchetto(pacchetto);
			prenotazione.setUtente(gestioneUtente.getUtenteDTO());
			prenotazione.setData(date);
			Date dataCheckIn = prenotazione.getAereoAndata().getData();
			Date dataCheckOut = prenotazione.getAereoRitorno().getData();
			Date in = new Date(dataCheckIn.getYear(), dataCheckIn.getMonth(), dataCheckIn.getDate());
			Date out = new Date(dataCheckOut.getYear(), dataCheckOut.getMonth(), dataCheckOut.getDate());
			prenotazione.setCheckInHotel(in);
			prenotazione.setCheckOutHotel(out);
			int diffInDays = (int)( (out.getTime() - in.getTime()) 
	                 / (1000 * 60 * 60 * 24.0) );
			costo = costo + prenotazione.getHotel().getCostoGiornaliero() * (diffInDays);
			Integer value = new Integer(numero);
			costo = costo * value.intValue();
			prenotazione.setNumeroPersone(value.intValue());
			return "riep&cond?faces-redirect=true";
		}
		return null;
	}
	
	/**
	 * Metodo che ritorna true se una data selezione � possibile false altrimenti  
	 * @return un booleano che � true se la selezione � possibile false altrimenti 
	 */
	private boolean selezionePossibile(){
		return checkNumeroPosti() && checkDate() && checkAerei();
	}
	
	/**
	 * Metodoc he dati due aerei controlla che soddisfino determinati vincoli
	 * @return
	 */
	private boolean checkAerei(){
		AereoDTO andata = prenotazione.getAereoAndata();
		AereoDTO ritorno = prenotazione.getAereoRitorno();
		if(!andata.getCittaDecollo().equals(ritorno.getCittaAtterraggio())){
			String message = "Con gli aerei scelti non ritorni da dove sei partito";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return false;
		}
		if(andata.getData().after(ritorno.getData())){
			String message = "Pensi di tornare prima di partire?!";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return false;
		}
		return true;
	}
	
	/**
	 * Metodo che serve per controllare se vi sono posti disponibili sugli aerei di andata e ritorno e sull'hotel
	 * @return true se vi sono posti, false altrimenti 
	 */
	private boolean checkNumeroPosti(){
		AereoDTO andata = prenotazione.getAereoAndata();
		AereoDTO ritorno = prenotazione.getAereoRitorno();
		HotelDTO hotel = prenotazione.getHotel();
		if(andata.getPostiDisponibili()<pacchetto.getNumeroPersone()){
			String message = "I posti sull'aereo di andata non sono sufficienti";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return false;
		}
		if(ritorno.getPostiDisponibili()<pacchetto.getNumeroPersone()){
			String message = "I posti sull'aereo di ritorno non sono sufficienti";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return false;
		}
		if(hotel.getCamereDisponibili()<=0){
			String message = "L'albergo non ha camere disponibili";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return false;
		}		
		return true;
	}
	
	/**
	 * Metodo che si occupa di controllare se le date soddisfano determinati vincoli 
	 * @return
	 */
	private boolean checkDate(){
		AereoDTO andata = prenotazione.getAereoAndata();
		AereoDTO ritorno = prenotazione.getAereoRitorno();
		List<EscursioneDTO> lista = prenotazione.getEscursioni();
		for(EscursioneDTO escursione:lista){
			if(escursione.getData().before(andata.getData()) ||
					escursione.getData().after(ritorno.getData())){
				String message = "La data dell'escursione "+escursione.getId()+"  non � compresa tra le date degli aerei scelti";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				return false;
			}
		}
		return true;
	}
		
	/**
	 * Metodo che conferma la prenotazione 
	 * @return
	 */
	public String confermaPrenotazione(){
		if(!modifica)
			gestionePrenotazione.inserisciPrenotazionePacchetto(prenotazione);
		else{
			prenotazione.setId(idPrenotazione);
			gestionePrenotazione.aggiornaPrenotazionePacchetto(prenotazione);
			
		}
		if(checkCondivisione){
			setCondivisione();
			gestioneCondivisione.creaCondivisione(condivisione, prenotazione);
		}
		return "index?faces-redirect=true";
	}
	
	/**
	 * Metodo setter della condivisione
	 */
	private void setCondivisione(){
		condivisione = new CondivisioneDTO();
		condivisione.setData(new Date());
		condivisione.setLink(linkCondivisione);
		condivisione.setUtente(gestioneUtente.getUtenteDTO());
	}
	
	/**
	 * Metodo che gestisce l'interfaccia utente
	 * @param event
	 */
	public void onTransfer(TransferEvent event){
		StringBuilder builder = new StringBuilder();  
        for(Object item : event.getItems()) {  
            builder.append(item.toString()).append("<br />");  
        }  
		FacesMessage msg = new FacesMessage();  
        msg.setSeverity(FacesMessage.SEVERITY_INFO);  
        msg.setSummary("Escursione selezionata");  
        msg.setDetail(builder.toString());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
		
	}
	
	public void validaCondizione(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox) component;
		if(checkBox.getSubmittedValue().equals(false)){
			 throw new ValidatorException(new FacesMessage("Devi accettare le condizioni di viaggio"));
		}
	}
	
	/**
	 * Metodo che si occupa di validare le date e di verificare se la data di fine validit� � successiva a quella di inizio
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validaDate(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		UIInput datainizio = (UIInput)component.getAttributes().get("data_inizio");
		Date dataInizio = (Date)datainizio.getValue();
		Date dataFine = (Date)value;
		if (dataFine.before(dataInizio)){
                throw new ValidatorException(new FacesMessage("La data di fine validit�� deve essere successiva a quella di inizio"));
        }
	}
	
	/**
	 * Metodo che valida l'id se non � gi� utilizzato 
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validaId(FacesContext context,UIComponent component,Object value) throws ValidatorException{
        if (gestionePacchetto.esisteIdPacchetto(value.toString())){
                throw new ValidatorException(new FacesMessage("Id gi�� utilizzato. Scegline un altro"));
        }
	}
	
	
	/**
	 * Metodo che si occupa di caricare le destinazioni
	 */

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

	/**
	 * Metodo che verifica se gli aerei rispettano determinati vincoli 
	 * @return
	 */
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

	/**
	 * Metodo che rispetta se le modifiche fatte verificano i vincoli imposti
	 * @return
	 */
	private boolean validaModifiche(){
		return validaAerei() && listaHotel.size()>0 && listaEscursioni.size()>=0;
	}
	
	/**
	 * Metodo che si occupa di resettare le selezioni
	 */
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

	/**
	 * Metodo che si occupa di caricare la lista delle escursioni
	 */
	private void loadListaEscursioni(){
		ArrayList<String> escursioni = new ArrayList<String>();
		for(EscursioneDTO escursione:listaEscursioniDB){
			Integer id = new Integer(escursione.getId());
			escursioni.add(id.toString());
		}
		ArrayList<String> escursioniTarget = new ArrayList<String>();
		setListaSelezioneEscursioni(new DualListModel<>(escursioni, escursioniTarget));
	}
	
	
	/*
	 * Metodi getter e setter dei dati e dell'interfaccia 
	 */
	

	private void setSelezioneEscursioni(){
		boolean trovato = false;
		List<String> selezioneEscursioni = listaSelezioneEscursioni.getTarget();
		ArrayList<EscursioneDTO> escursioni = new ArrayList<EscursioneDTO>();
		for(String selezione:selezioneEscursioni){
			trovato = false;
			for(int i=0; i<listaEscursioniDB.size() & !trovato;i++){
				Integer idValue = new Integer(listaEscursioniDB.get(i).getId());
				if(idValue.toString().equals(selezione)){
					escursioni.add(listaEscursioniDB.get(i));
					trovato = true;
					costo = costo + listaEscursioniDB.get(i).getPrezzo();
				}
			}
		}
		prenotazione.setEscursioni(escursioni);		
	}

	private void setSelezioneAerei(){
		boolean aereoTrovato= false;
		for(int i=0;i<listaAereiAndataDB.size() & !aereoTrovato; i++){
			AereoDTO aereo = listaAereiAndataDB.get(i);
			System.out.println("i " +i);
			System.out.println("aereo andata "+aereo+" id aereo ="+idAereoAndata);
			if (aereo.toString().equals(idAereoAndata)){
				prenotazione.setAereo(aereo);
				aereoTrovato = true;
				costo = costo + aereo.getCosto();
			}
		}
		System.out.println("Aereo andata "+prenotazione.getAereoAndata());
		aereoTrovato = false;
		for(int i=0;i<listaAereiRitornoDB.size() & !aereoTrovato; i++){
			AereoDTO aereo = listaAereiRitornoDB.get(i);
			if (aereo.toString().equals(idAereoRitorno)){
				prenotazione.setAereoRitorno(aereo);
				aereoTrovato = true;
				costo = costo + aereo.getCosto();
			}
		}
		
	}
	
	
	private void setSelezioneHotel(){
		boolean hotelTrovato = false;
		for(int i=0;i<listaHotelDB.size() & !hotelTrovato; i++){
			HotelDTO hotel = listaHotelDB.get(i);
			if (hotel.toString().equals(idHotel)){
				prenotazione.setHotel(hotel);
				hotelTrovato = true;
				
			}
		}
	}

	public void messaggioCheckBox(){
		String summary;
		if(checkCondivisione){
			Date date = new Date();
			summary = "Condivisione Prenotazione Attivata";
			linkCondivisione = "cond"+pacchetto.getId()+"user"+gestioneUtente.getUtenteDTO().getUsername()+
					"date"+date.getYear()+date.getMonth()+date.getDay();
		}
		else{
			summary = "Condivisione Prenotazione Disattivata";
			linkCondivisione =null;
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
	}
	
	public void messaggioCheckBoxCondizione(){
		String summary;
		if(checkCondizione){
			summary = "Condizioni di viaggio Accettate";
		}
		else{
			summary = "Condizioni di viaggio non accettate";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
	}
	
	

	public void listenerData(){
		if(ritorno == null || partenza == null){
			String message = "Seleziona sia la data della partenza che del ritorno";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return;
		}
		if(numero == null){
			String message = "Seleziona il numero di persone";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return;
		}
		ArrayList<AereoDTO> listaAndata = new ArrayList<AereoDTO>();
		Integer value = new Integer(numero);
		List<AereoDTO> aereiPossibili = gestionePacchetto.getListaAereiAndataDisp(partenza, ritorno, pacchetto, value);
		for(AereoDTO aereo:aereiPossibili){
			if((aereo.getData().after(partenza) || aereo.getData().equals(partenza)) && 
					aereo.getData().before(ritorno)){
				listaAndata.add(aereo);
			}
		}
		listaAereiAndataDB = listaAndata;
		ArrayList<AereoDTO> listaRitorno = new ArrayList<AereoDTO>();
		aereiPossibili = gestionePacchetto.getListaAereiRitornoDisp(partenza, ritorno, pacchetto, value);
		for(AereoDTO aereo:aereiPossibili){
			if(aereo.getData().after(partenza) && 
					(aereo.getData().before(ritorno) || aereo.getData().equals(ritorno))){
				listaRitorno.add(aereo);
			}
		}
		listaAereiRitornoDB = listaRitorno;
		ArrayList<EscursioneDTO> lista = new ArrayList<EscursioneDTO>();
		for(EscursioneDTO escursione:pacchetto.getEscursioni()){
			if((escursione.getData().after(partenza)) && escursione.getData().before(ritorno)){
				lista.add(escursione);
			}
		}
		listaEscursioniDB = lista;
		loadListaEscursioni();
		List<HotelDTO> hotelDisponibili = gestionePacchetto.getListaHotelDip(partenza,ritorno,pacchetto);
		listaHotelDB = hotelDisponibili;
		String message = "Selezionate le date e il numero di persone";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}
	
	private void resetSelezione(){
		ritorno = null;
		partenza = null;
		numero = null;
		idAereoAndata = null;
		idAereoRitorno = null;
		idHotel = null;
		listaSelezioneEscursioni = null;
		checkCondivisione = false;
		checkCondizione = false;
	}
	public Date getRitorno() {
		return ritorno;
	}

	public void setRitorno(Date ritorno) {
		this.ritorno = ritorno;
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

	public void setListaPacchettiSelezionati(List<PacchettoDTO> listaPacchettiSelezionati) {
		this.listaPacchettiSelezionati = listaPacchettiSelezionati;
	}

	public PacchettoDataModel getDatiPacchetti() {
		return datiPacchetti;
	}

	public void setDatiPacchetti(PacchettoDataModel datiPacchetti) {
		this.datiPacchetti = datiPacchetti;
	}
	
	public Date getPartenza() {
		return partenza;
	}

	public void setPartenza(Date partenza) {
		this.partenza = partenza;
	}
	
	public CondivisioneDTO getCondivisione() {
		return condivisione;
	}

	public void setCondivisione(CondivisioneDTO condivisione) {
		this.condivisione = condivisione;
	}

	public boolean isCheckCondivisione() {
		return checkCondivisione;
	}

	public void setCheckCondivisione(boolean checkCondivisione) {
		this.checkCondivisione = checkCondivisione;
	}

	public String getLinkCondivisione() {
		return linkCondivisione;
	}

	public void setLinkCondivisione(String linkCondivisione) {
		this.linkCondivisione = linkCondivisione;
	}
	
	public String getIdAereoAndata() {
		return idAereoAndata;
	}

	public void setIdAereoAndata(String idAereoAndata) {
		this.idAereoAndata = idAereoAndata;
	}

	public String getIdAereoRitorno() {
		return idAereoRitorno;
	}

	public void setIdAereoRitorno(String idAereoRitorno) {
		this.idAereoRitorno = idAereoRitorno;
	}

	public String getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(String idHotel) {
		this.idHotel = idHotel;
	}
	
	public Prenotazione_PacchettoDTO getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione_PacchettoDTO prenotazione) {
		this.prenotazione = prenotazione;
	}

	public DualListModel<String> getListaSelezioneEscursioni() {
		return listaSelezioneEscursioni;
	}

	public void setListaSelezioneEscursioni(DualListModel<String> listaSelezioneEscursioni) {
		this.listaSelezioneEscursioni = listaSelezioneEscursioni;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public SelectItem[] getDestinazioni() {
		return destinazioni;
	}

	public void setDestinazioni(SelectItem[] destinazioni) {
		this.destinazioni = destinazioni;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public PacchettoDataModel getDatiPacchettiCasuali() {
		return datiPacchettiCasuali;
	}
	public String getNumero() {
		return numero;
	}

	public void setDatiPacchettiCasuali(PacchettoDataModel datiPacchettiCasuali) {
		this.datiPacchettiCasuali = datiPacchettiCasuali;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<String> getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(List<String> numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public Date getDataOdierna() {
		return dataOdierna;
	}

	public void setDataOdierna(Date dataOdierna) {
		this.dataOdierna = dataOdierna;
	}

	public boolean isModifica() {
		return modifica;
	}

	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}

	public int getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(int idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public boolean isCheckCondizione() {
		return checkCondizione;
	}

	public void setCheckCondizione(boolean checkCondizione) {
		this.checkCondizione = checkCondizione;
	}

	}

