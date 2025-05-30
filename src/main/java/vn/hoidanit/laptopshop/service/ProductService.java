package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;
import vn.hoidanit.laptopshop.domain.User;
import org.springframework.stereotype.Service;

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

  public void handleAddProductToCart(String email, Long productId) {
    User user = this.userService.getUserByEmail(email);
    if (user != null) {
      Cart cart = this.cartRepository.findByUser(user);

      if (cart == null) {
        // taoj moi cart
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setSum(1);

        cart = this.cartRepository.save(newCart);
      }
      // save cart
      // find product
      Optional<Product> productOptional = this.productRepository.findById(productId);
      if (productOptional.isPresent()) {
        Product product = productOptional.get();

        CartDetail cartDetail = new CartDetail();
        
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setQuantity(1L);
        cartDetail.setPrice(product.getPrice());

        this.cartDetailRepository.save(cartDetail);

      }

    }

  }
}
