import com.tombooks.entities.Book;
import com.tombooks.entities.Rental;
import com.tombooks.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;

@Stateless
public class WypozyczenieService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public String wypozyczKsiazke(Long userId, Long bookId, String metodaPlatnosci, Date dataZwrotu) {
        User user = entityManager.find(User.class, userId);
        Book book = entityManager.find(Book.class, bookId);
        
        if (user == null || book == null) {
            return "Nie znaleziono użytkownika lub książki.";
        }
        
        if (!"DOSTĘPNA".equals(book.getStatus())) {
            return "Książka jest już wypożyczona.";
        }
        
        // Tworzenie nowej transakcji wypożyczenia
        Rental rental = new Rental();
        rental.setUserId(user);
        rental.setBookId(book);
        rental.setStatus("WYPOŻYCZONA");
        
        // Aktualizacja statusu książki
        book.setStatus("WYPOŻYCZONA");
        
        entityManager.persist(rental);
        entityManager.merge(book);
        
        return "Wypożyczenie zakończone sukcesem. Data zwrotu: " + dataZwrotu;
    }
}
