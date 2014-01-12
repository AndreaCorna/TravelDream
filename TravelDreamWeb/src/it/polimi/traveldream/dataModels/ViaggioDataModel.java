package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.PacchettoDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ViaggioDataModel extends ListDataModel<PacchettoDTO> implements SelectableDataModel<PacchettoDTO>{

	public ViaggioDataModel(){
		
	}
	
	public ViaggioDataModel(List<PacchettoDTO> listaPacchetti){
		super(listaPacchetti);
	}
	@Override
	public PacchettoDTO getRowData(String id) {
		List<PacchettoDTO> pacchetti = (List<PacchettoDTO>) getWrappedData(); 
		for(PacchettoDTO pacchetto:pacchetti){
			Integer value = new Integer(pacchetto.getId());
			if ( value.toString().equals(id)){
				return pacchetto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(PacchettoDTO pacchetto) {
		return pacchetto.getId();
	}

}
