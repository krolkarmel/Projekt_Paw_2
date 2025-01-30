package com.tombooks.dao;

import com.tombooks.entities.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class BookDAO {
    private final static String UNIT_NAME = "LibraryPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

   public void create(Book book) {
    try {
        if (em == null) {
            System.out.println("EntityManager jest null!");
            return;
        }
        em.persist(book);
        em.flush(); // Wymusza zapis w bazie
        System.out.println("Książka zapisana: " + book);
    } catch (Exception e) {
        e.printStackTrace(); // Logowanie błędów
    }
}

    public Book findById(Long id) {
        return em.find(Book.class, id);
    }
    
    public List<Book> findAll() {
    Query query = em.createQuery("SELECT u FROM Book u");
    return query.getResultList();
    }
    
    public void update(Book book) {
        em.merge(book);  // Zaktualizowanie użytkownika w bazie danych
    
}
    public void delete(Long id) {
        Book book = findById(id);
        if (book != null) {
            em.remove(book);
        }
    }
    
    public int countBooks() {
        Query query = em.createQuery("SELECT COUNT(b) FROM Book b");
        return ((Long) query.getSingleResult()).intValue();
    }

    public List<Book> findBooks(int offset, int pageSize) {
        Query query = em.createQuery("SELECT b FROM Book b");
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}