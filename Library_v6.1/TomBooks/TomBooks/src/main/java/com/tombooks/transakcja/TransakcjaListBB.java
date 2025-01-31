package com.tombooks.transakcja;

import com.tombooks.dao.TransakcjaDAO;
import com.tombooks.entities.Transakcja;
import com.tombooks.entities.User;
import com.tombooks.dao.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named
@RequestScoped
public class TransakcjaListBB {
    private static final String PAGE_TRANSAKCJA_EDIT = "transakcjaEdit?faces-redirect=true";
    private LazyDataModel<Transakcja> lazyModel;
    private Transakcja newTransakcja = new Transakcja();
    private Transakcja selectedTransakcja;
    private List<Transakcja> transakcjaList;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    TransakcjaDAO transakcjaDAO;
    
    @EJB
    UserDAO userDAO;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<Transakcja>() {
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return (int) transakcjaDAO.countTransactions();
            }

            @Override
            public List<Transakcja> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                return transakcjaDAO.findTransactions(offset, pageSize);
            }
        };
    }

    public LazyDataModel<Transakcja> getLazyModel() {
        return lazyModel;
    }
    
    public Transakcja getNewTransakcja() {
        return newTransakcja;
    }

    public void setNewTransakcja(Transakcja newTransakcja) {
        this.newTransakcja = newTransakcja;
    }

    public Transakcja getSelectedTransakcja() {
        return selectedTransakcja;
    }

    public void setSelectedTransakcja(Transakcja selectedTransakcja) {
        this.selectedTransakcja = selectedTransakcja;
    }

    public List<Transakcja> getFullList() {
        if (transakcjaList == null) {
            transakcjaList = transakcjaDAO.findAll();
        }
        return transakcjaList;
    }

    public void deleteTransakcja(Long id) {
        transakcjaDAO.delete(id);
        transakcjaList = transakcjaDAO.findAll();
        flash.put("message", "Transakcja successfully deleted.");
    }

    public String saveNewTransakcja() {
        System.out.println("saveNewTransakcja method called");
        newTransakcja.setDataTransakcji(new Date());
        transakcjaDAO.create(newTransakcja);
        flash.put("message", "Transakcja successfully created.");
        return "transakcjaList?faces-redirect=true";
    }

    public String saveEditedTransakcja() {
        System.out.println("saveEditedTransakcja method called");
        if (selectedTransakcja != null) {
            transakcjaDAO.update(selectedTransakcja);
            flash.put("message", "Transakcja successfully updated.");
        }
        return "transakcjaList?faces-redirect=true";
    }
    
    public List<Transakcja> getTransactionsByUser(Long userId) {
        return transakcjaDAO.findTransactionsByUser(userId);
    }
    
    public String editTransakcja(Transakcja transakcja) {
        flash.put("transakcja", transakcja);
        return PAGE_TRANSAKCJA_EDIT;
    }
}
