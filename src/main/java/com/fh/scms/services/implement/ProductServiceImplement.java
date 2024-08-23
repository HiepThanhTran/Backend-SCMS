package com.fh.scms.services.implement;

import com.fh.scms.dto.product.ProductResponse;
import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Tag;
import com.fh.scms.pojo.Unit;
import com.fh.scms.repository.ProductRepository;
import com.fh.scms.repository.TagRepository;
import com.fh.scms.repository.UnitRepository;
import com.fh.scms.services.CategoryService;
import com.fh.scms.services.ProductService;
import com.fh.scms.services._GlobalService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImplement implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private _GlobalService globalService;

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
    public Product get(Long id) {
        return this.productRepository.get(id);
    }

    @Override
    public void insert(Product Product) {
        this.productRepository.insert(Product);
    }

    @Override
    public void insert(Product product, List<String> tagIds, List<String> unitIds) {
        Set<Tag> tags = new HashSet<>();
        for (String tagId : tagIds) {
            Tag tag = this.tagRepository.get(Long.parseLong(tagId));
            if (tag != null) {
                tags.add(tag);
            }
        }
        product.setTagSet(tags);

        Set<Unit> units = new HashSet<>();
        for (String unitId : unitIds) {
            Unit unit = this.unitRepository.get(Long.parseLong(unitId));
            if (unit != null) {
                units.add(unit);
            }
        }
        product.setUnitSet(units);

        if (product.getFile() != null && !product.getFile().isEmpty()) {
            product.setImage(this.globalService.uploadImage(product.getFile()));
        }

        this.productRepository.insert(product);
    }

    @Override
    public void update(Product Product) {
        this.productRepository.update(Product);
    }

    @Override
    public void update(Product product, @NotNull List<String> tagIds, List<String> unitIds) {
        Set<Tag> tags = new HashSet<>();
        for (String tagId : tagIds) {
            Tag tag = this.tagRepository.get(Long.parseLong(tagId));
            if (tag != null) {
                tags.add(tag);
            }
        }
        product.setTagSet(tags);

        Set<Unit> units = new HashSet<>();
        for (String unitId : unitIds) {
            Unit unit = this.unitRepository.get(Long.parseLong(unitId));
            if (unit != null) {
                units.add(unit);
            }
        }
        product.setUnitSet(units);

        this.productRepository.update(product);
    }

    @Override
    public void delete(Long id) {
        this.productRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.productRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.productRepository.count();
    }

    @Override
    public List<Product> getAll(Map<String, String> params) {
        return this.productRepository.getAll(params);
    }
}
