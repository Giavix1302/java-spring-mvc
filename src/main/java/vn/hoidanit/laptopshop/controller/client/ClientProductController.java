package vn.hoidanit.laptopshop.controller.client;

import org.springframework.ui.Model;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ClientProductController {

  private final ProductService productService;

  public ClientProductController(ProductService productService) {
    this.productService = productService;
  }

  @RequestMapping("/product/{id}")
  public String getProductDetailPage(Model model, @PathVariable Long id) {
    Product product = productService.getProductById(id).get();
    model.addAttribute("product", product);
    model.addAttribute("id", id);

    return "client/product/detail";
  }

  @PostMapping("/add-product-to-cart/{id}")
  public String addProductToCart(@PathVariable Long id, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    long productId = id;
    String email = (String) session.getAttribute("email");

    this.productService.handleAddProductToCart(email, productId, session); // Assuming cartId is 1 for demo purposes

    return "redirect:/";
  }

  @GetMapping("/cart")
  public String getCartPage(Model model, HttpServletRequest request) {
    User currentUser = new User();
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart.getCartDetails();

    double totalPrice = 0.0;
    for (CartDetail cartDetail : cartDetails) {
      totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);

    return "client/cart/show";
  }

}