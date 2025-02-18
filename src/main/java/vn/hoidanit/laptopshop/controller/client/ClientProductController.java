package vn.hoidanit.laptopshop.controller.client;

import org.springframework.ui.Model;  
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ClientProductController {

  @RequestMapping("/product/{id}")
  public String getProductDetailPage(Model model, @PathVariable Long id) {
    return "client/product/detail";
  }

}