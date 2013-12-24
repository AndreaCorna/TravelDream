package it.polimi.traveldream.webBeans;

import javax.faces.context.FacesContext;

public class LogoutManagedBean {

	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	  }
}
