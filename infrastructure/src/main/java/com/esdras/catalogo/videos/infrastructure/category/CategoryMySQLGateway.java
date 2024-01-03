package com.esdras.catalogo.videos.infrastructure.category;

import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import com.esdras.catalogo.videos.domain.category.CategoryID;
import com.esdras.catalogo.videos.domain.category.CategorySearchQuery;
import com.esdras.catalogo.videos.domain.pagination.Pagination;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
//PODEMOS COLOCAR TBM COMPONENT
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }


    @Override
    public Category create(final Category aCategory) {
        return save(aCategory);
    }


    @Override
    public void deleteById(CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }

}