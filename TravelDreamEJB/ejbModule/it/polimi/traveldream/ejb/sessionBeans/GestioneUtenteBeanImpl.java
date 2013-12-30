package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

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
	public void aggiungiNuovoUtente(UtenteDTO utente) {
		Anagrafica nuovaAnagrafica = new Anagrafica(utente);
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
	public void modificaProfiloUtente(UtenteDTO utente) {
		Anagrafica modifiche = new Anagrafica(utente);
		em.merge(modifiche);
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
	
	/*
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public AnagraficaDTO getAnagraficaDTO(String idAnagrafica) {
		Anagrafica anag = em.find(Anagrafica.class, idAnagrafica);
		
		return convertToAnagraficaDTO(anag);
	}
*/
	
	
    public Utente cerca(String username) {
    	return em.find(Utente.class, username);
    }
    
    @Override
	@RolesAllowed({"AMMINISTRATORE","DIPENDENTE"})
    public List<UtenteDTO> getListaUtenti() {
    	List<Utente> utentiDB = em.createNamedQuery("Utente.findAll", Utente.class)
                .getResultList();
    	ArrayList<UtenteDTO> utenti = new ArrayList<UtenteDTO>();
    	for(int i=0; i<utentiDB.size();i++){
    		UtenteDTO user = convertToDTO(utentiDB.get(i));
    		utenti.add(user);
    	}
    	List<UtenteDTO> listaUtenti = utenti;
    	return listaUtenti;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @RolesAllowed({"AMMINISTRATORE","DIPENDENTE"})
	public List<UtenteDTO> getListaUtentiBase() {
    	List<Utente> utentiDB = em.createQuery("SELECT u FROM Utente u, IN (u.gruppi) g WHERE g.nome =:nome")
			    .setParameter("nome", "UTENTE").getResultList();
    	ArrayList<UtenteDTO> utenti = new ArrayList<UtenteDTO>();
    	for(int i=0; i<utentiDB.size();i++){
    		UtenteDTO user = convertToDTO(utentiDB.get(i));
    		utenti.add(user);
    	}
    	List<UtenteDTO> listaUtenti = utenti;
    	return listaUtenti;
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
		dto.setCodiceFiscale(utente.getAnagrafica().getCf());
		Anagrafica anag = em.find(Anagrafica.class, utente.getAnagrafica().getCf());
		dto = convertToAnagraficaDTO(anag,dto);
		return dto;
	}
    
    private UtenteDTO convertToAnagraficaDTO(Anagrafica anagrafica, UtenteDTO nuovo) {
		
		nuovo.setCognome(anagrafica.getCognome());
		nuovo.setDataNascita(anagrafica.getData_Nascita());
		nuovo.setLuogoNascita(anagrafica.getLuogo_Nascita());
		nuovo.setNome(anagrafica.getNome());
		nuovo.setResidenza(anagrafica.getResidenza());
		return nuovo;
	}


	








	




	

	   
	

}
