package controller.validators;

import model.entities.Monitor;

import javax.ejb.Stateless;
import javax.inject.Inject;


public class MonitorValidator implements Validator<Monitor> {

    @Override
    public boolean validate(Monitor object) {
        if(object == null){
            return false;
        }
        if( object.getLastName() == null || object.getLastName().length() == 0 ){
            return false;
        }
        if( object.getName() == null || object.getName().length() == 0 ){
            return false;
        }
        if( object.getNif() == null || object.getNif().length() != 9 ){
            return false;
        }else{
            //TODO: validate NIF
        }
        return true;
    }

}
