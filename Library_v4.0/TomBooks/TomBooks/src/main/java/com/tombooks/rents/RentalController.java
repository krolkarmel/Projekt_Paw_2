package com.tombooks.rents;

import com.tombooks.dao.BookDAO;
import com.tombooks.dao.RentalDAO;
import com.tombooks.dao.UserDAO;
import com.tombooks.entities.Book;
import com.tombooks.services.RentalService;
import com.tombooks.entities.Rental;
import com.tombooks.entities.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@RequestScoped
public class RentalController {

    @Inject
    private RentalService rentalService;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private RentalDAO rentalDAO;
    @POST
    @Path("/rent/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean rentBook(Long bookId, String username) {
    User user = userDAO.findByUsername(username);
    Book book = bookDAO.findById(bookId);

    if (book == null || !book.getStatus().equals("AVAILABLE")) {
        return false;  // Zwracamy false, jeśli książka nie jest dostępna
    }

    Rental rental = new Rental();
    rental.setBookId(book);
    rental.setUserId(user);
    rental.setStatus("RENTED");
    rental.setCreatedAt(new Date());
    rental.setUpdatedAt(new Date());

    book.setStatus("RENTED");

    rentalDAO.rentBook(rental);  // Zapisujemy wypożyczenie
    bookDAO.update(book);  // Aktualizujemy status książki

    return true;  // Zwracamy true, jeśli wszystko przebiegło pomyślnie
}

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rental> getUserRentals(@HeaderParam("username") String username) {
        return rentalService.getUserRentals(username);
    }
}
