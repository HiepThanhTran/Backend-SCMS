package com.fh.scms.services.implement;

import com.fh.scms.pojo.Customer;
import com.fh.scms.pojo.Invoice;
import com.fh.scms.pojo.Shipper;
import com.fh.scms.pojo.User;
import com.fh.scms.repository.InvoiceRepository;
import com.fh.scms.repository.ShipperRepository;
import com.fh.scms.services.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShipperServiceImplement implements ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

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
}
