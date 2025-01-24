package com.tombooks.ebook;

import com.tombooks.dao.EBookDAO;
import com.tombooks.entities.Ebook;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
public class EBookListBB {
    private static final String PAGE_EBOOK_EDIT = "ebookEdit?faces-redirect=true";
    private String title;  // Zmieniono "surname" na "title", ponieważ to będzie filtr tytulu ebooka
    private List<Ebook> ebookList;
    private Ebook newEbook = new Ebook();
    private Ebook selectedEbook;  // Dodajemy pole dla edytowanego ebooka

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    EBookDAO ebookDAO;

    public Ebook getNewEbook() {
        return newEbook;
    }

    public void setNewEbook(Ebook newEbook) {
        this.newEbook = newEbook;
    }

    public Ebook getSelectedEbook() {
        return selectedEbook;
    }

    public void setSelectedEbook(Ebook selectedEbook) {
        this.selectedEbook = selectedEbook;
    }

    public List<Ebook> getFullList() {
        if (ebookList == null) {
            ebookList = ebookDAO.findAll();
        }
        return ebookList;
    }

    public void deleteEbook(Long id) {
        ebookDAO.delete(id);
        ebookList = ebookDAO.findAll();
        flash.put("message", "Ebook successfully deleted.");
    }

    public String saveNewEbook() {
        System.out.println("saveNewEbook method called");
        ebookDAO.create(newEbook);
        flash.put("message", "Ebook successfully created.");
        return "ebookList?faces-redirect=true";
    }

    // Metoda edytująca ebooka
    public String saveEditedEbook() {
        System.out.println("saveEditedEbook method called");
        if (selectedEbook != null) {
            ebookDAO.update(selectedEbook);  // Zaktualizowanie ebooka w bazie
            flash.put("message", "Ebook successfully updated.");
        }
        return "ebookList?faces-redirect=true";
    }
    
    public String editEbook(Ebook ebook){
        //1. Pass object through session
        //HttpSession session = (HttpSession) extcontext.getSession(true);
        //session.setAttribute("person", person);

        //2. Pass object through flash 
        flash.put("ebook", ebook);

        return PAGE_EBOOK_EDIT;
    }
}
