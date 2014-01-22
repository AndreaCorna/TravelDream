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
       
    }
    
    /**
     * Metodo che dato un utente lo rende dipendente
     */
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
	
	/**
	 * Metodo che permette di restituire la lista dei dipendenti 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({"AMMINISTRATORE"})
	public List<UtenteDTO> getListaDipendenti() {
		List<Utente> dipendentiDB = em.createQuery("SELECT u FROM Utente u, IN (u.gruppi) g WHERE g.nome =:nome")
			    .setParameter("nome", "DIPENDENTE").getResultList();
    	ArrayList<UtenteDTO> dipendenti = new ArrayList<UtenteDTO>();
    	for(int i=0; i<dipendentiDB.size();i++){
    		UtenteDTO user = ConverterDTO.convertToDTO(dipendentiDB.get(i));
    		dipendenti.add(user);
    	}
    	List<UtenteDTO> listaDip = dipendenti;
    	return listaDip;
	}
	
	/**
	 * Metodo che dato un dipendente permette di eliminarlo e quindi di retrocederlo a utente
	 */
	@Override
	@RolesAllowed({"AMMINISTRATORE"})
	public void eliminaDipendente(UtenteDTO utenteDTO) {
		Utente dip = em.find(Utente.class, utenteDTO.getUsername());
		List<Gruppo> gruppi = new ArrayList<Gruppo>();
		Gruppo nuovoGruppo = new Gruppo();
		nuovoGruppo.setNome("UTENTE");
		gruppi.add(nuovoGruppo);
		dip.setGruppi(gruppi);
		em.merge(dip);
		
	}


}
