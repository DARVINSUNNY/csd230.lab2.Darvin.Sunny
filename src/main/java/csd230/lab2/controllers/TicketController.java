package csd230.lab2.controllers;

import csd230.lab2.entities.Ticket;
import csd230.lab2.entities.Cart;
import csd230.lab2.respositories.TicketRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final CartRepository cartRepository;

    public TicketController(TicketRepository ticketRepository, CartRepository cartRepository) {
        this.ticketRepository = ticketRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "ticket"; // expects tickets.html
    }

    @GetMapping("/add")
    public String showAddTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "add-ticket";
    }

    @PostMapping("/add")
    public String processAddTicket(@ModelAttribute Ticket ticket) {
        ticketRepository.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit")
    public String showEditTicketForm(@RequestParam("id") Long id, Model model) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }

    @PostMapping("/edit")
    public String processEditTicket(@ModelAttribute Ticket ticket) {
        ticketRepository.save(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/selection")
    public String processSelection(@RequestParam("selectedTickets") List<Long> selectedTicketIds,
                                   @RequestParam("action") String action) {
        if ("delete".equalsIgnoreCase(action)) {
            for (Long id : selectedTicketIds) {
                Ticket ticket = ticketRepository.findById(id).orElse(null);
                if (ticket != null) {
                    ticketRepository.delete(ticket);
                }
            }
        } else if ("addToCart".equalsIgnoreCase(action)) {
            Cart cart = cartRepository.findAll().iterator().next();
            for (Long id : selectedTicketIds) {
                Ticket ticket = ticketRepository.findById(id).orElse(null);
                if (ticket != null) {
                    cart.addItem(ticket);
                }
            }
            cartRepository.save(cart);
        }
        return "redirect:/tickets";
    }
}
