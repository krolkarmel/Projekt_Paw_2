package com.tombooks.ebook;

import com.tombooks.dao.EBookDAO;
import com.tombooks.dao.TransakcjaDAO;
import com.tombooks.entities.Ebook;
import com.tombooks.entities.Transakcja;
import com.tombooks.entities.User;
import com.tombooks.dao.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.Date;
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
    
    @EJB
    UserDAO userDAO;
    
    @EJB
    TransakcjaDAO transakcjaDAO;

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
    

public void buyEbook(Ebook ebook, Long userId) {
    // Znalezienie użytkownika w bazie
    User user = userDAO.findById(userId);
    
    // Sprawdzanie, czy użytkownik ma wystarczającą ilość pieniędzy
    BigDecimal userBalance = user.getSaldo();  // zakładając, że saldo jest typu BigDecimal
    BigDecimal ebookPrice = ebook.getCena();  // cena e-booka

    if (userBalance.compareTo(ebookPrice) < 0) {  
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true); // Zachowanie wiadomości po przekierowaniu

    facesContext.addMessage(null, 
        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                         "Nie masz wystarczających środków!", 
                         "Proszę doładować konto."));
    
    return;  // Zakończenie metody, aby nie wykonywać dalszych operacji
}

    // Użytkownik ma wystarczającą ilość pieniędzy, przechodzimy do zakupu
    if (!"Kupiony".equals(ebook.getStatus())) {
        ebook.setStatus("Kupiony");
        ebookDAO.update(ebook);  // Zapis do bazy danych
    }

    // Tworzenie nowej transakcji
    Transakcja transakcja = new Transakcja();
    transakcja.setIdUzytkownika(user);
    transakcja.setIdEbooka(ebook);
    transakcja.setKwota(ebookPrice);
    transakcja.setDataTransakcji(new Date());
    transakcja.setStatus("ZAKOŃCZONA");

    // Tworzenie transakcji w bazie danych
    transakcjaDAO.create(transakcja);

    // Zmniejszanie salda użytkownika o cenę e-booka
    user.setSaldo(userBalance.subtract(ebookPrice));
    userDAO.update(user);  // Zapisanie zaktualizowanego salda użytkownika w bazie danych

    
}


    
    public void showTransaction(Long userId) {
    List<Transakcja> transactions = transakcjaDAO.findTransactionsByUser(userId);

    if (transactions.isEmpty()) {
        System.out.println("Brak transakcji dla użytkownika o ID: " + userId);
    } else {
        System.out.println("Transakcje użytkownika ID " + userId + ":");
        for (Transakcja t : transactions) {
            System.out.println("Ebook: " + t.getIdEbooka().getTytul() + ", Kwota: " + t.getKwota() + ", Data: " + t.getDataTransakcji());
        }
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
