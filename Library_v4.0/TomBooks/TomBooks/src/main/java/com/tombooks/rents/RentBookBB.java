package com.tombooks.rents;

import com.tombooks.entities.Book;
import com.tombooks.entities.Rental;
import com.tombooks.entities.User;
import com.tombooks.dao.BookDAO;
import com.tombooks.dao.RentalDAO;
import com.tombooks.dao.UserDAO;
import com.tombooks.services.RentalService;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.util.Date;

@Stateless
public class RentBookBB {

    @Inject
    private RentalService rentalService;

    @Inject
    private UserDAO userDAO;

    @Inject
    private BookDAO bookDAO;

}