package com.esdras.catalogo.videos.infrastructure.category;

import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import com.esdras.catalogo.videos.domain.category.CategoryID;
import com.esdras.catalogo.videos.domain.category.CategorySearchQuery;
import com.esdras.catalogo.videos.domain.pagination.Pagination;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.esdras.catalogo.videos.infrastructure.utils.SpecificationUtils.like;

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
        final String idValue = anId.getValue();
        //IDEAL NÃO FICAR MANDANDO EXCEPTION DAQUI
        if (this.repository.existsById(idValue)) {
            this.repository.deleteById(idValue);
        }
        //SE NÃO EXISTIR NÃO FAZ NADA
    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        //UM ERRO QUE COMETIA ERA REALIZAR ISSO AQUI
        //return this.repository.findById(anId.getValue()).map(CategoryJpaEntity::toAggregate).orElse(null);
        return this.repository.findById(anId.getValue()).map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {

        //CIRAÇÃO DAS ESPECIFICAÇÕES DA PAGE
        //O SPECIFICATION VAI SER PRO WHERE E OS PARAMETROS DE PAGINAS
        //TANTO PRA QUAL E A PAGINA, QUANTOS POR PAGINA E SE E ASC OU DESC
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        //CRIAÇÃO DAS ESPECIFICAÇÕES DA QUERY
        //SO QUE SE NÃO TIVER, NEM VAI FAZER AS ESPECIFIÇÃO
        //PRA ISSO O FILTER
        //DEPOIS MAPPEAR E CRIAR UMA ESPCIFICAÇÃO DE BUSCA PRA CADA UM DOS CAMPOS
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> {
                    final Specification<CategoryJpaEntity> nameLike = like("name", str);
                    final Specification<CategoryJpaEntity> descriptionLike = like("description", str);
                    return nameLike.or(descriptionLike);
                })
                .orElse(null);

        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page);

        //DEPOIS DISSO SO RETORNAR A PAGINAÇÃO PROS PARAMETROS GENERICOS
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }

}