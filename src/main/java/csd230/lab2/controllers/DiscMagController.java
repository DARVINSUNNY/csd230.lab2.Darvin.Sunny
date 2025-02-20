package csd230.lab2.controllers;

import csd230.lab2.entities.DiscMag;
import csd230.lab2.entities.Cart;
import csd230.lab2.respositories.DiscMagRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discmags")
public class DiscMagController {

    private final DiscMagRepository discMagRepository;
    private final CartRepository cartRepository;

    public DiscMagController(DiscMagRepository discMagRepository, CartRepository cartRepository) {
        this.discMagRepository = discMagRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public String listDiscMags(Model model) {
        model.addAttribute("discmags", discMagRepository.findAll());
        return "discmag"; // expects discmags.html
    }

    @GetMapping("/add")
    public String showAddDiscMagForm(Model model) {
        model.addAttribute("discMag", new DiscMag());
        return "add-discmag";
    }

    @PostMapping("/add")
    public String processAddDiscMag(@ModelAttribute DiscMag discMag) {
        discMagRepository.save(discMag);
        return "redirect:/discmags";
    }

    @GetMapping("/edit")
    public String showEditDiscMagForm(@RequestParam("id") Long id, Model model) {
        DiscMag discMag = discMagRepository.findById(id).orElse(null);
        model.addAttribute("discMag", discMag);
        return "edit-discmag";
    }

    @PostMapping("/edit")
    public String processEditDiscMag(@ModelAttribute DiscMag discMag) {
        discMagRepository.save(discMag);
        return "redirect:/discmags";
    }

    @PostMapping("/selection")
    public String processSelection(@RequestParam("selectedDiscMags") List<Long> selectedDiscMagIds,
                                   @RequestParam("action") String action) {
        if ("delete".equalsIgnoreCase(action)) {
            for (Long id : selectedDiscMagIds) {
                DiscMag dm = discMagRepository.findById(id).orElse(null);
                if (dm != null) {
                    discMagRepository.delete(dm);
                }
            }
        } else if ("addToCart".equalsIgnoreCase(action)) {
            Cart cart = cartRepository.findAll().iterator().next();
            for (Long id : selectedDiscMagIds) {
                DiscMag dm = discMagRepository.findById(id).orElse(null);
                if (dm != null) {
                    cart.addItem(dm);
                }
            }
            cartRepository.save(cart);
        }
        return "redirect:/discmags";
    }
}
