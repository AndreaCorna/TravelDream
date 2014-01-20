package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.Prenotazione_PacchettoDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class PrenotazioneDataModel extends ListDataModel<Prenotazione_PacchettoDTO> implements SelectableDataModel<Prenotazione_PacchettoDTO>{

	public PrenotazioneDataModel(){
		
	}
	
	public PrenotazioneDataModel(List<Prenotazione_PacchettoDTO> listaPrenotazioni){
		super(listaPrenotazioni);
	}
	@Override
	public Prenotazione_PacchettoDTO getRowData(String id) {
		List<Prenotazione_PacchettoDTO> prenotazioni = (List<Prenotazione_PacchettoDTO>) getWrappedData(); 
		for(Prenotazione_PacchettoDTO pacchetto:prenotazioni){
			Integer value = new Integer(pacchetto.getId());
			if ( value.toString().equals(id)){
				return pacchetto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Prenotazione_PacchettoDTO pacchetto) {
		return pacchetto.getId();
	}

}
