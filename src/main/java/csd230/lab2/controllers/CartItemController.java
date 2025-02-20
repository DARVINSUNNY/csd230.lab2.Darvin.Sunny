package csd230.lab2.controllers;

import csd230.lab2.entities.CartItem;
import csd230.lab2.respositories.CartItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cartitems")
public class CartItemController {

    private final CartItemRepository cartItemRepository;

    public CartItemController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // List all cart items
    @GetMapping
    public String listCartItems(Model model) {
        model.addAttribute("cartItems", cartItemRepository.findAll());
        return "cartitems"; // returns cartitems.html
    }

    // Show form to add a new cart item
    @GetMapping("/add")
    public String showAddCartItemForm(Model model) {
        model.addAttribute("cartItem", new CartItem());
        return "add-cartitem"; // returns add-cartitem.html
    }

    // Process adding a new cart item
    @PostMapping("/add")
    public String processAddCartItem(@ModelAttribute CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return "redirect:/cartitems";
    }

    // Show form to edit an existing cart item
    @GetMapping("/edit")
    public String showEditCartItemForm(@RequestParam("id") Long id, Model model) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        model.addAttribute("cartItem", cartItem);
        return "edit-cartitem"; // returns edit-cartitem.html
    }

    // Process editing the cart item
    @PostMapping("/edit")
    public String processEditCartItem(@ModelAttribute CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return "redirect:/cartitems";
    }

    // Delete a cart item
    @PostMapping("/delete")
    public String deleteCartItem(@RequestParam("id") Long id) {
        cartItemRepository.deleteById(id);
        return "redirect:/cartitems";
    }
}
