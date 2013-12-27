package it.polimi.traveldream.webBeans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.traveldream.ejb.dto.AereoDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.sessionBeans.GestioneComponenteBean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;



@ManagedBean(name="componente")
@RequestScoped
public class ComponenteManagedBean {
	
	@EJB
	private GestioneComponenteBean gestioneComp;
	
	private AereoDTO aereo;
	
	private HotelDTO hotel;
	
	public ComponenteManagedBean(){
		setAereo(new AereoDTO());
	}

	public AereoDTO getAereo() {
		return aereo;
	}

	public void setAereo(AereoDTO aereo) {
		this.aereo = aereo;
	}
	
	public String aggiungiAereoDB(){
		gestioneComp.aggiungiAereoDB(aereo);
		return "index?faces-redirect=true";
	}
	
	public String aggiungiHotelDB(){
		gestioneComp.aggiungiHotelDB(hotel);
		return "index?faces-redirect=true";
	}
	
	
	/*
	 * Validatori dei dati inseriti
	 */
	
	public void validaIdAereo(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("L'id può contenere solo numeri"));
		}
		if (gestioneComp.esisteAereo(value.toString())){
            throw new ValidatorException(new FacesMessage("Identificativo già utilizzato"));
		}
		
	}
	
	public void validaNumero(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (!isNumeroCorretto(value.toString())){
            throw new ValidatorException(new FacesMessage("Il campo può contenere solo numeri"));
		}
	}
	
	/*public void aereoVola(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if(aereo.getCittaDecollo().compareTo((String)value) == 0){
			throw new ValidatorException(new FacesMessage("Il decollo deve essere diverso dall'atterraggio"));
		}
	}*/
	
	private boolean isNumeroCorretto(String id){
		Pattern p1 = Pattern.compile("[0-9]+");
		Matcher m1 = p1.matcher(id);
		return  m1.find();
	}
}




