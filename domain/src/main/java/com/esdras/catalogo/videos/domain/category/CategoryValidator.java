package com.esdras.catalogo.videos.domain.category;

import com.esdras.catalogo.videos.domain.validation.ValidationHandler;
import com.esdras.catalogo.videos.domain.validation.Validator;
import com.esdras.catalogo.videos.domain.validation.Error;

public class CategoryValidator extends Validator {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;
    private final Category category;
    //PRA CADA AGREGRATE TEMOS UM VALIDATOR
    //PRA ISOLAR E DESACOPLAR A LOGICA DE NEGOCIOS DE VALIDAÇÃO, OU RETIRAR TBM

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }


    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        //E AQUI ESTAMOS COLOCANDO AS REGRAS DE VALIDAÇÃO
        //SE O NOME FOR NULO, VAZIO OU SE TIVER MENOS DE 3 CARACTERES OU MAIS DE 255
        //VAMOS ADICIONAR UM ERRO NO HANDLER
        final var name = this.category.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
