package com.tombooks.book;

import com.tombooks.dao.BookDAO;
import com.tombooks.dao.RentalDAO;
import com.tombooks.dao.UserDAO;
import com.tombooks.entities.Book;
import com.tombooks.entities.Rental;
import com.tombooks.entities.Transakcja;
import com.tombooks.entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named
@RequestScoped
public class BookListBB {
    private static final String PAGE_BOOK_EDIT = "bookEdit?faces-redirect=true";
    private List<Book> bookList;
    private Book newBook = new Book();
    private Book selectedBook;
    private LazyDataModel<Book> lazyModel;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    BookDAO bookDAO;
    
    @EJB
    UserDAO userDAO;
    
    @EJB
    RentalDAO rentalDAO;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<Book>() {
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return (int) bookDAO.countBooks();
            }

            @Override
            public List<Book> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                List<Book> books = bookDAO.findBooks(offset, pageSize);
                return books;
            }
        };
    }

    public LazyDataModel<Book> getLazyModel() {
        return lazyModel;
    }
    
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
    
    public void rentBook(Book book, Long userId) {
        User user = userDAO.findById(userId);
        if (!"Wypożyczona".equals(book.getStatus())) {
            book.setStatus("Wypożyczona");
            bookDAO.update(book);  // Zapis do bazy danych
        }
        
        Rental rental = new Rental();
        rental.setUserId(user);
        rental.setBookId(book);
        rental.setCreatedAt(new Date());
        rental.setDataZwrotu(rental.getCreatedAtPlusOneMonth());
        rental.setStatus("Aktywne");
        
        rentalDAO.create(rental);

    }
    
    public void showRental(Long userId) {
    List<Rental> rentals = rentalDAO.findRentalsByUser(userId);

    if (rentals.isEmpty()) {
        System.out.println("Brak transakcji dla użytkownika o ID: " + userId);
    } else {
        System.out.println("Transakcje użytkownika ID " + userId + ":");
        for (Rental t : rentals) {
            System.out.println("Ebook: " + t.getBookId().getTytul() + ", Data: " + t.getCreatedAt());
        }
    }
}

    public String editBook(Book book) {
        // Pass object through flash
        flash.put("book", book);
        return "bookEdit?faces-redirect=true";

    }
}
