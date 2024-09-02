package com.fh.scms.components;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fh.scms.dto.order.OrderDetailsRequest;
import com.fh.scms.dto.order.OrderRequest;
import com.fh.scms.dto.pt.PaymentTermsRequest;
import com.fh.scms.dto.user.UserRequestRegister;
import com.fh.scms.enums.*;
import com.fh.scms.pojo.*;
import com.fh.scms.pojo.System;
import com.fh.scms.repository.*;
import com.fh.scms.services.OrderService;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
    @Autowired
    private InventoryDetailsRepository inventoryDetailsRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private OrderService orderService;

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
        return this.getCurrentSession().get(System.class, 1L) == null;
    }

    public void saveFirstRun() {
        Session session = this.getCurrentSession();
        System system = System.builder().name("isFirstRun").build();
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
                Warehouse.builder().name("Warehouse 2").location("Hà Nội").capacity(100000000.0F).cost(new BigDecimal(200000)).build(),
                Warehouse.builder().name("Warehouse 3").location("Đà Nẵng").capacity(150000000.0F).cost(new BigDecimal(300000)).build(),
                Warehouse.builder().name("Warehouse 4").location("Cần Thơ").capacity(200000000.0F).cost(new BigDecimal(400000)).build(),
                Warehouse.builder().name("Warehouse 5").location("Hải Phòng").capacity(250000000.0F).cost(new BigDecimal(500000)).build()
        ).forEach(this.warehouseRepository::save);
    }

    public void createUser() {
        this.userService.registerUser(UserRequestRegister.builder()
                .email("admin@scm.com")
                .username("adminscm")
                .password("admin@123")
                .userRole(UserRole.ROLE_ADMIN)
                .build());

        this.createCustomer();

        this.createSupplier();

        this.createShipper();
    }

    public void createProduct() {
        AtomicInteger count = new AtomicInteger(1);
        Random random = new Random();

        List<Category> categories = this.categoryRepository.findAllWithFilter(null);
        List<Unit> units = this.unitRepository.findAllWithFilter(null);
        List<Tag> tags = this.tagRepository.findAllWithFilter(null);

        categories.forEach(category -> {
            // Tạo sản phẩm hết hạn
            this.createProductsWithExpiryDates(count, random, -30, category, units, tags);

            // Tạo sản phẩm sắp hết hạn
            this.createProductsWithExpiryDates(count, random, 15, category, units, tags);

            // Tạo sản phẩm còn hạn
            this.createProductsWithExpiryDates(count, random, 60, category, units, tags);
        });
    }

    public void createInventory() {
        List<Product> products = this.productRepository.findAllWithFilter(null);
        List<Warehouse> warehouses = this.warehouseRepository.findAllWithFilter(null);
        Random random = new Random();
        AtomicInteger count = new AtomicInteger(1);

        warehouses.forEach(warehouse -> IntStream.range(0, 10).forEach(index -> {
            Inventory inventory = Inventory.builder()
                    .name("Inventory " + count)
                    .warehouse(warehouse)
                    .build();

            Collections.shuffle(products, new Random());
            int numberOfProductsToReturn = 50 + new Random().nextInt(100 - 50 + 1);
            List<Product> randomProducts = products.parallelStream()
                    .limit(numberOfProductsToReturn)
                    .collect(Collectors.toList());

            Set<InventoryDetails> inventoryDetailsSet = randomProducts.parallelStream()
                    .map(product -> InventoryDetails.builder()
                            .quantity(5000 + (random.nextFloat() * (10000 - 100)))
                            .product(product)
                            .inventory(inventory)
                            .build())
                    .collect(Collectors.toSet());

            inventory.setInventoryDetailsSet(inventoryDetailsSet);
            this.inventoryRepository.save(inventory);

            count.getAndIncrement();
        }));
    }

    public void createRating() {
        List<CriteriaType> criteriaTypes = new ArrayList<>(List.of(CriteriaType.values()));
        List<Supplier> suppliers = this.supplierRepository.findAllWithFilter(null);
        List<User> users = this.userService.findAllWithFilter(null);
        Random random = new Random();
        AtomicInteger count = new AtomicInteger(1);

        suppliers.forEach(supplier -> IntStream.range(0, 100).forEach(index -> {
            Collections.shuffle(criteriaTypes, random);
            List<User> userList = users.parallelStream()
                    .filter(u -> u.getSupplier() == null || !u.getSupplier().getId().equals(supplier.getId()))
                    .collect(Collectors.toList());
            Collections.shuffle(userList, random);

            Rating rating = Rating.builder()
                    .rating(BigDecimal.valueOf(1 + (random.nextDouble() * (5 - 1))))
                    .content("Rating " + count + " for " + supplier.getName())
                    .user(userList.get(0))
                    .supplier(supplier)
                    .criteria(criteriaTypes.get(0))
                    .build();
            rating.setCreatedAt(this.getRandomDateTimeInYear("rating"));

            this.ratingRepository.save(rating);
            count.getAndIncrement();
        }));
    }

    public void createOrder() {
        List<InventoryDetails> inventoryDetails = this.inventoryDetailsRepository.findAllWithFilter(null);
        List<User> users = this.userService.findAllWithFilter(null);
        Random random = new Random();

        users.forEach(user -> IntStream.range(0, 5).forEach(index -> {
            Collections.shuffle(inventoryDetails, random);
            List<Product> randomProducts = inventoryDetails.parallelStream()
                    .map(InventoryDetails::getProduct)
                    .limit(3)
                    .collect(Collectors.toList());

            Set<OrderDetailsRequest> orderDetails = randomProducts.parallelStream()
                    .map(product -> OrderDetailsRequest.builder()
                            .productId(product.getId())
                            .quantity(3F)
                            .unitPrice(product.getPrice())
                            .build())
                    .collect(Collectors.toSet());

            OrderRequest orderRequest = OrderRequest.builder()
                    .type(random.nextBoolean() ? OrderType.OUTBOUND : OrderType.INBOUND)
                    .status(OrderStatus.DELIVERED)
                    .paid(true)
                    .inventoryId(inventoryDetails.get(0).getInventory().getId())
                    .orderDetails(orderDetails)
                    .createdAt(this.getRandomDateTimeInYear())
                    .build();

            if (orderRequest.getType() == OrderType.OUTBOUND) {
                this.orderService.checkout(user, orderRequest);
            } else if (orderRequest.getType() == OrderType.INBOUND) {
                this.orderService.checkin(user, orderRequest);
            }
        }));
    }

    private void createCustomer() {
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
                .email("customer2@scm.com")
                .username("customer2")
                .password("user@123")
                .userRole(UserRole.ROLE_CUSTOMER)
                .firstName("CUSTOMER 2")
                .middleName("M")
                .lastName("L")
                .address("TPHCM")
                .phone("9872635196")
                .build());
        this.userService.registerUser(UserRequestRegister.builder()
                .email("customer3@scm.com")
                .username("customer3")
                .password("user@123")
                .userRole(UserRole.ROLE_CUSTOMER)
                .firstName("CUSTOMER 3")
                .middleName("M")
                .lastName("L")
                .address("TPHCM")
                .phone("2781764019")
                .build());
    }

    private void createSupplier() {
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
                .email("supplier2@scm.com")
                .username("supplier2")
                .password("user@123")
                .userRole(UserRole.ROLE_SUPPLIER)
                .name("SUPPLIER 2")
                .address("TPHCM")
                .phone("5982716231")
                .contactInfo("5982716231")
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
                .email("supplier3@scm.com")
                .username("supplier3")
                .password("user@123")
                .userRole(UserRole.ROLE_SUPPLIER)
                .name("SUPPLIER 3")
                .address("TPHCM")
                .phone("6782910498")
                .contactInfo("6782910498")
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
    }

    private void createShipper() {
        this.userService.registerUser(UserRequestRegister.builder()
                .email("shipper1@scm.com")
                .username("shipper1")
                .password("user@123")
                .userRole(UserRole.ROLE_SHIPPER)
                .name("SHIPPER 1")
                .contactInfo("0987654321")
                .build());
        this.userService.registerUser(UserRequestRegister.builder()
                .email("shipper2@scm.com")
                .username("shipper2")
                .password("user@123")
                .userRole(UserRole.ROLE_SHIPPER)
                .name("SHIPPER 2")
                .contactInfo("8239184751")
                .build());
        this.userService.registerUser(UserRequestRegister.builder()
                .email("shipper3@scm.com")
                .username("shipper3")
                .password("user@123")
                .userRole(UserRole.ROLE_SHIPPER)
                .name("SHIPPER 3")
                .contactInfo("2617384928")
                .build());
    }

    private @NotNull Date getRandomDateTimeInYear() {
        LocalDateTime now = LocalDateTime.now();

        // Ngày bắt đầu là ngày một tháng trước
        LocalDate start = LocalDate.from(now.minusMonths(1).withDayOfMonth(1));

        // Chuyển đổi các ngày thành số ngày từ epoch để tạo ngày ngẫu nhiên
        long startEpochDay = start.toEpochDay();
        long endEpochDay = now.toLocalDate().toEpochDay();

        // Tạo một ngày ngẫu nhiên giữa start và end
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return getDate(randomEpochDay);
    }

    private @NotNull Date getRandomDateTimeInYear(String rating) {
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 13);

        LocalDate start = LocalDate.of(LocalDate.now().getYear(), randomMonth, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay() + 1);

        return getDate(randomDay);
    }

    @NotNull
    private Date getDate(long randomDay) {
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        int randomHour = ThreadLocalRandom.current().nextInt(0, 24);
        int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);
        int randomSecond = ThreadLocalRandom.current().nextInt(0, 60);

        LocalDateTime randomDateTime = randomDate.atTime(randomHour, randomMinute, randomSecond);

        return Date.from(randomDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private void createProductsWithExpiryDates(AtomicInteger count, Random random, int daysFromNow, Category
            category, List<Unit> units, List<Tag> tags) {
        List<Supplier> suppliers = this.supplierRepository.findAllWithFilter(null);

        suppliers.forEach(supplier -> {
            for (int i = 0; i < 10; i++) {
                BigDecimal price = BigDecimal.valueOf(50000 + (random.nextDouble() * (1000000 - 50000)));

                Date expiryDate = Date.from(LocalDateTime.now().plusDays(daysFromNow).atZone(ZoneId.systemDefault()).toInstant());

                Collections.shuffle(tags, random);
                Set<Tag> randomTags = tags.parallelStream()
                        .limit(2)
                        .collect(Collectors.toSet());

                Unit unit = units.get(random.nextInt(units.size()));

                Product product = Product.builder()
                        .name("Product " + count)
                        .price(price)
                        .unit(unit)
                        .description("Product " + count)
                        .expiryDate(expiryDate)
                        .supplier(supplier)
                        .category(category)
                        .tagSet(randomTags)
                        .build();
                this.productRepository.save(product);

                count.getAndIncrement();
            }
        });
    }
}
