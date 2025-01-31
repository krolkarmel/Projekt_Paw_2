package com.tombooks.user;

import com.tombooks.dao.UserDAO;
import com.tombooks.entities.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 48512
 */
@Named
@RequestScoped
public class UserRegisterBB {

    private User newUser = new User();

    @EJB
    private UserDAO userDAO;

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String registerNewUser() {
    try {
        newUser.setRole("user");
        newUser.setSaldo(BigDecimal.ZERO);

        userDAO.create(newUser);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true); // Zachowanie wiadomości po przekierowaniu
        facesContext.addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Konto zostało założone pomyślnie!", null));
        return "index?faces-redirect=true";


    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas rejestracji", null));
        return null;
    }
}

}