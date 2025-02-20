package csd230.lab2.controllers;

import csd230.lab2.entities.Magazine;
import csd230.lab2.entities.Cart;
import csd230.lab2.respositories.MagazineRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineRepository magazineRepository;
    private final CartRepository cartRepository;

    public MagazineController(MagazineRepository magazineRepository, CartRepository cartRepository) {
        this.magazineRepository = magazineRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public String listMagazines(Model model) {
        model.addAttribute("magazines", magazineRepository.findAll());
        return "magazine"; // expects magazines.html
    }

    @GetMapping("/add")
    public String showAddMagazineForm(Model model) {
        model.addAttribute("magazine", new Magazine());
        return "add-magazine";
    }

    @PostMapping("/add")
    public String processAddMagazine(@ModelAttribute Magazine magazine) {
        magazineRepository.save(magazine);
        return "redirect:/magazines";
    }

    @GetMapping("/edit")
    public String showEditMagazineForm(@RequestParam("id") Long id, Model model) {
        Magazine magazine = magazineRepository.findById(id).orElse(null);
        model.addAttribute("magazine", magazine);
        return "edit-magazine";
    }

    @PostMapping("/edit")
    public String processEditMagazine(@ModelAttribute Magazine magazine) {
        magazineRepository.save(magazine);
        return "redirect:/magazines";
    }

    @PostMapping("/selection")
    public String processSelection(@RequestParam("selectedMagazines") List<Long> selectedMagazineIds,
                                   @RequestParam("action") String action) {
        if ("delete".equalsIgnoreCase(action)) {
            for (Long id : selectedMagazineIds) {
                Magazine mag = magazineRepository.findById(id).orElse(null);
                if (mag != null) {
                    magazineRepository.delete(mag);
                }
            }
        } else if ("addToCart".equalsIgnoreCase(action)) {
            Cart cart = cartRepository.findAll().iterator().next();
            for (Long id : selectedMagazineIds) {
                Magazine mag = magazineRepository.findById(id).orElse(null);
                if (mag != null) {
                    cart.addItem(mag);
                }
            }
            cartRepository.save(cart);
        }
        return "redirect:/magazines";
    }
}
