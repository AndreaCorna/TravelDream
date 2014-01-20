package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.Prenotazione_ViaggioDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class PrenotazioneViaggioDataModel extends ListDataModel<Prenotazione_ViaggioDTO> implements SelectableDataModel<Prenotazione_ViaggioDTO>{

	public PrenotazioneViaggioDataModel(){
		
	}
	
	public PrenotazioneViaggioDataModel(List<Prenotazione_ViaggioDTO> listaPrenotazioni){
		super(listaPrenotazioni);
	}
	@Override
	public Prenotazione_ViaggioDTO getRowData(String id) {
		List<Prenotazione_ViaggioDTO> prenotazioni = (List<Prenotazione_ViaggioDTO>) getWrappedData(); 
		for(Prenotazione_ViaggioDTO pacchetto:prenotazioni){
			Integer value = new Integer(pacchetto.getId());
			if ( value.toString().equals(id)){
				return pacchetto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Prenotazione_ViaggioDTO pacchetto) {
		return pacchetto.getId();
	}

}
