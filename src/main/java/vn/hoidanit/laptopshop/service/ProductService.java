package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
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
}
