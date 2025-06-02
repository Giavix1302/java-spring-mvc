package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;
import vn.hoidanit.laptopshop.domain.User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final CartDetailRepository cartDetailRepository;
  private final UserService userService;

  public ProductService(
      ProductRepository productRepository,
      CartRepository cartRepository,
      CartDetailRepository cartDetailRepository,
      UserService userService) {
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.cartDetailRepository = cartDetailRepository;
    this.userService = userService;
  }

  public Product handleSaveProduct(Product product) {
    Product _product = this.productRepository.save(product);
    System.out.println("Product saved: " + _product);
    return _product;
  }

  public List<Product> getAllProduct() {
    List<Product> listProduct = this.productRepository.findAll();
    System.out.println(listProduct);
    return listProduct;
  }

  public Optional<Product> getProductById(Long id) {
    return this.productRepository.findById(id);
  }

  public void deleteProductById(Long id) {
    this.productRepository.deleteById(id);
  }

  public void handleAddProductToCart(String email, long productId, HttpSession session) {
    User user = this.userService.getUserByEmail(email);
    if (user != null) {
      // check user đã có Cart chưa ? nếu chưa -> tạo mới
      Cart cart = this.cartRepository.findByUser(user);

      if (cart == null) {
        // tạo mới cart
        Cart otherCart = new Cart();
        otherCart.setUser(user);
        otherCart.setSum(0);

        cart = this.cartRepository.save(otherCart);
      }

      // save cart_detail
      // tìm product by id

      Optional<Product> productOptional = this.productRepository.findById(productId);
      if (productOptional.isPresent()) {
        Product realProduct = productOptional.get();

        // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
        CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
        //
        if (oldDetail == null) {
          CartDetail cd = new CartDetail();
          cd.setCart(cart);
          cd.setProduct(realProduct);
          cd.setPrice(realProduct.getPrice());
          cd.setQuantity(1l);
          this.cartDetailRepository.save(cd);

          // update cart (sum);
          int s = cart.getSum() + 1;
          cart.setSum(s);
          this.cartRepository.save(cart);
          session.setAttribute("sum", s);
        } else {
          oldDetail.setQuantity(oldDetail.getQuantity() + 1);
          this.cartDetailRepository.save(oldDetail);
        }

      }

    }
  }

  public Cart fetchByUser(User user) {
    return this.cartRepository.findByUser(user);
  }

}
