package com.tombooks.dao;

import com.tombooks.entities.Transakcja;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class TransakcjaDAO {
    @PersistenceContext
    private EntityManager em;

    public void create(Transakcja transakcja) {
        em.persist(transakcja);
    }
}
