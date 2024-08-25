package com.fh.scms.services.implement;

import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.pojo.User;
import com.fh.scms.repository.PaymentTermsRepository;
import com.fh.scms.repository.RatingRepository;
import com.fh.scms.repository.SupplierRepository;
import com.fh.scms.repository.UserRepository;
import com.fh.scms.services.SupplierService;
import com.fh.scms.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class SupplierServiceImplement implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentTermsRepository paymentTermsRepository;
    @Autowired
    private RatingRepository ratingRepository;

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
    public List<Rating> getRatingsForSupplier(Long supplierId) {
        Supplier supplier = this.supplierRepository.findById(supplierId);

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
                    .comment(ratingRequestCreate.getComment())
                    .criteria(ratingRequestCreate.getCriteria())
                    .build();
            this.ratingRepository.save(rating);
        } else {
            rating.setRating(ratingRequestCreate.getRating());
            rating.setComment(ratingRequestCreate.getComment());
            rating.setCriteria(ratingRequestCreate.getCriteria());
            this.ratingRepository.update(rating);
        }

        return rating;
    }

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
}
