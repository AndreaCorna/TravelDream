package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.AereoDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ViaggioDataModel extends ListDataModel<AereoDTO> implements SelectableDataModel<AereoDTO>{

	public ViaggioDataModel(){
		
	}
	
	public ViaggioDataModel(List<AereoDTO> listaPacchetti){
		super(listaPacchetti);
	}
	@Override
	public AereoDTO getRowData(String id) {
		List<AereoDTO> pacchetti = (List<AereoDTO>) getWrappedData(); 
		for(AereoDTO pacchetto:pacchetti){
			Integer value = new Integer(pacchetto.getId());
			if ( value.toString().equals(id)){
				return pacchetto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AereoDTO pacchetto) {
		return pacchetto.getId();
	}

}
