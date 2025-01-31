package com.tombooks.service;

import com.tombooks.entities.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;

@Stateless
public class UserService {

    @Inject
    private HttpSession session;

    /**
     * Metoda do pobrania aktualnego użytkownika z sesji.
     * @return aktualny użytkownik (User) przechowywany w sesji.
     */
    public User getCurrentUser() {
        return (User) session.getAttribute("currentUser");
    }

    /**
     * Metoda do ustawienia użytkownika w sesji.
     * @param user użytkownik, który ma być zapisany w sesji.
     */
    public void setCurrentUser(User user) {
        session.setAttribute("currentUser", user);
    }

    /**
     * Metoda do usunięcia użytkownika z sesji.
     */
    public void removeCurrentUser() {
        session.removeAttribute("currentUser");
    }

    /**
     * Metoda sprawdzająca, czy użytkownik jest zalogowany.
     * @return true, jeśli użytkownik jest zalogowany (czyli nie jest null), false w przeciwnym przypadku.
     */
    public boolean isUserLoggedIn() {
        return getCurrentUser() != null;
    }
}

