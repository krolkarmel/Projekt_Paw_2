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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tszkr
 */
@Entity
@Table(name = "transakcja")
@NamedQueries({
    @NamedQuery(name = "Transakcja.findAll", query = "SELECT t FROM Transakcja t"),
    @NamedQuery(name = "Transakcja.findById", query = "SELECT t FROM Transakcja t WHERE t.id = :id"),
    @NamedQuery(name = "Transakcja.findByDataTransakcji", query = "SELECT t FROM Transakcja t WHERE t.dataTransakcji = :dataTransakcji"),
    @NamedQuery(name = "Transakcja.findByKwota", query = "SELECT t FROM Transakcja t WHERE t.kwota = :kwota"),
    @NamedQuery(name = "Transakcja.findByStatus", query = "SELECT t FROM Transakcja t WHERE t.status = :status"),
    @NamedQuery(name = "Transakcja.findByMetodaPlatnosci", query = "SELECT t FROM Transakcja t WHERE t.metodaPlatnosci = :metodaPlatnosci")})
public class Transakcja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_transakcji")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTransakcji;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwota")
    private BigDecimal kwota;
    @Size(max = 12)
    @Column(name = "status")
    private String status;
    @Size(max = 7)
    @Column(name = "metoda_platnosci")
    private String metodaPlatnosci;
    @JoinColumn(name = "id_uzytkownika", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User idUzytkownika;
    @JoinColumn(name = "id_ebooka", referencedColumnName = "id")
    @ManyToOne
    private Ebook idEbooka;
    @JoinColumn(name = "id_ksiazki", referencedColumnName = "id")
    @ManyToOne
    private Book idKsiazki;

    public Transakcja() {
    }

    public Transakcja(Long id) {
        this.id = id;
    }

    public Transakcja(Long id, Date dataTransakcji, BigDecimal kwota) {
        this.id = id;
        this.dataTransakcji = dataTransakcji;
        this.kwota = kwota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataTransakcji() {
        return dataTransakcji;
    }

    public void setDataTransakcji(Date dataTransakcji) {
        this.dataTransakcji = dataTransakcji;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetodaPlatnosci() {
        return metodaPlatnosci;
    }

    public void setMetodaPlatnosci(String metodaPlatnosci) {
        this.metodaPlatnosci = metodaPlatnosci;
    }

    public User getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(User idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    public Ebook getIdEbooka() {
        return idEbooka;
    }

    public void setIdEbooka(Ebook idEbooka) {
        this.idEbooka = idEbooka;
    }

    public Book getIdKsiazki() {
        return idKsiazki;
    }

    public void setIdKsiazki(Book idKsiazki) {
        this.idKsiazki = idKsiazki;
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
        if (!(object instanceof Transakcja)) {
            return false;
        }
        Transakcja other = (Transakcja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tombooks.entities.Transakcja[ id=" + id + " ]";
    }
    
}
