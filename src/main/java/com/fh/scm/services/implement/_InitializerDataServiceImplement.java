package com.fh.scm.services.implement;

import com.fh.scm.dto.api.pt.PaymentTermsRequest;
import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.enums.PaymentTermType;
import com.fh.scm.enums.UserRole;
import com.fh.scm.pojo.*;
import com.fh.scm.repository.*;
import com.fh.scm.services.UserService;
import com.fh.scm.services._InitializerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class _InitializerDataServiceImplement implements _InitializerDataService {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void createUser() {
        List<User> users = this.userService.getAll(null);

        if (users.isEmpty()) {
            this.userService.register(UserRequestRegister.builder()
                    .email("admin@scm.com")
                    .username("adminscm")
                    .password("admin@123")
                    .userRole(UserRole.ROLE_ADMIN)
                    .build());

            this.userService.register(UserRequestRegister.builder()
                    .email("customer1@scm.com")
                    .username("customer1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_CUSTOMER)
                    .firstName("CUSTOMER 1")
                    .middleName("M")
                    .lastName("L")
                    .address("TPHCM")
                    .phone("0123456789")
                    .build());

            this.userService.register(UserRequestRegister.builder()
                    .email("supplier1@scm.com")
                    .username("supplier1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_SUPPLIER)
                    .name("SUPPLIER 1")
                    .address("TPHCM")
                    .phone("1234567890")
                    .contactInfo("1234567890")
                    .paymentTermsSet(Set.of(
                            PaymentTermsRequest.builder()
                                    .discountDays(10)
                                    .discountPercentage(BigDecimal.valueOf(0.03))
                                    .type(PaymentTermType.COD)
                                    .build(),
                            PaymentTermsRequest.builder()
                                    .discountDays(20)
                                    .discountPercentage(BigDecimal.valueOf(0.05))
                                    .type(PaymentTermType.PREPAID)
                                    .build()
                    ))
                    .build());

            this.userService.register(UserRequestRegister.builder()
                    .email("shipper1@scm.com")
                    .username("shipper1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_SHIPPER)
                    .name("SHIPPER 1")
                    .contactInfo("0987654321")
                    .build());
        }
    }

    @Override
    public void createCategory() {
        List.of(
                Category.builder().name("Thiết Bị Mạng").description("Thiết Bị Mạng").build(),
                Category.builder().name("Thiết Bị Di Động").description("Thiết Bị Di Động").build(),
                Category.builder().name("Công Nghệ Đám Mây").description("Công Nghệ Đám Mây").build(),
                Category.builder().name("Lưu Trữ và Đám Mây").description("Lưu Trữ và Đám Mây").build(),
                Category.builder().name("An Ninh và Bảo Mật").description("An Ninh và Bảo Mật").build(),
                Category.builder().name("Phần Mềm và Ứng Dụng").description("Phần Mềm và Ứng Dụng").build(),
                Category.builder().name("Máy Tính và Phụ Kiện").description("Máy Tính và Phụ Kiện").build(),
                Category.builder().name("Thiết Bị Đầu Vào và Đầu Ra").description("Thiết Bị Đầu Vào và Đầu Ra").build(),
                Category.builder().name("Phát Triển Web và Ứng Dụng").description("Phát Triển Web và Ứng Dụng").build(),
                Category.builder().name("Khoa Học Dữ Liệu và Trí Tuệ Nhân Tạo").description("Khoa Học Dữ Liệu và Trí Tuệ Nhân Tạo").build()
        ).forEach(category -> this.categoryRepository.insert(category));
    }

    @Override
    public void createTag() {
        List.of(
                Tag.builder().name("Artificial Intelligence").description("Artificial Intelligence").build(),
                Tag.builder().name("Cybersecurity").description("Cybersecurity").build(),
                Tag.builder().name("Cloud Computing").description("Cloud Computing").build(),
                Tag.builder().name("Data Science").description("Data Science").build(),
                Tag.builder().name("Blockchain").description("Blockchain").build(),
                Tag.builder().name("DevOps").description("DevOps").build(),
                Tag.builder().name("Machine Learning").description("Machine Learning").build(),
                Tag.builder().name("Internet of Things").description("Internet of Things").build(),
                Tag.builder().name("Big Data").description("Big Data").build(),
                Tag.builder().name("Software Development").description("Software Development").build()
        ).forEach(tag -> this.tagRepository.insert(tag));
    }

    @Override
    public void createUnit() {
        List.of(
                Unit.builder().name("Piece").abbreviation("PCS").build(),
                Unit.builder().name("Box").abbreviation("BOX").build(),
                Unit.builder().name("Kilogram").abbreviation("KG").build(),
                Unit.builder().name("Gram").abbreviation("G").build(),
                Unit.builder().name("Liter").abbreviation("L").build(),
                Unit.builder().name("Meter").abbreviation("M").build(),
                Unit.builder().name("Centimeter").abbreviation("CM").build(),
                Unit.builder().name("Package").abbreviation("PKG").build(),
                Unit.builder().name("Dozen").abbreviation("DZ").build(),
                Unit.builder().name("Roll").abbreviation("RL").build()
        ).forEach(unit -> this.unitRepository.insert(unit));
    }

    @Override
    public void createProduct() {
        AtomicInteger count = new AtomicInteger(1);
        Random random = new Random();

        List<Tag> tags = this.tagRepository.getAll(null);
        List<Unit> units = this.unitRepository.getAll(null);

        this.categoryRepository.getAll(null).forEach(category -> {
            for (int i = 1; i <= 3; i++) {
                Collections.shuffle(tags, random);
                Set<Tag> randomTags = tags.stream()
                        .limit(2)
                        .collect(Collectors.toSet());

                Collections.shuffle(units, random);
                Set<Unit> randomUnits = units.stream()
                        .limit(2)
                        .collect(Collectors.toSet());

                Product product = Product.builder()
                        .name("Product " + count)
                        .price(new BigDecimal(100000))
                        .description("Product " + count + " " + category.getName())
                        .expiryDate(new Date())
                        .category(category)
                        .tagSet(randomTags)
                        .unitSet(randomUnits)
                        .build();

                this.productRepository.insert(product);
                count.getAndIncrement();
            }
        });
    }

    @Override
    public void createTax() {
        List.of(
                Tax.builder().rate(BigDecimal.valueOf(0.01)).region("VN").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.05)).region("US").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.20)).region("EU").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.10)).region("APAC").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.15)).region("LATAM").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.08)).region("MEA").build()
        ).forEach(this.taxRepository::insert);
    }

    @Override
    public void createWarehouse() {
        List.of(
                Warehouse.builder().name("Warehouse 1").location("TPHCM").capacity(1000.0F).cost(new BigDecimal(100000)).build(),
                Warehouse.builder().name("Warehouse 2").location("Hà Nội").capacity(2000.0F).cost(new BigDecimal(200000)).build(),
                Warehouse.builder().name("Warehouse 3").location("Đà Nẵng").capacity(3000.0F).cost(new BigDecimal(300000)).build(),
                Warehouse.builder().name("Warehouse 4").location("Cần Thơ").capacity(4000.0F).cost(new BigDecimal(400000)).build(),
                Warehouse.builder().name("Warehouse 5").location("Hải Phòng").capacity(5000.0F).cost(new BigDecimal(500000)).build()
        ).forEach(this.warehouseRepository::insert);
    }

    @Override
    public void createInventory() {
        List<Product> products = this.productRepository.getAll(null);

        this.warehouseRepository.getAll(null).forEach(warehouse -> {
            products.forEach(product -> {
                Inventory inventory = Inventory.builder()
                        .name("Inventory " + product.getName() + " " + warehouse.getName())
                        .quantity(100.0F)
                        .warehouse(warehouse)
                        .build();
                this.inventoryRepository.insert(inventory);

                product.setInventory(inventory);
                this.productRepository.update(product);
            });
        });
    }
}
