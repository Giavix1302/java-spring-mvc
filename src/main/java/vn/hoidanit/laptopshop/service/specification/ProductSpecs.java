package vn.hoidanit.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
  public static Specification<Product> nameLike(String name) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
  }

  public static Specification<Product> minPrice(Number minPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), minPrice);
  }

  public static Specification<Product> maxPrice(Number maxPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.lt(root.get(Product_.PRICE), maxPrice);
  }

  public static Specification<Product> equalFactory(List<String> factories) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factories);
  }
}