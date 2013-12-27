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
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public void modificaProfilo(UtenteDTO utente, AnagraficaDTO anagrafica) {
		Anagrafica modifiche = new Anagrafica(anagrafica);
		em.merge(modifiche);
		em.merge(new Utente(utente, modifiche));
	}


	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public void eliminaProfilo() {
		rimuovi(getUtenteAttivo());
	}
	
	@Override
	@RolesAllowed({"UTENTE","AMMINISTRATORE","DIPENDENTE"})
	public UtenteDTO getUtenteDTO() {
		UtenteDTO userDTO = convertToDTO(getUtenteAttivo());
		return userDTO;
		
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
		return dto;
	}

	

	   
	

}
