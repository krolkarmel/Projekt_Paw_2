/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Library.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author 48505
 */
@Entity
@Table(name = "book")
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :id"),
    @NamedQuery(name = "Book.findByTytul", query = "SELECT b FROM Book b WHERE b.tytul = :tytul"),
    @NamedQuery(name = "Book.findByImieAutor", query = "SELECT b FROM Book b WHERE b.imieAutor = :imieAutor"),
    @NamedQuery(name = "Book.findByNazwiskoAutor", query = "SELECT b FROM Book b WHERE b.nazwiskoAutor = :nazwiskoAutor"),
    @NamedQuery(name = "Book.findByIsbn", query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
    @NamedQuery(name = "Book.findByDataWydania", query = "SELECT b FROM Book b WHERE b.dataWydania = :dataWydania"),
    @NamedQuery(name = "Book.findByStatus", query = "SELECT b FROM Book b WHERE b.status = :status"),
    @NamedQuery(name = "Book.findByCreatedAt", query = "SELECT b FROM Book b WHERE b.createdAt = :createdAt"),
    @NamedQuery(name = "Book.findByUpdatedAt", query = "SELECT b FROM Book b WHERE b.updatedAt = :updatedAt")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tytul")
    private String tytul;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "imie_autor")
    private String imieAutor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwisko_autor")
    private String nazwiskoAutor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ISBN")
    private String isbn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_wydania")
    @Temporal(TemporalType.DATE)
    private Date dataWydania;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    private Collection<Opinion> opinionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    private Collection<Rental> rentalCollection;

    public Book() {
    }

    public Book(Long id) {
        this.id = id;
    }

    public Book(Long id, String tytul, String imieAutor, String nazwiskoAutor, String isbn, Date dataWydania, String status) {
        this.id = id;
        this.tytul = tytul;
        this.imieAutor = imieAutor;
        this.nazwiskoAutor = nazwiskoAutor;
        this.isbn = isbn;
        this.dataWydania = dataWydania;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getImieAutor() {
        return imieAutor;
    }

    public void setImieAutor(String imieAutor) {
        this.imieAutor = imieAutor;
    }

    public String getNazwiskoAutor() {
        return nazwiskoAutor;
    }

    public void setNazwiskoAutor(String nazwiskoAutor) {
        this.nazwiskoAutor = nazwiskoAutor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDataWydania() {
        return dataWydania;
    }

    public void setDataWydania(Date dataWydania) {
        this.dataWydania = dataWydania;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<Opinion> getOpinionCollection() {
        return opinionCollection;
    }

    public void setOpinionCollection(Collection<Opinion> opinionCollection) {
        this.opinionCollection = opinionCollection;
    }

    public Collection<Rental> getRentalCollection() {
        return rentalCollection;
    }

    public void setRentalCollection(Collection<Rental> rentalCollection) {
        this.rentalCollection = rentalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Library.entities.Book[ id=" + id + " ]";
    }
    
}