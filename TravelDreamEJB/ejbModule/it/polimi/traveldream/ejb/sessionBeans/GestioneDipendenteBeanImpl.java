package it.polimi.traveldream.ejb.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import it.polimi.traveldream.ejb.dto.UtenteDTO;
import it.polimi.traveldream.ejb.entities.Gruppo;
import it.polimi.traveldream.ejb.entities.Utente;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class GestioneDipendenteBeanImpl
 */
@Stateless
public class GestioneDipendenteBeanImpl implements GestioneDipendenteBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public GestioneDipendenteBeanImpl() {
        // TODO Auto-generated constructor stub
    }
	@Override
	@RolesAllowed({"AMMINISTRATORE"})
	public void creaDipendente(UtenteDTO utenteDTO) {
		Utente nuovoDip = em.find(Utente.class, utenteDTO.getUsername());
		List<Gruppo> gruppi = new ArrayList<Gruppo>();
		Gruppo nuovoGruppo = new Gruppo();
		nuovoGruppo.setNome("DIPENDENTE");
		gruppi.add(nuovoGruppo);
		nuovoDip.setGruppi(gruppi);
		Utente amministratore = em.find(Utente.class, context.getCallerPrincipal().getName());
		nuovoDip.setAmministratoreCreatore(amministratore);
		em.merge(nuovoDip);
	}

}
