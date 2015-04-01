package controller.validators;

/**
 * Created by cuatroochenta on 1/4/15.
 */
public interface Validator<E> {
    boolean validate (E object);
}
