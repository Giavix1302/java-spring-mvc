package vn.hoidanit.laptopshop.controller.client;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Product;
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

    this.productService.handleAddProductToCart(email, productId); // Assuming cartId is 1 for demo purposes

    return "redirect:/";
  }

}