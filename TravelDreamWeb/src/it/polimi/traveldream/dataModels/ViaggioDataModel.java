package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.ViaggioDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ViaggioDataModel extends ListDataModel<ViaggioDTO> implements SelectableDataModel<ViaggioDTO>{

	public ViaggioDataModel(){
		
	}
	
	public ViaggioDataModel(List<ViaggioDTO> listaPacchetti){
		super(listaPacchetti);
	}
	@Override
	public ViaggioDTO getRowData(String id) {
		List<ViaggioDTO> pacchetti = (List<ViaggioDTO>) getWrappedData(); 
		for(ViaggioDTO pacchetto:pacchetti){
			Integer value = new Integer(pacchetto.getId());
			if ( value.toString().equals(id)){
				return pacchetto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ViaggioDTO pacchetto) {
		return pacchetto.getId();
	}

}
