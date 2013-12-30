package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.entities.Aereo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class GestionePacchettoBeanImpl
 */
@Stateless
public class GestionePacchettoBeanImpl implements GestionePacchettoBean {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
    /**
     * Default constructor. 
     */
    public GestionePacchettoBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@RolesAllowed({"DIPENDENTE"})
	public List<AereoDTO> getListaAerei() {
		List<Aereo> aereiDB = em.createNamedQuery("Aereo.findAll", Aereo.class).getResultList();
		ArrayList<AereoDTO> aerei = new ArrayList<AereoDTO>();
    	for(int i=0; i<aereiDB.size();i++){
    		AereoDTO aereo = convertToDTO(aereiDB.get(i));
    		aerei.add(aereo);
    	}
    	List<AereoDTO> listaAerei = aerei;
    	return listaAerei;
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

}
