package com.tombooks.dao;

import com.tombooks.entities.Rental;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class RentalDAO {
    @PersistenceContext
    private EntityManager em;

    public void create(Rental rental) {
        em.persist(rental);
    }
}
