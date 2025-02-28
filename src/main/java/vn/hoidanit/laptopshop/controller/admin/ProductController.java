package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;

    public ProductController(UploadService uploadService, PasswordEncoder passwordEncoder,
            ProductService productService) {
        this.productService = productService;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product/create") // duong link
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create"; // duong dan file
    }

    // add product
    @PostMapping(value = "/admin/product/create")
    public String postCreateUser(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("productFile") MultipartFile file) {
        // validate
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        //
        String image = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(image);
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    // list product
    @RequestMapping("/admin/product")
    public String getProductsPage(Model model) {
        List<Product> listProduct = this.productService.getAllProduct();
        System.out.println(listProduct);
        model.addAttribute("products", listProduct);
        return "admin/product/show";
    }

}
