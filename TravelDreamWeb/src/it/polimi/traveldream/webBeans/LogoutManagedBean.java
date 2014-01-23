package it.polimi.traveldream.webBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="logout")
@RequestScoped
public class LogoutManagedBean {

	/**
	 * Metodo che permette il logout
	 * @return
	 */
	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	  }
}
