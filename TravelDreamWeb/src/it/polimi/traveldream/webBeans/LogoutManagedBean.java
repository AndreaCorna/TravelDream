package it.polimi.traveldream.webBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * IL managed bean permette di effettuare il logout all'utente attivo
 * @author Alessandro Brunitti - Andrea Corna
 *
 */
@ManagedBean(name="logout")
@RequestScoped
public class LogoutManagedBean {

	/**
	 * Metodo che permette il logout
	 * @return redirect alla homepage generale del sito
	 */
	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	  }
}
