package vn.hoidanit.laptopshop.controller.client;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    this.productService.handleAddProductToCart(email, productId, session, 1); // Assuming cartId is 1 for demo purposes

    return "redirect:/";
  }

  @GetMapping("/cart")
  public String getCartPage(Model model, HttpServletRequest request) {
    User currentUser = new User();
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

    double totalPrice = 0.0;
    for (CartDetail cartDetail : cartDetails) {
      totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);
    model.addAttribute("cart", cart);

    return "client/cart/show";
  }

  @PostMapping("/delete-cart-product/{id}")
  public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    long cartDetailId = id;
    this.productService.handleRemoveCartDetail(cartDetailId, session);
    return "redirect:/cart";
  }

  @GetMapping("/checkout")
  public String getCheckOutPage(Model model, HttpServletRequest request) {
    User currentUser = new User();// null
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

    double totalPrice = 0;
    for (CartDetail cd : cartDetails) {
      totalPrice += cd.getPrice() * cd.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);

    return "client/cart/checkout";
  }

  @PostMapping("/confirm-checkout")
  public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
    // List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() :
    // cart.getCartDetails();
    List<CartDetail> cartDetails = new ArrayList<CartDetail>();
    this.productService.handleUpdateCartBeforeCheckout(cartDetails);
    return "redirect:/checkout";
  }

  @PostMapping("/place-order")
  public String handlePlaceOrder(
      HttpServletRequest request,
      @RequestParam("receiverName") String receiverName,
      @RequestParam("receiverAddress") String receiverAddress,
      @RequestParam("receiverPhone") String receiverPhone) {

    User currentUser = new User();
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

    return "redirect:/thanks";
  }

  @GetMapping("/thanks")
  public String getThanksPage(Model model) {

    return "client/cart/thanks";
  }

  @PostMapping("/add-product-from-view-detail")
  public String handleAddProductFromViewDetail(
      @RequestParam("id") long id,
      @RequestParam("quantity") long quantity,
      HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    String email = (String) session.getAttribute("email");
    this.productService.handleAddProductToCart(email, id, session, quantity);
    return "redirect:/product/" + id;
  }

  @GetMapping("/products")
  public String getProductPage(
      Model model,
      @RequestParam("page") Optional<String> pageOptional,
      @RequestParam("name") Optional<String> nameOptional,
      @RequestParam("price") Optional<String> priceOptional,
      @RequestParam("factory") Optional<String> factoryOptional,
      @RequestParam("target") Optional<String> targetOptional,
      @RequestParam("sort") Optional<String> sortOptional
  ) {
    int page = 1;
    try {
      if (pageOptional.isPresent()) {
        // convert from String to int
        page = Integer.parseInt(pageOptional.get());
      } else {
        // page = 1
      }
    } catch (Exception e) {
      // page = 1
      // TODO: handle exception
    }

    Pageable pageable = PageRequest.of(page - 1, 60);

    String name = nameOptional.isPresent() ? nameOptional.get() : "";
    Page<Product> prs = this.productService.getAllProductWithSpec(pageable,
    name);

    // case: min price
    // Double minPrice = minPriceOptional.isPresent() ?
    // Double.parseDouble(minPriceOptional.get()) : 0;
    // Page<Product> prs = this.productService.getAllProductWithMinPrice(pageable,
    // minPrice);

    // case: max price
    // Double maxPrice = maxPriceOptional.isPresent() ? Double.parseDouble(maxPriceOptional.get()) : 0;
    // Page<Product> prs = this.productService.getAllProductWithMaxPrice(pageable, maxPrice);


    // case: factory
    // String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";
    // Page<Product> prs = this.productService.getAllProductWithFactory(pageable, factory);

    // case: many factory
    // List<String> factories = Arrays.asList(factoryOptional.get().split(","));
    // Page<Product> prs = this.productService.getAllProductWithFactory(pageable, factories);

    // case: min < price < max
    // String price = priceOptional.isPresent() ? priceOptional.get() : "";
    // Page<Product> prs = this.productService.getAllProductWithPriceRange(pageable, price);

    // case many min < price < max
    // List<String> prices = Arrays.asList(priceOptional.get().split(","));
    // Page<Product> prs = this.productService.getAllProductWithPriceRange(pageable, prices);


    // if (minPriceOptional.isPresent()) {
    // prs = this.productService.getAllProductWithMinPrice(pageable, minPrice);
    // } else if (maxPriceOptional.isPresent()) {
    // prs = this.productService.getAllProductWithMaxPrice(pageable,
    // Double.parseDouble(maxPriceOptional.get()));
    // } else if (factoryOptional.isPresent()) {
    // System.out.println("-------------------------" + factoryOptional.get());
    // // prs = this.productService.getAllProductWithFactory(pageable,
    // // factoryOptional.get());
    // } else {
    // prs = this.productService.getAllProductWithSpec(pageable, name);
    // }
    // prs = this.productService.getAllProductWithSpec(pageable, name);

    List<Product> products = prs.getContent();

    model.addAttribute("products", products);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", prs.getTotalPages());
    return "client/product/show";
  }

}