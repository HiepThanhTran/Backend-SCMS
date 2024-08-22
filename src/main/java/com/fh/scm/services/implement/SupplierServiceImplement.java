package com.fh.scm.services.implement;

import com.fh.scm.dto.api.rating.RatingRequestCreate;
import com.fh.scm.dto.api.supplier.SupplierDTO;
import com.fh.scm.exceptions.RatingSupplierException;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.pojo.Rating;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.PaymentTermsRepository;
import com.fh.scm.repository.RatingRepository;
import com.fh.scm.repository.SupplierRepository;
import com.fh.scm.repository.UserRepository;
import com.fh.scm.services.SupplierService;
import com.fh.scm.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public SupplierDTO getSupplierResponse(Supplier supplier) {
        return SupplierDTO.builder()
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .contactInfo(supplier.getContactInfo())
                .build();
    }

    @Override
    public Supplier getProfileSupplier(String username) {
        User user = this.userRepository.getByUsername(username);

        return this.getByUser(user);
    }

    @Override
    public SupplierDTO updateProfileSupplier(String username, SupplierDTO supplierDTO) {
        User user = this.userRepository.getByUsername(username);

        if (!user.getIsConfirm()) {
            throw new UserException("Tài khoản chưa được xác nhận");
        }

        Supplier supplier = this.supplierRepository.getByUser(user);

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
    public Rating addRatingForSupplier(String username, Long supplierId, RatingRequestCreate ratingRequestCreate) {
        User user = this.userRepository.getByUsername(username);
        Supplier supplier = this.supplierRepository.get(supplierId);

        if (supplier == null) {
            throw new RatingSupplierException("Nhà cung cấp không tồn tại", HttpStatus.NOT_FOUND.value());
        }

        if (user.getSupplier() != null && Objects.equals(user.getSupplier().getId(), supplierId)) {
            throw new RatingSupplierException("Không thể đánh giá chính mình");
        }

        Rating rating = this.ratingRepository.getByUserAndSupplier(user.getId(), supplierId);

        if (rating == null) {
            rating = Rating.builder()
                    .user(user)
                    .supplier(supplier)
                    .rating(ratingRequestCreate.getRating())
                    .content(ratingRequestCreate.getContent())
                    .criteria(ratingRequestCreate.getCriteria())
                    .build();
            this.ratingRepository.insert(rating);
        } else {
            rating.setRating(ratingRequestCreate.getRating());
            rating.setContent(ratingRequestCreate.getContent());
            rating.setCriteria(ratingRequestCreate.getCriteria());
            this.ratingRepository.update(rating);
        }

        return rating;
    }

    @Override
    public Supplier get(Long id) {
        return this.supplierRepository.get(id);
    }

    @Override
    public Supplier getByUser(User user) {
        return this.supplierRepository.getByUser(user);
    }

    @Override
    public Supplier getByPhone(String phone) {
        return this.supplierRepository.getByPhone(phone);
    }

    @Override
    public void insert(Supplier supplier) {
        this.supplierRepository.insert(supplier);
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
    public void softDelete(Long id) {
        this.supplierRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.supplierRepository.count();
    }

    @Override
    public List<Supplier> getAll(Map<String, String> params) {
        return this.supplierRepository.getAll(params);
    }
}
