package controller.validators;

import model.entities.Activity;

import javax.ejb.Stateless;


/**
 * Created by cuatroochenta on 1/4/15.
 */
public class ActivityValidator implements Validator<Activity> {

    @Override
    public boolean validate(Activity object) {

        if(object == null){
            return false;
        }
        if( object.getTitle() == null || object.getTitle().length() == 0 ){
            return false;
        }
        if (object.getDescription()==null || object.getDescription().length()==0){
            return false;
        }
        if (object.getPlace()==null || object.getPlace().length()==0){
            return false;
        }
        if (object.getCategory()==null){
            return false;
        }

        if( object.getTotalPlaces() < 1.0f ){
            return false;
        }
        if( object.getPrice() < 0.0f ){
            return false;
        }

        if( object.getPrice() > 99999.99f){
            return false;
        }
        if ( object.getDate() == null ){
            return false;
        }
        return true;
    }

}
