package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;

import java.util.List;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class GestionePrenotazioneImpl
 */
@Stateless
public class GestionePrenotazioneBeanImpl implements it.polimi.traveldream.ejb.sessionBeans.GestionePrenotazioneBean {

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

}
