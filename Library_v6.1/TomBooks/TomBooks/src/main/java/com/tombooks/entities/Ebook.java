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
    @NamedQuery(name = "Ebook.findByKod", query = "SELECT e FROM Ebook e WHERE e.kod = :kod"),
    @NamedQuery(name = "Ebook.findByDataWydania", query = "SELECT e FROM Ebook e WHERE e.dataWydania = :dataWydania"),
    @NamedQuery(name = "Ebook.findByCena", query = "SELECT e FROM Ebook e WHERE e.cena = :cena"),
    @NamedQuery(name = "Ebook.findByFormatPliku", query = "SELECT e FROM Ebook e WHERE e.formatPliku = :formatPliku"),
    @NamedQuery(name = "Ebook.findByStatus", query = "SELECT e FROM Ebook e WHERE e.status = :status"),
    @NamedQuery(name = "Ebook.findByLinkPobrania", query = "SELECT e FROM Ebook e WHERE e.linkPobrania = :linkPobrania")})
public class Ebook implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tytul")
    private String tytul;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "imieAutora")
    private String imieAutora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwiskoAutora")
    private String nazwiskoAutora;
    @Size(max = 255)
    @Column(name = "kod")
    private String kod;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private BigDecimal cena;
    @Size(max = 500)
    @Column(name = "formatPliku")
    private String formatPliku;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "linkPobrania")
    private String linkPobrania;
    @OneToMany(mappedBy = "idEbooka")
    private Collection<Transakcja> transakcjaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "dataWydania")
    @Temporal(TemporalType.DATE)
    private Date dataWydania;

    public Ebook() {
    }

    public Ebook(Long id) {
        this.id = id;
    }

    public Ebook(Long id, String tytul, String imieAutora, String nazwiskoAutora, BigDecimal cena, String linkPobrania) {
        this.id = id;
        this.tytul = tytul;
        this.imieAutora = imieAutora;
        this.nazwiskoAutora = nazwiskoAutora;
        this.cena = cena;
        this.linkPobrania = linkPobrania;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Date getDataWydania() {
        return dataWydania;
    }
    public void setDataWydania(Date dataWydania) {
        this.dataWydania = dataWydania;
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

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public String getFormatPliku() {
        return formatPliku;
    }

    public void setFormatPliku(String formatPliku) {
        this.formatPliku = formatPliku;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinkPobrania() {
        return linkPobrania;
    }

    public void setLinkPobrania(String linkPobrania) {
        this.linkPobrania = linkPobrania;
    }

    public Collection<Transakcja> getTransakcjaCollection() {
        return transakcjaCollection;
    }

    public void setTransakcjaCollection(Collection<Transakcja> transakcjaCollection) {
        this.transakcjaCollection = transakcjaCollection;
    }
    
}