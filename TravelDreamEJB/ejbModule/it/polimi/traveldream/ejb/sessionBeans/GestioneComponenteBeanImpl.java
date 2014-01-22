package it.polimi.traveldream.ejb.sessionBeans;

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

    /**
     * metodo che dato l'aereo permette di aggiungerlo al database
     */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiAereoDB(AereoDTO aereo) {
		Aereo nuovo = new Aereo(aereo);
		em.persist(nuovo);
		em.flush();
		nuovo = em.find(Aereo.class, nuovo.getId());
		aereo.setId(nuovo.getId());
		
	}
	
	/**
	 * Metodo che dato l'hotel permette di aggiungerlo al database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiHotelDB(HotelDTO hotel) {
		Hotel nuovo = new Hotel(hotel);
		nuovo.setValido((byte)1);
		em.persist(nuovo);
		em.flush();
		nuovo = em.find(Hotel.class, nuovo.getId());
		hotel.setId(nuovo.getId());
		
	}
	
	/**
	 * Metodo che data l'escursione permette di aggiungerla al database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void aggiungiEscursioneDB(EscursioneDTO escursione) {
		Escursione nuovo = new Escursione(escursione);
		nuovo.setValido((byte)1);
		em.persist(nuovo);
		em.flush();
		nuovo = em.find(Escursione.class,nuovo.getId());
		escursione.setId(nuovo.getId());
		
	}
	
	/**
	 * metodo che dato l'id di un aereo permette di restituire un booleano che è true se 
	 * l'aereo esiste nel database false altrimenti
	 */

	@Override
	public boolean esisteAereo(String id) {
		Integer num = new Integer(id);
		if (em.find(Aereo.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	/**
	 * metodo che dato l'id di un hotel permette di restituire un booleano che è true se 
	 * l'hotel esiste nel database false altrimenti
	 */
	@Override
	public boolean esisteHotel(String id) {
		Integer num = new Integer(id);
		if (em.find(Hotel.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	/**
	 * metodo che dato l'id di un'escursione permette di restituire un booleano che è true se 
	 * l'escursione esiste nel database false altrimenti
	 */
	@Override
	public boolean esisteEscursione(String id) {
		Integer num = new Integer(id);
		if (em.find(Escursione.class,num.intValue())!=null){
	           return true;
	    }
	   return false;
	}

	/**
	 * Metodo che permette dato l'id di un aereo di restituire l'aereo cercato nel database
	 */
	@Override
	public AereoDTO getAereoById(String id) {
		Integer value = new Integer(id);
		Aereo aereo = em.find(Aereo.class, value.intValue());
		return ConverterDTO.convertToDTO(aereo);
	}
	
	/**
	 * Metodo che permette dato l'id di un hotel di restituire l'hotel cercato nel database
	 */
	@Override
	public HotelDTO getHotelById(String id) {
		Integer value = new Integer(id);
		Hotel hotel = em.find(Hotel.class, value.intValue());
		return ConverterDTO.convertToDTO(hotel);
	}
	
	/**
	 * Metodo che permette dato l'id di un'escursione di ritornare l'escursione cercata nel database
	 */
	@Override
	public EscursioneDTO getEscursioneById(String id) {
		Integer value = new Integer(id);
		Escursione escursione = em.find(Escursione.class, value.intValue());
		return ConverterDTO.convertToDTO(escursione);
	}

	
	/**
	 * Metodo che prende un aereo che è stato modificato e si occupa di integrare le modifiche
	 *  all'interno del database
	 */
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

	/**
	 * Metodo che prende un hotel che è stato modificato e si occupa di integrare le modifiche
	 *  all'interno del database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void modificaHotel(HotelDTO hotel) {
		Hotel modificato = em.find(Hotel.class, hotel.getId());
		modificato.setCamere_Disponibili(hotel.getCamereDisponibili());
		modificato.setCitta(hotel.getCitta());
		modificato.setNome(hotel.getNome());
		modificato.setStelle(hotel.getRating().intValue());
		modificato.setCostoGiornaliero(hotel.getCostoGiornaliero());
		em.merge(modificato);
		//aggiornaPacchetti(modificato);
	}
	
	/**
	 * Metodo che prende un'escursione che è stata modificata e si occupa di integrare le modifiche
	 *  all'interno del database
	 */
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
	
	/**
	 * Metodo che prende un aereo e permette di eliminarlo dal database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void eliminaAereo(AereoDTO aereo) {
		Aereo aereoDB = em.find(Aereo.class, aereo.getId());
		aereoDB.setValido((byte)0);
		em.merge(aereoDB);
		aggiornaPacchetti(aereoDB);
		
	}
	
	/**
	 * Metodo che prende un hotel e permette di eliminarlo dal database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void eliminaHotel(HotelDTO hotel) {
		Hotel hotelDB = em.find(Hotel.class, hotel.getId());
		hotelDB.setValido((byte)0);
		em.merge(hotelDB);
		aggiornaPacchetti(hotelDB);
	}

	/**
	 * Metodo che prende un'escursione e permette di eliminarla dal database
	 */
	@Override
	@RolesAllowed({"DIPENDENTE"})
	public void eliminaEscursione(EscursioneDTO escursione) {
		Escursione escursioneDB = em.find(Escursione.class, escursione.getId());
		escursioneDB.setValido((byte)0);
		em.merge(escursioneDB);
		aggiornaPacchetti(escursioneDB);
		
	}


	/**
	 * Metodo che permette di aggiornare i pacchetti contenenti un dato aereo
	 * @param aereo
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
	

	/**
	 * Metodo che permette di aggiornare i pacchetti contenenti un datoa data escursione
	 * @param aereo
	 */
	@SuppressWarnings({"unchecked" })
	private void aggiornaPacchetti(Escursione escursione){
		List<Pacchetto> pacchetti = em.createQuery("SELECT p FROM Pacchetto p, IN (p.escursioni) e WHERE e=:nome")
			    .setParameter("nome",escursione).getResultList();
		for(Pacchetto pacchetto:pacchetti){
			if(escursione.getData().before(pacchetto.getInizio_Validita()) ||
					escursione.getData().after(pacchetto.getFine_Validita()) || escursione.getValido()==0){
				pacchetto.getEscursioni().remove(escursione);
				if(pacchetto.getEscursioni().size() == 0){
					pacchetto.setValido((byte)0);
					em.merge(pacchetto);
				}
			}
		}
	}
	

	/**
	 * Metodo che permette di aggiornare i pacchetti contenenti un dato hotel
	 * @param aereo
	 */
	@SuppressWarnings({"unchecked" })
	private void aggiornaPacchetti(Hotel hotel){
		List<Pacchetto> pacchetti = em.createQuery("SELECT p FROM Pacchetto p, IN (p.hotels) e WHERE e=:nome")
			    .setParameter("nome",hotel).getResultList();
		for(Pacchetto pacchetto:pacchetti){
			if(hotel.getValido()==0){
				pacchetto.getAerei().remove(hotel);
				if(pacchetto.getHotels().size() == 0){
					pacchetto.setValido((byte)0);
					em.merge(pacchetto);
				}
			}
		}
	}
	
	/**
	 * Metodo che restituisce se un dato pacchetto è provvisto sia di aereo di andata che di ritorno
	 * @param pacchetto
	 * @return
	 */
	private boolean conRitornoAndata(Pacchetto pacchetto){
		boolean andata = false;
		boolean ritorno = false;
		List<Aereo> lista = pacchetto.getAerei();
		for(int i=0;i<lista.size() && (!andata || !ritorno);i++){
			if( lista.get(i).getAtterraggio().equals(pacchetto.getDestinazione())
					&& lista.get(i).getValido()==1){
				andata = true;
			}
			else if( lista.get(i).getDecollo().equals(pacchetto.getDestinazione())
						&& lista.get(i).getValido()==1){
				ritorno = true;
			}
		}
		return andata & ritorno;
	}

	

	
	
	



}
