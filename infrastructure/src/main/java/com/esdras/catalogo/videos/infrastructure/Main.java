package com.esdras.catalogo.videos.infrastructure;

import com.esdras.catalogo.videos.application.UseCase;
import com.esdras.catalogo.videos.domain.category.Category;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
         System.out.println(new UseCase().execute());
        System.out.println(new Category());
    }
}