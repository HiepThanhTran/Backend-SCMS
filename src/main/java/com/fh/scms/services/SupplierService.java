package com.fh.scms.services;

import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.dto.product.ProductRequestPublish;
import com.fh.scms.dto.product.ProductResponseForDetails;
import com.fh.scms.dto.product.ProductResponseForList;
import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SupplierService {

    Supplier findById(Long id);

    void save(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    Long count();

    List<Supplier> findAllWithFilter(Map<String, String> params);

    SupplierDTO getSupplierResponse(Supplier supplier);

    Supplier getProfileSupplier(String username);

    SupplierDTO updateProfileSupplier(String username, SupplierDTO supplierDTO);

    ProductResponseForDetails publishProduct(String username, ProductRequestPublish productRequestPublish);

    void unpublishProduct(String username, Long productId);

    List<OrderResponse> getOrdersOfSupplier(Long supplierId, Map<String, String> params);

    List<Rating> getRatingsOfSupplier(Long supplierId);

    Rating addRatingForSupplier(String username, Long supplierId, RatingRequestCreate ratingRequestCreate);
}
