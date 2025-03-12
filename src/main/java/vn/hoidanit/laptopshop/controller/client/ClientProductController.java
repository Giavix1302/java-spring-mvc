package vn.hoidanit.laptopshop.controller.client;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

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

}