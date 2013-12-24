package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AnagraficaDTO;
import it.polimi.traveldream.ejb.dto.UtenteDTO;

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
		
		
	}

}
