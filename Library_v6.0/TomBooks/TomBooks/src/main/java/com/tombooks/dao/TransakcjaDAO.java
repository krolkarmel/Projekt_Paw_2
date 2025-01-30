package com.tombooks.dao;

import com.tombooks.entities.Transakcja;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Stateless
public class TransakcjaDAO {
    private final static String UNIT_NAME = "LibraryPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    // Metoda do zapisywania transakcji w bazie danych
    public void create(Transakcja transakcja) {
        try {
            em.persist(transakcja);
            em.flush();  // Wymusza zapis w bazie
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów
        }
    }

    // Metoda do znajdowania transakcji po ID
    public Transakcja findById(Long id) {
        return em.find(Transakcja.class, id);
    }

    // Metoda do pobierania wszystkich transakcji
    public List<Transakcja> findAll() {
        Query query = em.createQuery("SELECT t FROM Transakcja t");
        return query.getResultList();
    }

    // Metoda do aktualizacji transakcji
    public void update(Transakcja transakcja) {
        em.merge(transakcja);  // Zaktualizowanie transakcji w bazie danych
    }

    // Metoda do usuwania transakcji
    public void delete(Long id) {
        Transakcja transakcja = findById(id);
        if (transakcja != null) {
            em.remove(transakcja);
        }
    }

    // Metoda do zliczania wszystkich transakcji
    public int countTransactions() {
        Query query = em.createQuery("SELECT COUNT(t) FROM Transakcja t");
        return ((Long) query.getSingleResult()).intValue();
    }

    // Metoda do pobierania transakcji z paginacją
    public List<Transakcja> findTransactions(int offset, int pageSize) {
        Query query = em.createQuery("SELECT t FROM Transakcja t");
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    // Metoda do pobierania transakcji dla danego użytkownika
    public List<Transakcja> findTransactionsByUser(Long userId) {
        Query query = em.createQuery("SELECT t FROM Transakcja t WHERE t.idUzytkownika.id = :userId");
        query.setParameter("userId", userId);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    // Metoda do pobierania transakcji dla danego e-booka
    public List<Transakcja> findTransactionsByEbook(Long ebookId) {
        Query query = em.createQuery("SELECT t FROM Transakcja t WHERE t.idEbooka.id = :ebookId");
        query.setParameter("ebookId", ebookId);
        return query.getResultList();
    }
    
    
}