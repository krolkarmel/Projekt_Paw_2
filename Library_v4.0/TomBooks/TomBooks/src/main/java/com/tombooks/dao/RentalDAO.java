package com.tombooks.dao;

import com.tombooks.entities.Rental;
import com.tombooks.entities.User;
import com.tombooks.entities.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Date;

@Stateless
public class RentalDAO {

    @PersistenceContext
    private EntityManager em;

    public boolean isBookAvailable(Book book) {
        Long count = em.createQuery("SELECT COUNT(r) FROM Rental r WHERE r.bookId = :book AND r.status = :status", Long.class)
                       .setParameter("book", book)
                       .setParameter("status", Rental.STATUS_RENTED)
                       .getSingleResult();
        return count == 0;
    }

    public boolean rentBook(Rental rental) {
        try {
            em.persist(rental); // Zapisuje wypożyczenie w bazie danych
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów
            return false;
        }
    }
    
    public List<Rental> findUserRentals(User user) {
        return em.createQuery("SELECT r FROM Rental r WHERE r.userId = :user", Rental.class)
                 .setParameter("user", user)
                 .getResultList();
    }

    public boolean returnBook(Rental rental) {
        if (rental.getStatus().equals(Rental.STATUS_RENTED)) {
            rental.returnBook();
            em.merge(rental);
            return true;
        }
        return false;
    }

    public void updateRental(Rental rental) {
        em.merge(rental);
    }
    
    public List<Rental> findUserRentals(String username) {
    return em.createQuery("SELECT r FROM Rental r WHERE r.userId.name = :username", Rental.class)
             .setParameter("username", username)
             .getResultList();
}
}
