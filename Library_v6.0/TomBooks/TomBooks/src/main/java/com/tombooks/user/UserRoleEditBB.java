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
        System.out.println("Próba aktualizacji użytkownika: " + user.getId() + ", rola: " + user.getRole()); // Logowanie
        userDAO.update(user);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Rola użytkownika została zaktualizowana", null));
        System.out.println("Aktualizuje role");
        return "userList?faces-redirect=true";
    } catch (Exception e) {
        e.printStackTrace(); // Dodaj pełny ślad stosu wyjątku
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas aktualizacji roli", null));
        System.out.println("cos poszlo nie tak");
        return null;
    }
}
}
