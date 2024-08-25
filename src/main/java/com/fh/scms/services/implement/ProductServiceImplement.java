package com.fh.scms.services.implement;

import com.fh.scms.components.GlobalService;
import com.fh.scms.dto.product.ProductResponseForDetails;
import com.fh.scms.dto.product.ProductResponseForList;
import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Tag;
import com.fh.scms.repository.ProductRepository;
import com.fh.scms.services.CategoryService;
import com.fh.scms.services.ProductService;
import com.fh.scms.services.TagService;
import com.fh.scms.services.UnitService;
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
    public ProductResponseForList getProductResponseForList(@NotNull Product product) {
        return ProductResponseForList.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }

    @Override
    public ProductResponseForDetails getProductResponseForDetails(@NotNull Product product) {
        return ProductResponseForDetails.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .expiryDate(product.getExpiryDate())
                .unit(this.unitService.getUnitResponse(product.getUnit()))
                .category(this.categoryService.getCategoryResponse(product.getCategory()))
                .tagSet(product.getTagSet().stream()
                        .map(tag -> this.tagService.getTagResponse(tag))
                        .collect(Collectors.toSet())
                )
                .build();
    }

    @Override
    public List<ProductResponseForList> getAllProductResponseForList(Map<String, String> params) {
        return this.productRepository.findAllWithFilter(params)
                .stream().map(this::getProductResponseForList)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product, @NotNull List<String> tagIds) {
        Set<Tag> tags = new HashSet<>();
        for (String tagId : tagIds) {
            Tag tag = this.tagService.findById(Long.parseLong(tagId));
            if (tag != null) {
                tags.add(tag);
            }
        }
        product.setTagSet(tags);

        if (product.getFile() != null && !product.getFile().isEmpty()) {
            product.setImage(this.globalService.uploadImage(product.getFile()));
        }

        this.productRepository.save(product);
    }

    @Override
    public void update(Product product, @NotNull List<String> tagIds) {
        Set<Tag> tags = new HashSet<>();
        for (String tagId : tagIds) {
            Tag tag = this.tagService.findById(Long.parseLong(tagId));
            if (tag != null) {
                tags.add(tag);
            }
        }
        product.setTagSet(tags);

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
