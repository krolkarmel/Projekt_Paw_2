package com.tombooks.book;

import com.tombooks.dao.BookDAO;
import com.tombooks.entities.Book;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class BookListBB {
    private static final String PAGE_BOOK_EDIT = "bookEdit?faces-redirect=true";
    private String title;  // Opcjonalne pole do filtrowania po tytule
    private List<Book> bookList;
    private Book newBook = new Book();
    private Book selectedBook;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    BookDAO bookDAO;

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public List<Book> getFullList() {
        if (bookList == null) {
            bookList = bookDAO.findAll();
        }
        return bookList;
    }

    public void deleteBook(Long id) {
        bookDAO.delete(id);
        bookList = bookDAO.findAll();
        flash.put("message", "Book successfully deleted.");
    }

    public String saveNewBook() {
        System.out.println("saveNewBook method called");
        bookDAO.create(newBook);
        flash.put("message", "Book successfully created.");
        return "bookList?faces-redirect=true";
    }

    public String saveEditedBook() {
        System.out.println("saveEditedBook method called");
        if (selectedBook != null) {
            bookDAO.update(selectedBook);
            flash.put("message", "Book successfully updated.");
        }
        return "bookList?faces-redirect=true";
    }

    public String editBook(Book book) {
        // Pass object through flash
        flash.put("book", book);
        return "bookEdit?faces-redirect=true";

    }
}
