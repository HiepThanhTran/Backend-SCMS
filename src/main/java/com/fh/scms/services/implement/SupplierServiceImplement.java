package com.fh.scms.services.implement;

import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.dto.product.ProductRequestPublish;
import com.fh.scms.dto.product.ProductResponseForDetails;
import com.fh.scms.dto.product.ProductResponseForList;
import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.pojo.*;
import com.fh.scms.repository.*;
import com.fh.scms.services.OrderService;
import com.fh.scms.services.ProductService;
import com.fh.scms.services.SupplierService;
import com.fh.scms.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImplement implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @Override
    public Supplier findById(Long id) {
        return this.supplierRepository.findById(id);
    }

    @Override
    public void save(Supplier supplier) {
        this.supplierRepository.save(supplier);
    }

    @Override
    public void update(Supplier supplier) {
        this.supplierRepository.update(supplier);
    }

    @Override
    public void delete(Long id) {
        Supplier supplier = this.supplierRepository.findById(id);
        User user = supplier.getUser();
        List<Invoice> invoices = new ArrayList<>(user.getInvoiceSet());
        invoices.forEach(invoice -> {
            invoice.setUser(null);
            this.invoiceRepository.update(invoice);
        });

        this.supplierRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.supplierRepository.count();
    }

    @Override
    public List<Supplier> findAllWithFilter(Map<String, String> params) {
        return this.supplierRepository.findAllWithFilter(params);
    }

    @Override
    public SupplierDTO getSupplierResponse(@NotNull Supplier supplier) {
        return SupplierDTO.builder()
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .contactInfo(supplier.getContactInfo())
                .build();
    }

    @Override
    public Supplier getProfileSupplier(String username) {
        User user = this.userRepository.findByUsername(username);

        return this.supplierRepository.findByUser(user);
    }

    @Override
    public SupplierDTO updateProfileSupplier(String username, SupplierDTO supplierDTO) {
        User user = this.userRepository.findByUsername(username);

        if (!user.getConfirm()) {
            throw new IllegalArgumentException("Tài khoản chưa được xác nhận");
        }

        Supplier supplier = this.supplierRepository.findByUser(user);

        Field[] fields = SupplierDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(supplierDTO);

                if (value != null && !value.toString().isEmpty()) {
                    Field supplierField = Supplier.class.getDeclaredField(field.getName());
                    supplierField.setAccessible(true);
                    Object convertedValue = Utils.convertValue(supplierField.getType(), value.toString());
                    supplierField.set(supplier, convertedValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        this.supplierRepository.update(supplier);

        return this.getSupplierResponse(supplier);
    }

    @Override
    public ProductResponseForDetails publishProduct(String username, @NotNull ProductRequestPublish productRequestPublish) {
        Supplier supplier = this.getProfileSupplier(username);
        Optional.ofNullable(supplier).orElseThrow(() -> new EntityNotFoundException("Nhà cung cấp không tồn tại"));

        Product product = Product.builder()
                .name(productRequestPublish.getName())
                .description(productRequestPublish.getDescription())
                .price(productRequestPublish.getPrice())
                .expiryDate(productRequestPublish.getExpiryDate())
                .supplier(supplier)
                .unit(this.unitRepository.findById(productRequestPublish.getUnit()))
                .category(this.categoryRepository.findById(productRequestPublish.getCategory()))
                .tagSet(productRequestPublish.getTags().stream().map(this.tagRepository::findById).collect(Collectors.toSet()))
                .build();

        this.productService.save(product);

        return this.productService.getProductResponseForDetails(product);
    }

    @Override
    public void unpublishProduct(String username, Long productId) {
        Supplier supplier = this.getProfileSupplier(username);
        Optional.ofNullable(supplier).orElseThrow(() -> new EntityNotFoundException("Nhà cung cấp không tồn tại"));

        Product product = this.productService.findById(productId);
        Optional.ofNullable(product).orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));

        if (!Objects.equals(product.getSupplier().getId(), supplier.getId())) {
            throw new AccessDeniedException("Không thể thực hiện hành động này");
        }

        this.productService.delete(productId);
    }

    @Override
    public List<OrderResponse> getOrdersOfSupplier(@NotNull Long supplierId, Map<String, String> params) {
        List<Order> orders = this.orderService.findAllBySupplierId(supplierId, params);

        return this.orderService.getAllOrderResponse(orders);
    }

    @Override
    public List<Rating> getRatingsOfSupplier(Long supplierId) {
        Supplier supplier = this.supplierRepository.findById(supplierId);
        Optional.ofNullable(supplier).orElseThrow(() -> new EntityNotFoundException("Nhà cung cấp không tồn tại"));

        return new ArrayList<>(supplier.getRatingSet());
    }

    @Override
    public Rating addRatingForSupplier(String username, Long supplierId, RatingRequestCreate ratingRequestCreate) {
        User user = this.userRepository.findByUsername(username);
        Supplier supplier = this.supplierRepository.findById(supplierId);

        Optional.ofNullable(supplier).orElseThrow(() -> new EntityNotFoundException("Nhà cung cấp không tồn tại"));

        if (user.getSupplier() != null && Objects.equals(user.getSupplier().getId(), supplierId)) {
            throw new IllegalArgumentException("Không thể đánh giá chính mình");
        }

        Rating rating = this.ratingRepository.findByUserIdAndSupplierId(user.getId(), supplierId);

        if (rating == null) {
            rating = Rating.builder()
                    .user(user)
                    .supplier(supplier)
                    .rating(ratingRequestCreate.getRating())
                    .content(ratingRequestCreate.getContent())
                    .criteria(ratingRequestCreate.getCriteria())
                    .build();
            this.ratingRepository.save(rating);
        } else {
            rating.setRating(ratingRequestCreate.getRating());
            rating.setContent(ratingRequestCreate.getContent());
            rating.setCriteria(ratingRequestCreate.getCriteria());
            this.ratingRepository.update(rating);
        }

        return rating;
    }
}
