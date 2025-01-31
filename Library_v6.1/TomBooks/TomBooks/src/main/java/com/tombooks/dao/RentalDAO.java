package com.tombooks.dao;

import com.tombooks.entities.Rental;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Stateless
public class RentalDAO {
    private final static String UNIT_NAME = "LibraryPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    // Metoda do zapisywania wypożyczenia w bazie danych
    public void create(Rental rental) {
        try {
            em.persist(rental);
            em.flush();  // Wymusza zapis w bazie
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów
        }
    }

    // Metoda do znajdowania wypożyczenia po ID
    public Rental findById(Long id) {
        return em.find(Rental.class, id);
    }

    // Metoda do pobierania wszystkich wypożyczeń
    public List<Rental> findAll() {
        Query query = em.createQuery("SELECT r FROM Rental r");
        return query.getResultList();
    }

    // Metoda do aktualizacji wypożyczenia
    public void update(Rental rental) {
        em.merge(rental);  // Zaktualizowanie wypożyczenia w bazie danych
    }

    // Metoda do usuwania wypożyczenia
    public void delete(Long id) {
        Rental rental = findById(id);
        if (rental != null) {
            em.remove(rental);
        }
    }

    // Metoda do zliczania wszystkich wypożyczeń
    public int countRentals() {
        Query query = em.createQuery("SELECT COUNT(r) FROM Rental r");
        return ((Long) query.getSingleResult()).intValue();
    }

    // Metoda do pobierania wypożyczeń z paginacją
    public List<Rental> findRentals(int offset, int pageSize) {
        Query query = em.createQuery("SELECT r FROM Rental r");
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    // Metoda do pobierania wypożyczeń dla danego użytkownika
    public List<Rental> findRentalsByUser(Long userId) {
        Query query = em.createQuery("SELECT r FROM Rental r WHERE r.userId.id = :userId");
        query.setParameter("userId", userId);
        System.out.println(query.getResultList()); // Debugowanie
        return query.getResultList();
    }

    // Metoda do pobierania wypożyczeń dla danego e-booka
    public List<Rental> findRentalsByEbook(Long ebookId) {
        Query query = em.createQuery("SELECT r FROM Rental r WHERE r.ebook.id = :ebookId");
        query.setParameter("ebookId", ebookId);
        return query.getResultList();
    }
}
