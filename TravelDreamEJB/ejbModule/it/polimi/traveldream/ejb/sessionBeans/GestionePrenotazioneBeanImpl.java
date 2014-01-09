package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.CameraDTO;
import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.PacchettoDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Camera;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 * Session Bean implementation class GestionePrenotazioneImpl
 */
@Stateless
public class GestionePrenotazioneBeanImpl implements it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean {


	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
    /**
     * Default constructor. 
     */
    public GestionePrenotazioneBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Prenotazione_PacchettoDTO> getPrenotazioni() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<AereoDTO> getListaAereiAndata(String cittaAtterraggio, Date inizioValidita, Date fineValidita) {
		List<Aereo> aerei = em.createQuery("SELECT a FROM Aereo a WHERE a.atterraggio =:nome and a.data BETWEEN :startDate AND :endDate")
			    .setParameter("nome", cittaAtterraggio)
			    .setParameter("startDate", inizioValidita, TemporalType.TIMESTAMP)
			    .setParameter("endDate", fineValidita, TemporalType.TIMESTAMP)
			    .getResultList();
		List<AereoDTO> listaAerei = convertListaAereiAndataToDTO(aerei, cittaAtterraggio);
		return listaAerei;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"DIPENDENTE","UTENTE"})
	public List<Prenotazione_PacchettoDTO> getListaPrenotazioni() {
		String idUtenteOnline = context.getCallerPrincipal().getName();
		List<Prenotazione_Pacchetto> prenotazioniUtente = em.createQuery("SELECT a FROM Prenotazione_Pacchetto a WHERE id_Utente =:nome")
			    .setParameter("nome", idUtenteOnline)
			    .getResultList();
		List<Prenotazione_PacchettoDTO> listaUtenti = convertListaUtentiOnlineToDTO(prenotazioniUtente, idUtenteOnline);
		return listaUtenti;
	}
	
	

	
	/*
	 * Metodi privati per la conversione di un oggetto prelevato dal database in un oggetto che verr√† inviato alla view.
	 */
	
	private List<Prenotazione_PacchettoDTO> convertListaUtentiOnlineToDTO(List<Prenotazione_Pacchetto> lista, String idUtentiOnline){
		ArrayList<AereoDTO> listaAereiAndata = new ArrayList<AereoDTO>();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getAtterraggio().toLowerCase().equals(idUtentiOnline.toLowerCase())){
				AereoDTO nuovo = convertToDTO(lista.get(i));
				listaAereiAndata.add(nuovo);
			}
		}
		List<AereoDTO> aerei = listaAereiAndata;
		return aerei;
	}
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
		private Prenotazione_PacchettoDTO convertToDTO(Prenotazione_Pacchetto prenotazione){
			Prenotazione_PacchettoDTO nuovo = new Prenotazione_PacchettoDTO();
			nuovo.setId(prenotazione.getId());
			nuovo.setData(prenotazione.getData());
			nuovo.setCondivisioni(prenotazione.getCondivisioni());
			nuovo.setEscursioni(convertListaEscursioniToDTO(prenotazione.getEscursioni()));
			nuovo.setAereo(convertListaAereiToDTO(prenotazione.getAereo1()));
			nuovo.setAereoRitorno(prenotazione.getAereo2());
			nuovo.setHotel(prenotazione.getHotel());
			nuovo.setUtente(prenotazione.getUtente());
			nuovo.setPacchetto(prenotazione.getPacchetto());
			
			return nuovo;
		}

		@Override
		public List<AereoDTO> getAereo(String Citt‡) {
			// TODO Auto-generated method stub
			return null;
		}
		
		//metodi convertTODTO
		private List<EscursioneDTO> convertListaEscursioniToDTO(List<Escursione> lista){
			ArrayList<EscursioneDTO> listaEscursioni = new ArrayList<EscursioneDTO>();
			for(int i=0;i<lista.size();i++){
				EscursioneDTO nuova = convertToDTO(lista.get(i));
				listaEscursioni.add(nuova);
			}
			List<EscursioneDTO> escursioni = listaEscursioni;
			return escursioni;
		}
		
		private List<AereoDTO> convertListaAereiAndataToDTO1(List<Aereo> lista, String destinazionePacchetto){
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
		
		private List<HotelDTO> convertListaHotelToDTO(List<Hotel> lista){
			ArrayList<HotelDTO> listaHotel = new ArrayList<HotelDTO>();
			for(int i=0;i<lista.size();i++){
				HotelDTO nuovo = convertToDTO(lista.get(i));
				listaHotel.add(nuovo);
			}
			List<HotelDTO> hotel = listaHotel;
			return hotel;
		}
		
		private List<PacchettoDTO> convertListaPacchettiToDTO(List<Pacchetto> lista){
			ArrayList<PacchettoDTO> pacchetti = new ArrayList<PacchettoDTO>();
			for (int i=0;i<lista.size();i++){
				pacchetti.add(convertToDTO(lista.get(i)));
			}
			List<PacchettoDTO> listaPacchetti = pacchetti;
			return listaPacchetti;
		}
}
