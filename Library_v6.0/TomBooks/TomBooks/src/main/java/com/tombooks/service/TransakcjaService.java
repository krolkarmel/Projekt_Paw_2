import com.tombooks.entities.Ebook;
import com.tombooks.entities.Transakcja;
import com.tombooks.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;

@Stateless
public class TransakcjaService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public String kupEbook(Long userId, Long ebookId, String metodaPlatnosci) {
        User user = entityManager.find(User.class, userId);
        Ebook ebook = entityManager.find(Ebook.class, ebookId);
        
        if (user == null || ebook == null) {
            return "Nie znaleziono użytkownika lub e-booka.";
        }
        
        if (user.getSaldo().compareTo(ebook.getCena()) < 0) {
            return "Brak wystarczających środków na koncie.";
        }
        
        // Pomniejszenie salda użytkownika
        user.setSaldo(user.getSaldo().subtract(ebook.getCena()));
        entityManager.merge(user);
        
        // Tworzenie nowej transakcji
        Transakcja transakcja = new Transakcja();
        transakcja.setIdUzytkownika(user);
        transakcja.setIdEbooka(ebook);
        transakcja.setKwota(ebook.getCena());
        transakcja.setDataTransakcji(new Date());
        transakcja.setMetodaPlatnosci(metodaPlatnosci);
        transakcja.setStatus("ZAKOŃCZONA");
        
        entityManager.persist(transakcja);
        System.out.println("Rozpoczynam zakup ebooka ID: " + ebookId + " przez użytkownika ID: " + userId);
        return "Zakup zakończony sukcesem. Link do pobrania: " + ebook.getLinkPobrania();
    }
}