package com.esdras.catalogo.videos.infrastructure.category.persistence;


import com.esdras.catalogo.videos.MySQLGatewayTest;
import com.esdras.catalogo.videos.domain.category.Category;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {


    //CENARIO LINDO DE TESTE
    //ONDE AS VALIDAÇÃO DOS NULL ESOA
    @Autowired
    private CategoryRepository categoryRepository;


    //SEGREDO PRA CADA PROPRIEDADE QUE NÃO PODE SER NULA TESTAR, TANTO PENSANDO NO DOMINIO, TANTO NO JPA
    @Test
    @DisplayName("Dado uma categoria com nome nulo, quando chamar o save, então deve retornar erro")
    public void givenAnInvalidNullName_whenCallsSave_shouldReturnError() {
        final var expectedPropertyName = "name";
        final var expectedMessage = "not-null property references a null or transient value : com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity.name";
        //PEGAR ESSA EXPECTED MESSAGE TYESTANDO E VENDO O QUE VEM NO ERRO

        final var aCategory = Category.newCategory("Filmes", "A categoria mais  daora", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        //NESSE CASO SETAR O NULO PRO NOME
        anEntity.setName(null);

        //VER QUAL EXCEÇÃO DEU
        //SE FOI UMA DATA INTEGRITY VIOLATION QUE E DO PROPRIO HIBERANTE
        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        //E VER SE A CAUSA FOI UMA PROPERTY VALUE EXCEPTION DADO A PROPRIEDADE NAME
        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    @DisplayName("Dado uma categoria com createdAt nulo, quando chamar o save, então deve retornar erro")
    public void givenAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {
        final var expectedPropertyName = "createdAt";
        final var expectedMessage = "not-null property references a null or transient value : com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity.createdAt";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais daora", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    @DisplayName("Dado uma categoria com updatedAt nula, quando chamar o save, então deve retornar erro")
    public void givenAnInvalidNullUpdatedAt_whenCallsSave_shouldReturnError() {
        final var expectedPropertyName = "updatedAt";
        final var expectedMessage = "not-null property references a null or transient value : com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity.updatedAt";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais legal", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setUpdatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }
}
