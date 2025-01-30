package com.tombooks.ebook;

import com.tombooks.dao.EBookDAO;
import com.tombooks.entities.Ebook;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Backing Bean for creating a new e-book.
 */
@Named
@RequestScoped
public class EBookCreateBB {

    private Ebook newEbook = new Ebook();

    @EJB
    private EBookDAO ebookDAO;

    public Ebook getNewEbook() {
        return newEbook;
    }

    public void setNewEbook(Ebook newEbook) {
        this.newEbook = newEbook;
    }

    public String createEbook() {
        try {
            ebookDAO.create(newEbook);

            // Add success message
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Book został dodany pomyślnie!", null));
            
            return "ebookList?faces-redirect=true"; // Redirect to e-book list page
        } catch (Exception e) {
            // Add error message
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas dodawania e-booka.", null));
            
            return null; // Stay on the current page
        }
    }
}
