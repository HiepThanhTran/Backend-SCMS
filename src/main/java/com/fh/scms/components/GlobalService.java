package com.fh.scms.components;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fh.scms.dto.pt.PaymentTermsRequest;
import com.fh.scms.dto.user.UserRequestRegister;
import com.fh.scms.enums.PaymentTermType;
import com.fh.scms.enums.UserRole;
import com.fh.scms.pojo.*;
import com.fh.scms.repository.*;
import com.fh.scms.services.UserService;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Transactional
public class GlobalService {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Cloudinary cloudinary;
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

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @SuppressWarnings("rawtypes")
    public String uploadImage(@NotNull MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            return null;
        }
    }

    public Boolean isFirstRun() {
        return this.getCurrentSession().get(_System.class, 1L) != null;
    }

    public void saveFirstRun() {
        Session session = this.getCurrentSession();
        _System system = _System.builder().name("isFirstRun").build();
        session.persist(system);
    }

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
        ).forEach(category -> this.categoryRepository.save(category));
    }

    public void createTag() {
        List.of(
                Tag.builder().name("Trí Tuệ Nhân Tạo").description("Trí Tuệ Nhân Tạo").build(),
                Tag.builder().name("An Ninh Mạng").description("An Ninh Mạng").build(),
                Tag.builder().name("Điện Toán Đám Mây").description("Điện Toán Đám Mây").build(),
                Tag.builder().name("Khoa Học Dữ Liệu").description("Khoa Học Dữ Liệu").build(),
                Tag.builder().name("Chuỗi Khối").description("Chuỗi Khối").build(),
                Tag.builder().name("Phát Triển và Vận Hành").description("Phát Triển và Vận Hành").build(),
                Tag.builder().name("Học Máy").description("Học Máy").build(),
                Tag.builder().name("Internet Vạn Vật").description("Internet Vạn Vật").build(),
                Tag.builder().name("Dữ Liệu Lớn").description("Dữ Liệu Lớn").build(),
                Tag.builder().name("Phát Triển Phần Mềm").description("Phát Triển Phần Mềm").build()
        ).forEach(tag -> this.tagRepository.save(tag));

    }

    public void createUnit() {
        List.of(
                Unit.builder().name("Cái").abbreviation("PCS").build(),
                Unit.builder().name("Hộp").abbreviation("BOX").build(),
                Unit.builder().name("Kilogram").abbreviation("KG").build(),
                Unit.builder().name("Grams").abbreviation("G").build(),
                Unit.builder().name("Lít").abbreviation("L").build(),
                Unit.builder().name("Mét").abbreviation("M").build(),
                Unit.builder().name("Centimet").abbreviation("CM").build(),
                Unit.builder().name("Gói").abbreviation("PKG").build(),
                Unit.builder().name("Tá").abbreviation("DZ").build(),
                Unit.builder().name("Cuộn").abbreviation("RL").build()
        ).forEach(unit -> this.unitRepository.save(unit));
    }

    public void createTax() {
        List.of(
                Tax.builder().rate(BigDecimal.valueOf(0.01)).region("VN").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.05)).region("US").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.20)).region("EU").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.10)).region("APAC").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.15)).region("LATAM").build(),
                Tax.builder().rate(BigDecimal.valueOf(0.08)).region("MEA").build()
        ).forEach(this.taxRepository::save);
    }

    public void createWarehouse() {
        List.of(
                Warehouse.builder().name("Warehouse 1").location("TPHCM").capacity(50000000.0F).cost(new BigDecimal(100000)).build(),
                Warehouse.builder().name("Warehouse 2").location("Hà Nội").capacity(500000.0F).cost(new BigDecimal(200000)).build(),
                Warehouse.builder().name("Warehouse 3").location("Đà Nẵng").capacity(150000000.0F).cost(new BigDecimal(300000)).build(),
                Warehouse.builder().name("Warehouse 4").location("Cần Thơ").capacity(200000000.0F).cost(new BigDecimal(400000)).build(),
                Warehouse.builder().name("Warehouse 5").location("Hải Phòng").capacity(250000000.0F).cost(new BigDecimal(500000)).build()
        ).forEach(this.warehouseRepository::save);
    }

    public void createUser() {
        List<User> users = this.userService.findAllWithFilter(null);

        if (users.isEmpty()) {
            this.userService.registerUser(UserRequestRegister.builder()
                    .email("admin@scm.com")
                    .username("adminscm")
                    .password("admin@123")
                    .userRole(UserRole.ROLE_ADMIN)
                    .build());

            this.userService.registerUser(UserRequestRegister.builder()
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

            this.userService.registerUser(UserRequestRegister.builder()
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

            this.userService.registerUser(UserRequestRegister.builder()
                    .email("shipper1@scm.com")
                    .username("shipper1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_SHIPPER)
                    .name("SHIPPER 1")
                    .contactInfo("0987654321")
                    .build());
        }
    }

    public void createProduct() {
        AtomicInteger count = new AtomicInteger(1);
        Random random = new Random();

        List<Unit> units = this.unitRepository.findAllWithFilter(null);
        List<Category> categories = this.categoryRepository.findAllWithFilter(null);
        List<Tag> tags = this.tagRepository.findAllWithFilter(null);

        // Tạo sản phẩm hết hạn
        this.createProductsWithExpiryDates(count, random, -30, units, categories, tags);

        // Tạo sản phẩm sắp hết hạn
        this.createProductsWithExpiryDates(count, random, 15, units, categories, tags);

        // Tạo sản phẩm còn hạn
        this.createProductsWithExpiryDates(count, random, 60, units, categories, tags);
    }

    public void createInventory() {
        List<Product> products = this.productRepository.findAllWithFilter(null);
        List<Warehouse> warehouses = this.warehouseRepository.findAllWithFilter(null);
        Random random = new Random();
        AtomicInteger count = new AtomicInteger(1);

        warehouses.forEach(warehouse -> {
            // Tạo nhiều Inventory cho từng Warehouse
            IntStream.range(0, 10).forEach(index -> { // Ví dụ: tạo 10 Inventory cho mỗi warehouse
                Inventory inventory = Inventory.builder()
                        .name("Inventory " + count)
                        .warehouse(warehouse)
                        .build();

                Collections.shuffle(products, new Random());
                int numberOfProductsToReturn = 50 + new Random().nextInt(100 - 50 + 1);
                List<Product> randomProducts = products.stream()
                        .limit(numberOfProductsToReturn)
                        .collect(Collectors.toList());

                Set<InventoryDetails> inventoryDetailsSet = randomProducts.stream()
                        .map(product -> InventoryDetails.builder()
                                .quantity(100 + (random.nextFloat() * (1000 - 100)))
                                .product(product)
                                .inventory(inventory)
                                .build())
                        .collect(Collectors.toSet());

                inventory.setInventoryDetailsSet(inventoryDetailsSet);
                this.inventoryRepository.save(inventory);

                count.getAndIncrement();
            });
        });
    }

    private void createProductsWithExpiryDates(AtomicInteger count, Random random, int daysFromNow,
                                               List<Unit> units, @NotNull List<Category> categories, List<Tag> tags) {
        categories.forEach(category -> {
            for (int i = 0; i < 10; i++) {
                BigDecimal price = BigDecimal.valueOf(50000 + (random.nextDouble() * (1000000 - 50000)));

                String description = "Product " + count + " " + category.getName();

                LocalDate expiryDate = LocalDate.now().plusDays(daysFromNow);

                Collections.shuffle(tags, random);
                Set<Tag> randomTags = tags.stream()
                        .limit(2)
                        .collect(Collectors.toSet());

                Unit unit = units.get(random.nextInt(units.size()));

                Product product = Product.builder()
                        .name("Product " + count)
                        .price(price)
                        .unit(unit)
                        .description(description)
                        .expiryDate(expiryDate)
                        .category(category)
                        .tagSet(randomTags)
                        .build();

                this.productRepository.save(product);
                count.getAndIncrement();
            }
        });
    }
}
