//package com.tombooks.ebook;
//
//import com.tombooks.dao.EBookDAO;
//import com.tombooks.dao.TransakcjaDAO;
//import com.tombooks.entities.Ebook;
//import com.tombooks.entities.Transakcja;
//import com.tombooks.entities.User;
//import com.tombooks.service.UserService;
//import jakarta.ejb.EJB;
//import jakarta.faces.context.FacesContext;
//import jakarta.inject.Inject;
//import jakarta.inject.Named;
//import jakarta.enterprise.context.SessionScoped;
//import java.io.Serializable;
//import java.util.List;
//
//@Named
//@SessionScoped
//public class EbookPurchaseBB implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    private List<Ebook> ebookList;
//
//    @EJB
//    private EBookDAO ebookDAO;
//
//    @EJB
//    private TransakcjaDAO transakcjaDAO;
//
//    @Inject
//    private FacesContext facesContext;
//
//    public List<Ebook> getEbookList() {
//        if (ebookList == null) {
//            ebookList = ebookDAO.findAll();
//        }
//        return ebookList;
//    }
//
//    public String buyEbook(Ebook ebook) {
//        // Pobierz użytkownika z sesji
//        User user = UserService.getCurrentUser(); // Załóżmy, że masz metodę, która zwraca aktualnego użytkownika
//
//        // Tworzymy nową transakcję
//        Transakcja transakcja = new Transakcja();
//        transakcja.setIdUzytkownika(user);  // Przypiszemy obiekt użytkownika
//        transakcja.setIdEbooka(ebook);      // Przypiszemy obiekt ebooka
//        transakcja.setDataTransakcji(new java.util.Date());  // Ustawiamy datę zakupu
//
//        // Zapisujemy transakcję w bazie
//        transakcjaDAO.create(transakcja);
//
//        // Zwróć do widoku "TwojeEbooki"
//        return "yourEbooks?faces-redirect=true";
//    }
//
//    private Long getUserIdFromSession() {
//        // Implementacja, która zwróci ID użytkownika z sesji (lub jakiegoś innego mechanizmu autentykacji)
//        return 1L; // Przykład dla testów
//    }
//}
