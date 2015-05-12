package controller.validators;

import model.entities.User;


public class UserValidator implements Validator<User> {


    @Override
    public boolean validate(User object) {
        if(object == null){
            return false;
        }
        return true;
    }

}
