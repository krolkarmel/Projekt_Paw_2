package com.tombooks.book;

import com.tombooks.dao.BookDAO;
import com.tombooks.entities.Book;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Backing Bean for creating a new book.
 */
@Named
@RequestScoped
public class BookCreateBB {

    private Book newBook = new Book();

    @EJB
    private BookDAO bookDAO;

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }

    public String createBook() {
        try {
            bookDAO.create(newBook);

            // Add success message
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Książka została dodana pomyślnie!", null));
            
            return "bookList?faces-redirect=true"; // Redirect to book list page
        } catch (Exception e) {
            // Add error message
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas dodawania książki.", null));
            
            return null; // Stay on the current page
        }
    }
}
