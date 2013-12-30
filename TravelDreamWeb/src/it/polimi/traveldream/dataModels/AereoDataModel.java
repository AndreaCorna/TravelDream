package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.AereoDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class AereoDataModel extends ListDataModel<AereoDTO> implements SelectableDataModel<AereoDTO>{

	public AereoDataModel(){
		
	}
	
	public AereoDataModel(List<AereoDTO> aerei){
		super(aerei);
	}
	
	@Override
	public AereoDTO getRowData(String id) {
	      
        List<AereoDTO> aerei = (List<AereoDTO>) getWrappedData();  
          
        for(AereoDTO aereo : aerei) { 
        	Integer value = new Integer(aereo.getId());
            if(value.toString().equals(id)) 
                return aereo;  
        }  
        return null;
	}

	@Override
	public Object getRowKey(AereoDTO aereo) {
		return aereo.getId();
	}

}
