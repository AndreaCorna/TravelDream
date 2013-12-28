package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.ejb.dto.AnagraficaDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Anagrafica;
import it.polimi.traveldream.ejb.entities.Gruppo;
import it.polimi.traveldream.ejb.entities.Utente;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class GestioneUtenteBeanImpl
 */
@Stateless
public class GestioneUtenteBeanImpl implements GestioneUtenteBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    
	
    @Override
	public void aggiungiNuovoUtente(UtenteDTO utente, AnagraficaDTO anagrafica) {
		Anagrafica nuovaAnagrafica = new Anagrafica(anagrafica);
		em.persist(nuovaAnagrafica);
		Utente nuovoUtente = new Utente(utente, nuovaAnagrafica);
		List<Gruppo> gruppi = new ArrayList<Gruppo>();
		Gruppo nuovoGruppo = new Gruppo();
		nuovoGruppo.setNome("UTENTE");
		gruppi.add(nuovoGruppo);
		nuovoUtente.setGruppi(gruppi);
		em.persist(nuovoUtente);
		
	}


	@Override
	public boolean esisteUsername(String username) {
		if (em.find(Utente.class,username)!=null){
           return true;
        }
        return false;
	}
	
	@Override
	public boolean esisteCodiceFiscale(String codiceFiscale) {
		if (em.find(Anagrafica.class, codiceFiscale)!=null){
			return true;
		}
		return false;
	}


	
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public void modificaProfiloUtente(UtenteDTO utente, AnagraficaDTO anagrafica) {
		Anagrafica modifiche = new Anagrafica(anagrafica);
		Utente vecchio = em.find(Utente.class, utente.getUsername());
		Utente nuovo = new Utente(utente, modifiche);
		nuovo.setAmministratoreCreatore(vecchio.getAmministratoreCreatore());
		nuovo.setCondivisioni(vecchio.getCondivisioni());
		nuovo.setDipendentiAggiunti(vecchio.getDipendentiAggiunti());
		nuovo.setPrenotazioniPacchetti(vecchio.getPrenotazioniPacchetti());
		nuovo.setPrenotazioniViaggi(vecchio.getPrenotazioniViaggi());
		nuovo.setGruppi(vecchio.getGruppi());
		em.merge(nuovo);
		
	}


	
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public void eliminaProfilo(String cf) {
		Anagrafica old = em.find(Anagrafica.class, cf);
		rimuovi(getUtenteAttivo());
		em.remove(old);
	}
	
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public UtenteDTO getUtenteDTO() {
		UtenteDTO userDTO = convertToDTO(getUtenteAttivo());
		return userDTO;
		
	}
	
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public AnagraficaDTO getAnagraficaDTO(String idAnagrafica) {
		Anagrafica anag = em.find(Anagrafica.class, idAnagrafica);
		
		return convertToAnagraficaDTO(anag);
	}

	
	   
    public Utente cerca(String username) {
    	return em.find(Utente.class, username);
    }
    
    public List<Utente> getListaUtenti() {
    	return em.createNamedQuery("Utente.findAll", Utente.class)
                .getResultList();
    }

    public void rimuovi(String username) {
		Utente utente = cerca(username);
        em.remove(utente);
	}
    
    public void rimuovi(Utente utente) {
    	em.remove(utente);
	}
    
    
    public Utente getUtenteAttivo() {
    	return cerca(getUsernameAttivo());
    }
	
    
    public String getUsernameAttivo() {
    	return context.getCallerPrincipal().getName();
    }

    private UtenteDTO convertToDTO(Utente utente) {
		UtenteDTO dto = new UtenteDTO();
		dto.setUsername(utente.getUsername());
		dto.setTelefono(utente.getTelefono());
		dto.setEmail(utente.getEmail());
		dto.setIdAnagrafica(utente.getAnagrafica().getCf());
		return dto;
	}
    
    private AnagraficaDTO convertToAnagraficaDTO(Anagrafica anagrafica) {
		AnagraficaDTO nuovo = new AnagraficaDTO();
		nuovo.setCodiceFiscale(anagrafica.getCf());
		nuovo.setCognome(anagrafica.getCognome());
		nuovo.setDataNascita(anagrafica.getData_Nascita());
		nuovo.setLuogoNascita(anagrafica.getLuogo_Nascita());
		nuovo.setNome(anagrafica.getNome());
		nuovo.setResidenza(anagrafica.getResidenza());
		return nuovo;
	}





	




	

	   
	

}
