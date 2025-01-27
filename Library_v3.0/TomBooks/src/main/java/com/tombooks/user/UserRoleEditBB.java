package com.tombooks.user;

import com.tombooks.dao.UserDAO;
import com.tombooks.entities.User;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UserRoleEditBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user = new User();

    @EJB
    private UserDAO userDAO;

    public User getUser() {
        return user;
    }

    public void onLoad() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        user = (User) flash.get("user");
        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak użytkownika do edycji", null));
        }
    }

    public String saveRole() {
        try {
            userDAO.update(user);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Rola użytkownika została zaktualizowana", null));
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas aktualizacji roli", null));
            return null;
        }
    }
}
