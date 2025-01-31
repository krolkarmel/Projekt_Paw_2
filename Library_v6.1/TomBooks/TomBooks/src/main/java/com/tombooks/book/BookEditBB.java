package com.tombooks.book;

import com.tombooks.dao.BookDAO;
import com.tombooks.entities.Book;
import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@Named
@ViewScoped
public class BookEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOK_LIST = "bookList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Book book = new Book();
	private Book loaded = null;

	@EJB
	BookDAO bookDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Book getBook() {
		return book;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Book) flash.get("book");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			book = loaded;
			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (book.getId()== null) {
				bookDAO.create(book);
			} else {
				// existing record
				bookDAO.update(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_BOOK_LIST;
	}
}