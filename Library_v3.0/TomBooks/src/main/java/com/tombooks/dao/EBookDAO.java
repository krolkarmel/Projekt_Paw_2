package com.tombooks.dao;

import com.tombooks.entities.Ebook;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EBookDAO {
    private final static String UNIT_NAME = "LibraryPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Ebook ebook) {
        try {
            em.persist(ebook);
            em.flush();  // Wymusza zapis w bazie
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów
        }
    }

    public Ebook findById(Long id) {
        return em.find(Ebook.class, id);
    }

    public List<Ebook> findAll() {
    Query query = em.createQuery("SELECT e FROM Ebook e");
    return query.getResultList();
}
    
    

    public void update(Ebook ebook) {
        em.merge(ebook);  // Zaktualizowanie ebooka w bazie danych
    }

    public void delete(Long id) {
        Ebook ebook = findById(id);
        if (ebook != null) {
            em.remove(ebook);
        }
    }
}
