/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tombooks.entities;

import jakarta.persistence.Basic;
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
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author tszkr
 */
@Entity
@Table(name = "ebook")
@NamedQueries({
    @NamedQuery(name = "Ebook.findAll", query = "SELECT e FROM Ebook e"),
    @NamedQuery(name = "Ebook.findById", query = "SELECT e FROM Ebook e WHERE e.id = :id"),
    @NamedQuery(name = "Ebook.findByTytul", query = "SELECT e FROM Ebook e WHERE e.tytul = :tytul"),
    @NamedQuery(name = "Ebook.findByImieAutora", query = "SELECT e FROM Ebook e WHERE e.imieAutora = :imieAutora"),
    @NamedQuery(name = "Ebook.findByNazwiskoAutora", query = "SELECT e FROM Ebook e WHERE e.nazwiskoAutora = :nazwiskoAutora"),
    @NamedQuery(name = "Ebook.findByIsbn", query = "SELECT e FROM Ebook e WHERE e.isbn = :isbn"),
    @NamedQuery(name = "Ebook.findByDataWydania", query = "SELECT e FROM Ebook e WHERE e.dataWydania = :dataWydania"),
    @NamedQuery(name = "Ebook.findByCena", query = "SELECT e FROM Ebook e WHERE e.cena = :cena"),
    @NamedQuery(name = "Ebook.findBySciezkaPliku", query = "SELECT e FROM Ebook e WHERE e.sciezkaPliku = :sciezkaPliku"),
    @NamedQuery(name = "Ebook.findByStatus", query = "SELECT e FROM Ebook e WHERE e.status = :status"),
    @NamedQuery(name = "Ebook.findByUtworzono", query = "SELECT e FROM Ebook e WHERE e.utworzono = :utworzono"),
    @NamedQuery(name = "Ebook.findByZmodyfikowano", query = "SELECT e FROM Ebook e WHERE e.zmodyfikowano = :zmodyfikowano")})
public class Ebook implements Serializable {

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
    @Column(name = "imie_autora")
    private String imieAutora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwisko_autora")
    private String nazwiskoAutora;
    @Size(max = 255)
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "data_wydania")
    @Temporal(TemporalType.DATE)
    private Date dataWydania;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private BigDecimal cena;
    @Size(max = 500)
    @Column(name = "sciezka_pliku")
    private String sciezkaPliku;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "utworzono")
    @Temporal(TemporalType.TIMESTAMP)
    private Date utworzono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zmodyfikowano")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zmodyfikowano;
    @OneToMany(mappedBy = "idEbooka")
    private Collection<Transakcja> transakcjaCollection;

    public Ebook() {
    }

    public Ebook(Long id) {
        this.id = id;
    }

    public Ebook(Long id, String tytul, String imieAutora, String nazwiskoAutora, BigDecimal cena, Date utworzono, Date zmodyfikowano) {
        this.id = id;
        this.tytul = tytul;
        this.imieAutora = imieAutora;
        this.nazwiskoAutora = nazwiskoAutora;
        this.cena = cena;
        this.utworzono = utworzono;
        this.zmodyfikowano = zmodyfikowano;
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

    public String getImieAutora() {
        return imieAutora;
    }

    public void setImieAutora(String imieAutora) {
        this.imieAutora = imieAutora;
    }

    public String getNazwiskoAutora() {
        return nazwiskoAutora;
    }

    public void setNazwiskoAutora(String nazwiskoAutora) {
        this.nazwiskoAutora = nazwiskoAutora;
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

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public String getSciezkaPliku() {
        return sciezkaPliku;
    }

    public void setSciezkaPliku(String sciezkaPliku) {
        this.sciezkaPliku = sciezkaPliku;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUtworzono() {
        return utworzono;
    }

    public void setUtworzono(Date utworzono) {
        this.utworzono = utworzono;
    }

    public Date getZmodyfikowano() {
        return zmodyfikowano;
    }

    public void setZmodyfikowano(Date zmodyfikowano) {
        this.zmodyfikowano = zmodyfikowano;
    }

    public Collection<Transakcja> getTransakcjaCollection() {
        return transakcjaCollection;
    }

    public void setTransakcjaCollection(Collection<Transakcja> transakcjaCollection) {
        this.transakcjaCollection = transakcjaCollection;
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
        if (!(object instanceof Ebook)) {
            return false;
        }
        Ebook other = (Ebook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tombooks.entities.Ebook[ id=" + id + " ]";
    }
    
}
