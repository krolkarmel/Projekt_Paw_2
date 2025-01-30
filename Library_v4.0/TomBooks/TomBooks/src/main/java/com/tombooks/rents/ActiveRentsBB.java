package com.tombooks.rents;

import com.tombooks.entities.Rental;
import com.tombooks.services.RentalService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.util.List;

public class ActiveRentsBB {

    @Inject
    private RentalService rentalService;

    private List<Rental> userRents;

    @PostConstruct
    public void init() {
        String username = FacesContext.getCurrentInstance()
            .getExternalContext().getUserPrincipal().getName();
        userRents = rentalService.getUserRentals(username);
    }

    public List<Rental> getUserRents() {
        return userRents;
    }
}
