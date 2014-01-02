package it.polimi.traveldream.dataModels;

import it.polimi.traveldream.ejb.dto.HotelDTO;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;


public class HotelDataModel extends ListDataModel<HotelDTO> implements SelectableDataModel<HotelDTO>{

	public HotelDataModel(){
		
	}
	
	public HotelDataModel(List<HotelDTO> hotel){
		super(hotel);
	}
	
	@Override
	public HotelDTO getRowData(String id) {
	      
        List<HotelDTO> hotels = (List<HotelDTO>) getWrappedData();  
          
        for(HotelDTO hotel : hotels) { 
        	Integer value = new Integer(hotel.getId());
            if(value.toString().equals(id)) 
                return hotel;  
        }  
        return null;
	}

	@Override
	public Object getRowKey(HotelDTO hotel) {
		return hotel.getId();
	}

}