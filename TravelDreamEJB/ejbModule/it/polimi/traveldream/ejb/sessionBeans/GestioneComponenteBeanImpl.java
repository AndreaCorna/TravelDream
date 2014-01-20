package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class GestioneComponenteBeanImpl
 */
@Stateless
public class GestioneComponenteBeanImpl implements GestioneComponenteBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public GestioneComponenteBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiAereoDB(AereoDTO aereo) {
		Aereo nuovo = new Aereo(aereo);
		em.persist(nuovo);
		em.flush();
		nuovo = em.find(Aereo.class, nuovo.getId());
		aereo.setId(nuovo.getId());
		
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiHotelDB(HotelDTO hotel) {
		Hotel nuovo = new Hotel(hotel);
		em.persist(nuovo);
		em.flush();
		nuovo = em.find(Hotel.class, nuovo.getId());
		hotel.setId(nuovo.getId());
		
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiEscursioneDB(EscursioneDTO escursione) {
		Escursione nuovo = new Escursione(escursione);
		em.persist(nuovo);
		em.flush();
		nuovo = em.find(Escursione.class,nuovo.getId());
		escursione.setId(nuovo.getId());
		
	}
	
	

	@Override
	public boolean esisteAereo(String id) {
		Integer num = new Integer(id);
		if (em.find(Aereo.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	@Override
	public boolean esisteHotel(String id) {
		Integer num = new Integer(id);
		if (em.find(Hotel.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	@Override
	public boolean esisteEscursione(String id) {
		Integer num = new Integer(id);
		if (em.find(Escursione.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	@Override
	public AereoDTO getAereoById(String id) {
		Integer value = new Integer(id);
		Aereo aereo = em.find(Aereo.class, value.intValue());
		return ConverterDTO.convertToDTO(aereo);
	}
	
	@Override
	public HotelDTO getHotelById(String id) {
		Integer value = new Integer(id);
		Hotel hotel = em.find(Hotel.class, value.intValue());
		return ConverterDTO.convertToDTO(hotel);
	}
	
	@Override
	public EscursioneDTO getEscursioneById(String id) {
		Integer value = new Integer(id);
		Escursione escursione = em.find(Escursione.class, value.intValue());
		return ConverterDTO.convertToDTO(escursione);
	}

	

	

	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaAereo(AereoDTO aereo) {
		Aereo modificato = em.find(Aereo.class, aereo.getId());
		modificato.setAtterraggio(aereo.getCittaAtterraggio());
		modificato.setCosto(aereo.getCosto());
		modificato.setData(aereo.getData());
		modificato.setDecollo(aereo.getCittaDecollo());
		modificato.setPosti_Disponibili(aereo.getPostiDisponibili());
		em.merge(modificato);
		aggiornaPacchetti(modificato);
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaHotel(HotelDTO hotel) {
		Hotel modificato = em.find(Hotel.class, hotel.getId());
		//setCamere_Disponibili(hotel.getCamereDisponibili());
		modificato.setCitta(hotel.getCitta());
		modificato.setNome(hotel.getNome());
		modificato.setStelle(hotel.getRating().intValue());
		modificato.setCostoGiornaliero(hotel.getCostoGiornaliero());
		modificato.setDataCheckOut(hotel.getDataFine());
		modificato.setDataCheckIn(hotel.getDataInizio());
		em.merge(modificato);
		//aggiornaPacchetti(modificato);
	}
	
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaEscursione(EscursioneDTO escursione) {
		Escursione modificato = em.find(Escursione.class, escursione.getId());
		modificato.setData(escursione.getData());
		modificato.setDescrizione(escursione.getDescrizione());
		modificato.setLuogo(escursione.getLuogo());
		modificato.setPrezzo(escursione.getPrezzo());
		em.merge(modificato);
		aggiornaPacchetti(modificato);
		
	}
	
	@Override
	public void eliminaAereo(AereoDTO aereo) {
		Aereo aereoDB = em.find(Aereo.class, aereo.getId());
		aereoDB.setValido((byte)0);
		em.merge(aereoDB);
		
	}
/*	
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
		//nuovo.setCamereDisponibili(hotel.getCamere_Disponibili());
		nuovo.setId(hotel.getId());
		nuovo.setCitta(hotel.getCitta());
		nuovo.setNome(hotel.getNome());
		Integer value = new Integer(hotel.getStelle());
		nuovo.setRating(value);
		nuovo.setCostoGiornaliero(hotel.getCostoGiornaliero());
		nuovo.setDataFine(hotel.getDataCheckOut());
		nuovo.setDataInizio(hotel.getDataCheckIn());
		ArrayList<CameraDTO> camere = new ArrayList<CameraDTO>();
		for(Camera camera:hotel.getCamere()){
			camere.add(ConverterDTO.convertToDTO(camera));
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
	*/
	@SuppressWarnings("unchecked")
	private void aggiornaPacchetti(Aereo aereo){
		List<Pacchetto> pacchetti = em.createQuery("SELECT p FROM Pacchetto p, IN (p.aerei) a WHERE a=:nome")
			    .setParameter("nome",aereo).getResultList();
		for(Pacchetto pacchetto:pacchetti){
			if(aereo.getData().before(pacchetto.getInizio_Validita()) ||
					aereo.getData().after(pacchetto.getFine_Validita()) || aereo.getValido()==0){
				pacchetto.getAerei().remove(aereo);
				if(!conRitornoAndata(pacchetto)){
					pacchetto.setValido((byte)0);
					em.merge(pacchetto);
				}
			}
		}
   
	}
	
	@SuppressWarnings({"unchecked" })
	private void aggiornaPacchetti(Escursione escursione){
		List<Pacchetto> pacchetti = em.createQuery("SELECT p FROM Pacchetto p, IN (p.escursioni) e WHERE e=:nome")
			    .setParameter("nome",escursione).getResultList();
		for(Pacchetto pacchetto:pacchetti){
			if(escursione.getData().before(pacchetto.getInizio_Validita()) ||
					escursione.getData().after(pacchetto.getFine_Validita())){
				pacchetto.getAerei().remove(escursione);
				if(pacchetto.getEscursioni().size() == 0){
					pacchetto.setValido((byte)0);
					em.merge(pacchetto);
				}
			}
		}
	}
	
		
	private boolean conRitornoAndata(Pacchetto pacchetto){
		boolean andata = false;
		boolean ritorno = false;
		List<Aereo> lista = pacchetto.getAerei();
		for(int i=0;i<lista.size() && (!andata || !ritorno);i++){
			if( lista.get(i).getAtterraggio().equals(pacchetto.getDestinazione())){
				andata = true;
			}
			else if( lista.get(i).getDecollo().equals(pacchetto.getDestinazione())){
				ritorno = true;
			}
		}
		return andata & ritorno;
	}

	
	
	



}
