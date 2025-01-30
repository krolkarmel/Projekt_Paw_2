package com.tombooks.services;

import com.tombooks.entities.Book;
import com.tombooks.entities.Rental;
import com.tombooks.entities.User;
import com.tombooks.dao.BookDAO;
import com.tombooks.dao.RentalDAO;
import com.tombooks.dao.UserDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
public class RentalService {

    private RentalDAO rentalDAO;
    private UserDAO userDAO;
    private BookDAO bookDAO;

    

    public List<Rental> getUserRentals(String username) {
        User user = userDAO.findByUsername(username);
        return rentalDAO.findUserRentals(user);
    }
}