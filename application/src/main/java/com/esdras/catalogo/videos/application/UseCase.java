package com.esdras.catalogo.videos.application;

import com.esdras.catalogo.videos.domain.Category;

public class UseCase {
    public Category execute() {
        return new Category();
    }
}