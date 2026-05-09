package com.example.grocery.controller;

import com.example.grocery.model.CartItem;
import com.example.grocery.model.CheckoutForm;
import com.example.grocery.service.CartService;
import com.example.grocery.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private final ProductService productService;
    private final CartService cartService;

    public WebController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping({"/", "/products"})
    public String home(Model model, HttpSession session) {
        model.addAttribute("products", productService.findAll());
        addCartMeta(model, session);
        return "products";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        productService.findById(id).ifPresent(product -> cartService.addToCart(cart, product));
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        model.addAttribute("items", cartService.items(cart));
        model.addAttribute("total", cartService.total(cart));
        model.addAttribute("itemCount", cartService.count(cart));
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {
        cartService.removeFromCart(getCart(session), id);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkoutForm(Model model, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        model.addAttribute("items", cartService.items(cart));
        model.addAttribute("total", cartService.total(cart));
        model.addAttribute("checkoutForm", new CheckoutForm());
        model.addAttribute("itemCount", cartService.count(cart));
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@Valid CheckoutForm checkoutForm,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        List<CartItem> items = cartService.items(cart);
        if (items.isEmpty()) {
            return "redirect:/products";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("items", items);
            model.addAttribute("total", cartService.total(cart));
            model.addAttribute("itemCount", cartService.count(cart));
            return "checkout";
        }

        model.addAttribute("customerName", checkoutForm.getCustomerName());
        model.addAttribute("items", items);
        model.addAttribute("total", cartService.total(cart));
        cartService.clear(cart);
        return "success";
    }

    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCart(HttpSession session) {
        Object cart = session.getAttribute("cart");
        if (cart == null) {
            Map<Long, CartItem> newCart = cartService.createCart();
            session.setAttribute("cart", newCart);
            return newCart;
        }
        return (Map<Long, CartItem>) cart;
    }

    private void addCartMeta(Model model, HttpSession session) {
        model.addAttribute("itemCount", cartService.count(getCart(session)));
    }
}
