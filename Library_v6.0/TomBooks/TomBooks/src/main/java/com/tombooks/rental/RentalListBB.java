package com.tombooks.rental;

import com.tombooks.dao.RentalDAO;
import com.tombooks.entities.Rental;
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
public class RentalListBB {
    private static final String PAGE_RENTAL_EDIT = "rentalEdit?faces-redirect=true";
    private LazyDataModel<Rental> lazyModel;
    private Rental newRental = new Rental();
    private Rental selectedRental;
    private List<Rental> rentalList;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    RentalDAO rentalDAO;
    
    @EJB
    UserDAO userDAO;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<Rental>() {
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return (int) rentalDAO.countRentals();
            }

            @Override
            public List<Rental> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                return rentalDAO.findRentals(offset, pageSize);
            }
        };
    }

    public LazyDataModel<Rental> getLazyModel() {
        return lazyModel;
    }
    
    public Rental getNewRental() {
        return newRental;
    }

    public void setNewRental(Rental newRental) {
        this.newRental = newRental;
    }

    public Rental getSelectedRental() {
        return selectedRental;
    }

    public void setSelectedRental(Rental selectedRental) {
        this.selectedRental = selectedRental;
    }

    public List<Rental> getFullList() {
        if (rentalList == null) {
            rentalList = rentalDAO.findAll();
        }
        return rentalList;
    }

    public void deleteRental(Long id) {
        rentalDAO.delete(id);
        rentalList = rentalDAO.findAll();
        flash.put("message", "Rental successfully deleted.");
    }

    public String saveNewRental() {
        System.out.println("saveNewRental method called");
        newRental.setCreatedAt(new Date());
        rentalDAO.create(newRental);
        flash.put("message", "Rental successfully created.");
        return "rentalList?faces-redirect=true";
    }

    public String saveEditedRental() {
        System.out.println("saveEditedRental method called");
        if (selectedRental != null) {
            rentalDAO.update(selectedRental);
            flash.put("message", "Rental successfully updated.");
        }
        return "rentalList?faces-redirect=true";
    }
    
    public List<Rental> getRentalsByUser(Long userId) {
        return rentalDAO.findRentalsByUser(userId);
    }
    
    public String editRental(Rental rental) {
        flash.put("rental", rental);
        return PAGE_RENTAL_EDIT;
    }
}
