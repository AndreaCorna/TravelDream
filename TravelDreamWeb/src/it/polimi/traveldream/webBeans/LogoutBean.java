package it.polimi.traveldream.webBeans;

import javax.faces.context.FacesContext;

public class LogoutBean {

	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	  }
}
