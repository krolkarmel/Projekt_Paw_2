package com.tombooks.ebook;

import com.tombooks.dao.TransakcjaDAO;
import com.tombooks.entities.Transakcja;
import com.tombooks.entities.Ebook;
import com.tombooks.entities.User;
import com.tombooks.dao.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Query;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
//import org.apache.commons.io.IOUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Backing Bean dla widoku kupionych e-booków
 */
@Named
@RequestScoped
public class KupioneEbookiBB {

    @EJB
    private TransakcjaDAO transakcjaDAO;

    @EJB
    private UserDAO userDAO;

    @Inject
    private ExternalContext externalContext;

    private List<Transakcja> boughtEbooks;

    @PostConstruct
    public void init() {
        Long userId = getLoggedInUserId();

        if (userId == null) {
            System.out.println("Błąd: Brak zalogowanego użytkownika.");
            return;
        }

        boughtEbooks = transakcjaDAO.findTransactionsByUser(userId);

        if (boughtEbooks == null || boughtEbooks.isEmpty()) {
            System.out.println("Brak kupionych e-booków dla użytkownika ID: " + userId);
        } else {
            System.out.println("Znaleziono " + boughtEbooks.size() + " kupionych e-booków.");
        }
    }

   

    private Long getLoggedInUserId() {
        User user = (User) externalContext.getSessionMap().get("loggedUser");
        return user != null ? user.getId() : null;
    }

    public List<Ebook> getBoughtEbooks(Long userId) {
    List<Transakcja> transactions = transakcjaDAO.findTransactionsByUser(userId);
    
    System.out.println("Znalezione transakcje dla userId " + userId + ": " + transactions.size());

    return transactions.stream()
                       .map(Transakcja::getIdEbooka)
                       .distinct()
                       .toList();
}
    /**
     * Pobiera plik e-booka
     */
//    public void downloadEbook(Ebook ebook) {
//        if (ebook == null || ebook.getPlik() == null) {
//            return;
//        }
//
//        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + ebook.getTytul() + "." + ebook.getFormatPliku() + "\"");
//
//        try (InputStream inputStream = ebook.getPlik()) {
//            IOUtils.copy(inputStream, response.getOutputStream());
//            response.getOutputStream().flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        externalContext.responseComplete();
//    }
}
