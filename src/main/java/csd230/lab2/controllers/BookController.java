package csd230.lab2.controllers;

import csd230.lab2.entities.Book;
import csd230.lab2.entities.Cart;
import csd230.lab2.respositories.BookRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    public BookController(BookRepository bookRepository, CartRepository cartRepository) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public String books(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books"; // expects books.html
    }

    @GetMapping("/add-book")
    public String bookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String bookSubmit(@ModelAttribute Book book) {
        book.setDescription("Book: " + book.getTitle());
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit-book")
    public String edit_book(@RequestParam("id") Long id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/edit-book")
    public String edit_bookSubmit(@ModelAttribute Book book) {
        book.setDescription("Book: " + book.getTitle());
        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping("/selection")
    public String processSelection(@RequestParam("selectedBooks") List<Long> selectedBookIds,
                                   @RequestParam("action") String action) {
        if ("delete".equalsIgnoreCase(action)) {
            for (Long id : selectedBookIds) {
                Book book = bookRepository.findById(id).orElse(null);
                if (book != null) {
                    bookRepository.delete(book);
                }
            }
        } else if ("addToCart".equalsIgnoreCase(action)) {
            // Retrieve a cart—for demo purposes, we take the first cart
            Cart cart = cartRepository.findAll().iterator().next();
            for (Long id : selectedBookIds) {
                Book book = bookRepository.findById(id).orElse(null);
                if (book != null) {
                    cart.addItem(book);
                }
            }
            cartRepository.save(cart);
        }
        return "redirect:/books";
    }
}
