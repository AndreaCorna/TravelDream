package it.polimi.traveldream.dataModels;

import java.util.List;

import javax.faces.model.ListDataModel;

import it.polimi.traveldream.ejb.dto.EscursioneDTO;

import org.primefaces.model.SelectableDataModel;

public class EscursioneDataModel extends ListDataModel<EscursioneDTO> implements SelectableDataModel<EscursioneDTO>{

	public EscursioneDataModel(){
		
	}
	
	public EscursioneDataModel(List<EscursioneDTO> escursioni){
		super(escursioni);
	}
	
	@Override
	public EscursioneDTO getRowData(String id) {
		List<EscursioneDTO> escursioni = (List<EscursioneDTO>)getWrappedData();
		for(EscursioneDTO escursione : escursioni) { 
        	Integer value = new Integer(escursione.getId());
            if(value.toString().equals(id)) 
                return escursione;  
        }  
		return null;
	}

	@Override
	public Object getRowKey(EscursioneDTO escursione) {
		return escursione.getId();
	}

}
