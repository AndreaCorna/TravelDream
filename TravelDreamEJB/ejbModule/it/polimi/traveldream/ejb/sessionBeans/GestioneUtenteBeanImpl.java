package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.ejb.dto.AnagraficaDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Anagrafica;
import it.polimi.traveldream.ejb.entities.Gruppo;
import it.polimi.traveldream.ejb.entities.Utente;

import javax.annotation.Resource;
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
	public boolean esisteUsername(String value) {
		if (em.find(Utente.class,value)!=null){
           return true;
        }
        return false;
	}

    
	

}
