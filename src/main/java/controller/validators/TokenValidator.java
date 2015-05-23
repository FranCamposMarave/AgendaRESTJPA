package controller.validators;

import controller.Permissions;
import model.dao.UserJPA;
import model.entities.User;

import javax.inject.Inject;
import javax.ws.rs.core.Response;


public class TokenValidator {

    @Inject
    private UserJPA userDAO;

    public Response validate(String token, int permission) {
        if (token == null || token.equals("") || token.equals("null")) {
            return Response.status( Response.Status.UNAUTHORIZED ).build();
        }

        User user = userDAO.getByToken(token);
        System.out.println(token+"==="+user.toString()+"");

        if(! Permissions.getInstance().hasPermision(user.ROLE,permission)){
            System.out.println("======PERMISIONS: FALSE");
            return Response.status( Response.Status.UNAUTHORIZED ).build();
        }

        System.out.println("======PERMISIONS: TRUEE");
        return null;
    }

}
