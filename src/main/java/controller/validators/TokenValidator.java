package controller.validators;

import model.dao.UserJPA;
import model.entities.User;

import javax.inject.Inject;
import javax.ws.rs.core.Response;


public class TokenValidator {

    @Inject
    private UserJPA userDAO;

    public Response validate(String token) {
        if (token == null) {
            return Response.status( Response.Status.UNAUTHORIZED ).build();
        }

        User user = userDAO.getByToken(token);
        System.out.println(token+"==="+user.toString()+"");

        return null;
    }

}
