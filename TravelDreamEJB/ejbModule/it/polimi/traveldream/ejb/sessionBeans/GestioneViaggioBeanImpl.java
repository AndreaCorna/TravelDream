package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Camera;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 * Session Bean implementation class GestioneViaggioBeanImpl
 */
@Stateless
@LocalBean
public class GestioneViaggioBeanImpl implements GestioneViaggioBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	@EJB
	private GestioneUtenteBean gestioneUtente;
    /**
     * Default constructor. 
     */
    public GestioneViaggioBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void prenotaViaggio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findAll", Aereo.class).getResultList();
	   	List<AereoDTO> listaAerei = convertListaAereiToDTO(aereiDB);
    	return listaAerei;
	}
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<HotelDTO> getListaHotel(){
		List<Hotel> hotels = em.createNamedQuery("Hotel.findAll", Hotel.class).getResultList();
		List<HotelDTO> listaHotels = convertListaHotelToDTO(hotels);
		return listaHotels;
	}

	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<EscursioneDTO> getListaEscursioni() {
		List<Escursione> escursioniDB = em.createNamedQuery("Escursione.findAll", Escursione.class).getResultList();
	   	List<EscursioneDTO> listaEscursioni = convertListaEscursioniToDTO(escursioniDB);
    	return listaEscursioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date andata) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data = startDate")
			    .setParameter("nome", cittaAtterraggio)
			    .setParameter("startDate", andata, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiAndataToDTO(aerei, cittaAtterraggio);
		return listaAerei;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiRitorno(String cittaDecollo, Date partenza) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.decollo =:nome and a.data = startDAte")
			    .setParameter("nome", cittaDecollo)
			    .setParameter("startDate", partenza, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiRitornoToDTO(aerei, cittaDecollo);
		return listaAerei;
	}
	@Override
	public void mostraRiepilogo() {
		// TODO Auto-generated method stub
		
	}

	
	//ELENCO DEI CONVERTLISTA QUALCOSA TO DTO
	private List<AereoDTO> convertListaAereiAndataToDTO(List<Aereo> lista, String destinazionePacchetto){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getAtterraggio().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	
	private List<AereoDTO> convertListaAereiRitornoToDTO(List<Aereo> lista, String destinazionePacchetto){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getDecollo().toLowerCase().equals(destinazionePacchetto.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
	private List<AereoDTO> convertListaAereiToDTO(List<Aereo> lista){
		ArrayList<AereoDTO> listaAerei = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			AereoDTO nuovo = convertToDTO(lista.get(i));
			listaAerei.add(nuovo);
		}
		List<AereoDTO> aerei = listaAerei;
		return aerei;
	}
	
	private List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
		ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
		for(int i=0;i<lista.size();i++){
			EscursioneDTO nuova = convertToDTO(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<EscursioneDTO> escursioni = listaEscursioni;
		return escursioni;
	}
	
	
	private List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
		ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
		for(int i=0;i<lista.size();i++){
			HotelDTO nuovo = convertToDTO(lista.get(i));
			listaHotel.add(nuovo);
		}
		List<HotelDTO> hotel = listaHotel;
		return hotel;
	}
	
	//ELENCO DEI CONVERT TO DTO
	
	private AereoDTO convertToDTO(Aereo aereo){
		AereoDTO nuovo = new AereoDTO();
		nuovo.setCittaAtterraggio(aereo.getAtterraggio());
		nuovo.setCittaDecollo(aereo.getDecollo());
		nuovo.setCosto(aereo.getCosto());
		nuovo.setData(aereo.getData());
		nuovo.setId(aereo.getId());
		nuovo.setPostiDisponibili(aereo.getPosti_Disponibili());
		return nuovo;
	}
	private HotelDTO convertToDTO(Hotel hotel){
		HotelDTO nuovo = new HotelDTO();
		nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCittà());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		ArrayList<CameraDTO> camere = new ArrayList<CameraDTO>();
		for(Camera camera:hotel.getCamere()){
			camere.add(convertToDTO(camera));
		}
		nuovo.setCamere(camere);
		return nuovo;
	}
	
	private CameraDTO convertToDTO(Camera camera){
		CameraDTO nuovo = new CameraDTO();
		nuovo.setCosto(camera.getCosto());
		nuovo.setData_Checkin(camera.getData_Checkin());
		nuovo.setData_Checkout(camera.getData_Checkout());
		nuovo.setId(camera.getId());
		nuovo.setOccupata(camera.getOccupata());
		nuovo.setPosti(camera.getPosti());
		return nuovo;
	}
	
	private EscursioneDTO convertToDTO(Escursione escursione){
		EscursioneDTO nuovo = new EscursioneDTO();
		nuovo.setData(escursione.getData());
		nuovo.setDescrizione(escursione.getDescrizione());
		nuovo.setId(escursione.getId());
		nuovo.setLuogo(escursione.getLuogo());
		nuovo.setPrezzo(escursione.getPrezzo());
		return nuovo;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> aggiungiAereoViaggio(AereoDTO aereoAndata) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data = startDate")
			    .setParameter("nome", aereoAndata.getCittaAtterraggio())
			    .setParameter("startDate", aereoAndata.getData())
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiAndataToDTO(aerei, aereoAndata.getCittaAtterraggio());
		return listaAerei;
	}
}
