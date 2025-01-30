package com.tombooks.ebook;

import com.tombooks.dao.EBookDAO;
import com.tombooks.entities.Ebook;
import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EBookEditBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_EBOOK_LIST = "ebookList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private Ebook ebook = new Ebook();
    private Ebook loaded = null;

    @EJB
    EBookDAO ebookDAO;

    @Inject
    FacesContext context;

    @Inject
    Flash flash;

    public Ebook getEbook() {
        return ebook;
    }

    public void onLoad() throws IOException {
        // 1. load ebook passed through flash
        loaded = (Ebook) flash.get("ebook");

        // cleaning: attribute received => delete it from flash
        if (loaded != null) {
            ebook = loaded;
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
        }
    }

    public String saveData() {
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }

        try {
            if (ebook.getId() == null) {
                ebookDAO.create(ebook);
            } else {
                // existing record
                ebookDAO.update(ebook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu", null));
            return PAGE_STAY_AT_THE_SAME;
        }

        return PAGE_EBOOK_LIST;
    }
}