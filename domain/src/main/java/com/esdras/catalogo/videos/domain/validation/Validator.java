package com.esdras.catalogo.videos.domain.validation;

/*
Em um cenário prático, você criaria subclasses de Validator para diferentes entidades ou
contextos de validação.
 Cada uma dessas subclasses implementaria o método validate para verificar as
  regras de negócio específicas. Dependendo da estratégia de tratamento de erros desejada,
 */
public abstract class Validator {
    private final ValidationHandler handler;

    protected Validator(final ValidationHandler aHandler) {
        this.handler = aHandler;
    }

    // VALIDAR, COMO AS ENTIDADES MUDAM MUITO AS REGRAS DE VALIDAÇÃO
    // VAMOS DEIXAR ABSTRATO PRA DESACOPLAR A VALIDAÇÃO PRA CADA AGREGADO
    // ESSE MEOTODO SERVE PRAS SUBCRASSES DEFINIR AS REGRAS DE VALIDA~AOES ESPECIFICAS

    public abstract void validate();

    protected ValidationHandler validationHandler() {
        return this.handler;
    }
}
