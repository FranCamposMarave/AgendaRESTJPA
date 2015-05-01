package controller.validators;

import java.io.Serializable;


public interface Validator<E> extends Serializable{
    boolean validate (E object);
}
