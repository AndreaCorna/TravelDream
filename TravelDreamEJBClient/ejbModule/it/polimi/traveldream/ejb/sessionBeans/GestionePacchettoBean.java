package it.polimi.traveldream.ejb.sessionBeans;

import it.polimi.traveldream.ejb.dto.AereoDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface GestionePacchettoBean {

	List<AereoDTO> getListaAerei();

}
