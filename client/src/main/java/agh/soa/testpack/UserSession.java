package agh.soa.testpack;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
public class UserSession {
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/index?faces-redirect=true";
    }

    public boolean isUserLoggedIn() {
        String user = this.getUsername();
        return !((user == null)|| user.isEmpty());
    }

    /** Get the login username if it exists */
    public String getUsername() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }
    public String getRole() {
        if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Manager"))
            return "Manager";
        return "User";
    }
}
