package com.fh.scms.services.implement;

import com.fh.scms.dto.shipper.ShipperDTO;
import com.fh.scms.pojo.Invoice;
import com.fh.scms.pojo.Shipper;
import com.fh.scms.pojo.User;
import com.fh.scms.repository.InvoiceRepository;
import com.fh.scms.repository.ShipperRepository;
import com.fh.scms.repository.UserRepository;
import com.fh.scms.services.ShipperService;
import com.fh.scms.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class ShipperServiceImplement implements ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Shipper findById(Long id) {
        return this.shipperRepository.findById(id);
    }

    @Override
    public void save(Shipper shipper) {
        this.shipperRepository.save(shipper);
    }

    @Override
    public void update(Shipper shipper) {
        this.shipperRepository.update(shipper);
    }

    @Override
    public void delete(Long id) {
        Shipper shipper = this.shipperRepository.findById(id);
        User user = shipper.getUser();
        List<Invoice> invoices = new ArrayList<>(user.getInvoiceSet());
        invoices.forEach(invoice -> {
            invoice.setUser(null);
            this.invoiceRepository.update(invoice);
        });

        this.shipperRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.shipperRepository.count();
    }

    @Override
    public List<Shipper> findAllWithFilter(Map<String, String> params) {
        return this.shipperRepository.findAllWithFilter(params);
    }

    @Override
    public ShipperDTO getShipperResponse(@NotNull Shipper shipper) {
        return ShipperDTO.builder()
                .name(shipper.getName())
                .contactInfo(shipper.getContactInfo())
                .build();
    }

    @Override
    public Shipper getProfileShipper(String username) {
        User user = this.userRepository.findByUsername(username);

        return this.shipperRepository.findByUser(user);
    }

    @Override
    public ShipperDTO updateProfileShipper(String username, ShipperDTO shipperDTO) {
        User user = this.userRepository.findByUsername(username);

        if (!user.getConfirm()) {
            throw new IllegalArgumentException("Tài khoản chưa được xác nhận");
        }

        Shipper shipper = this.shipperRepository.findByUser(user);

        Field[] fields = ShipperDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(shipperDTO);

                if (value != null && !value.toString().isEmpty()) {
                    Field shipperField = Shipper.class.getDeclaredField(field.getName());
                    shipperField.setAccessible(true);
                    Object convertedValue = Utils.convertValue(shipperField.getType(), value.toString());
                    shipperField.set(shipper, convertedValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        this.shipperRepository.update(shipper);

        return this.getShipperResponse(shipper);
    }
}
