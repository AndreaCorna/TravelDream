package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.EscursioneDTO;
import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;
import it.polimi.traveldream.ejb.entities.Aereo;
import it.polimi.traveldream.ejb.entities.Escursione;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.Pacchetto;
import it.polimi.traveldream.ejb.entities.Prenotazione_Pacchetto;
import it.polimi.traveldream.ejb.entities.Utente;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@Override
	public void inserisciPrenotazionePacchetto(Prenotazione_PacchettoDTO prenotazione) {
		Prenotazione_Pacchetto nuova = new Prenotazione_Pacchetto();
		nuova.setAereo1(em.find(Aereo.class,prenotazione.getAereoAndata().getId()));
		nuova.setAereo2(em.find(Aereo.class,prenotazione.getAereoRitorno().getId()));
		nuova.setData(prenotazione.getData());
		nuova.setHotel(em.find(Hotel.class,prenotazione.getHotel().getId()));
		nuova.setPacchetto(em.find(Pacchetto.class,prenotazione.getPacchetto().getId()));
		nuova.setUtente(em.find(Utente.class, prenotazione.getUtente().getUsername()));
		nuova.setEscursioni(convertListaEscursioni(prenotazione.getEscursioni()));
		nuova.setDataCheckInHotel(prenotazione.getCheckInHotel());
		nuova.setDataCheckOutHotel(prenotazione.getCheckOutHotel());
		em.persist(nuova);
		em.flush();
		nuova = em.find(Prenotazione_Pacchetto.class, nuova.getId());
		prenotazione.setId(nuova.getId());
		
	}
	
	private List<Escursione> convertListaEscursioni(List<EscursioneDTO> lista){
		ArrayList<Escursione> listaEscursioni = new ArrayList<Escursione>();
		for(int i=0;i<lista.size();i++){
			Escursione nuova = new Escursione(lista.get(i));
			listaEscursioni.add(nuova);
		}
		List<Escursione> escursioni = listaEscursioni;
		return escursioni;
	}
	

}
