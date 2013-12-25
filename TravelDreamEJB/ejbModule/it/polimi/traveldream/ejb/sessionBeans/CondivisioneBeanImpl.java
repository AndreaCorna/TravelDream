package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.CondivisioneDTO;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class CondivisioneBeanImpl
 */
@Stateless
public class CondivisioneBeanImpl implements CondivisioneBean {

    /**
     * Default constructor. 
     */
    public CondivisioneBeanImpl() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public CondivisioneDTO mostraCondivisione(String link) {
		CondivisioneDTO condivisione = new CondivisioneDTO();
		
		return condivisione;
		
		
	}

}
