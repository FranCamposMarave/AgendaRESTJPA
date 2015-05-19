package controller.validators;

import model.entities.Monitor;

import javax.ejb.Stateless;
import javax.inject.Inject;


public class MonitorValidator implements Validator<Monitor> {

    private static final String VALID_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

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
            try{
                int nifNumber = Integer.parseInt( object.getNif().substring(0, 7) );
                if ( VALID_LETTERS.charAt( nifNumber % 23 ) != object.getNif().charAt(8) ){
                    return true;
                }
            }catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }

}
