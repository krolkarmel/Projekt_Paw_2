package com.tombooks.ebook;

import com.tombooks.dao.EBookDAO;
import com.tombooks.entities.Ebook;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

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
    private LazyDataModel<Ebook> lazyModel;
    private Ebook newEbook = new Ebook();
    private Ebook selectedEbook;
    private List<Ebook> ebookList;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    EBookDAO ebookDAO;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<Ebook>() {
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return (int) ebookDAO.countEBooks();
            }

            @Override
            public List<Ebook> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                List<Ebook> ebooks = ebookDAO.findEBooks(offset, pageSize);
                return ebooks;
            }
        };
    }

    public LazyDataModel<Ebook> getLazyModel() {
        return lazyModel;
    }
    
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
    
    public void buyEbook(Ebook ebook) {
        if (!"Kupiony".equals(ebook.getStatus())) {
            ebook.setStatus("Kupiony");
            ebookDAO.update(ebook);  // Zapis do bazy danych
        }
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
