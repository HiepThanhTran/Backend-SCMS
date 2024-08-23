package com.fh.scms.services.implement;

import com.fh.scms.components.GlobalService;
import com.fh.scms.dto.product.ProductResponse;
import com.fh.scms.dto.product.ProductResponseWithTagUnit;
import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Tag;
import com.fh.scms.pojo.Unit;
import com.fh.scms.repository.ProductRepository;
import com.fh.scms.services.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImplement implements ProductService {

    @Autowired
    private GlobalService globalService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UnitService unitService;

    @Override
    public ProductResponse getProductResponse(@NotNull Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .expiryDate(product.getExpiryDate())
                .category(this.categoryService.getCategoryResponse(product.getCategory()))
                .build();
    }

    @Override
    public ProductResponseWithTagUnit getProductResponseWithTagUnit(Product product) {
            return ProductResponseWithTagUnit.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .expiryDate(product.getExpiryDate())
                .category(this.categoryService.getCategoryResponse(product.getCategory()))
                .tagSet(product.getTagSet().stream()
                        .map(tag -> this.tagService.getTagResponse(tag))
                        .collect(Collectors.toSet())
                )
                .unitSet(product.getUnitSet().stream()
                        .map(unit -> this.unitService.getUnitResponse(unit))
                        .collect(Collectors.toSet()))
                .build(); }

    @Override
    public List<ProductResponseWithTagUnit> getAllProductResponseWithTagUnit(Map<String, String> params) {
        return this.productRepository.findAllWithFilter(params)
                .stream().map(this::getProductResponseWithTagUnit)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product, @NotNull List<String> tagIds, List<String> unitIds) {
        Set<Tag> tags = new HashSet<>();
        for (String tagId : tagIds) {
            Tag tag = this.tagService.findById(Long.parseLong(tagId));
            if (tag != null) {
                tags.add(tag);
            }
        }
        product.setTagSet(tags);

        Set<Unit> units = new HashSet<>();
        for (String unitId : unitIds) {
            Unit unit = this.unitService.findById(Long.parseLong(unitId));
            if (unit != null) {
                units.add(unit);
            }
        }
        product.setUnitSet(units);

        if (product.getFile() != null && !product.getFile().isEmpty()) {
            product.setImage(this.globalService.uploadImage(product.getFile()));
        }

        this.productRepository.save(product);
    }

    @Override
    public void update(Product product, @NotNull List<String> tagIds, List<String> unitIds) {
        Set<Tag> tags = new HashSet<>();
        for (String tagId : tagIds) {
            Tag tag = this.tagService.findById(Long.parseLong(tagId));
            if (tag != null) {
                tags.add(tag);
            }
        }
        product.setTagSet(tags);

        Set<Unit> units = new HashSet<>();
        for (String unitId : unitIds) {
            Unit unit = this.unitService.findById(Long.parseLong(unitId));
            if (unit != null) {
                units.add(unit);
            }
        }
        product.setUnitSet(units);

        this.productRepository.update(product);
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void save(Product Product) {
        this.productRepository.save(Product);
    }

    @Override
    public void update(Product Product) {
        this.productRepository.update(Product);
    }

    @Override
    public void delete(Long id) {
        this.productRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.productRepository.count();
    }

    @Override
    public List<Product> findAllWithFilter(Map<String, String> params) {
        return this.productRepository.findAllWithFilter(params);
    }
}
