package controller.validators;

import java.io.Serializable;

/**
 * Created by cuatroochenta on 1/4/15.
 */
public interface Validator<E> extends Serializable{
    boolean validate (E object);
}
