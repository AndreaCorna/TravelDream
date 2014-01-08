package it.polimi.traveldream.dataModels;

import java.util.List;

import it.polimi.traveldream.ejb.dto.UtenteDTO;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class UtenteDataModel extends ListDataModel<UtenteDTO> implements SelectableDataModel<UtenteDTO>{

	public UtenteDataModel(){
		
	}
	
	public UtenteDataModel(List<UtenteDTO> lista){
		super(lista);
	}
	
	@Override
	public UtenteDTO getRowData(String username) {
		List<UtenteDTO> listaUtenti = (List<UtenteDTO>) getWrappedData(); 
		for(UtenteDTO utente:listaUtenti){
			if ( utente.getUsername().equals(username) ){
				return utente;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(UtenteDTO utente) {
		return utente.getUsername();
	}

}
