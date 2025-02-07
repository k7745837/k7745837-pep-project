package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        ObjectMapper om = new ObjectMapper();
        app.get("example-endpoint", this::exampleHandler);

        /**
         * ## 1: Our API should be able to process new User registrations.
         * 
         * As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, but will not contain an account_id.
         * 
         * - The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. 
         *      If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
         * - If the registration is not successful, the response status should be 400. (Client error)
         * 
         */
        app.post("/register", ctx -> {
            //retrieve the json string from the request body
            String jsonString = ctx.body();

            //utilize jackson to convert the json string to an account object
            Account account = om.readValue(jsonString, Account.class);

            //Attempt registration

            //return the account as the response body, but also have Javalin convert it to JSON
            ctx.status(200);
            ctx.json(account);
        });

        /**
         * ## 2: Our API should be able to process User logins.
         * 
         * As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. The request body will contain a JSON representation of an Account, not containing an account_id. 
         *      In the future, this action may generate a Session token to allow the user to securely use the site. We will not worry about this for now.
         * 
         * - The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
         *      If successful, the response body should contain a JSON of the account in the response body, including its account_id. The response status should be 200 OK, which is the default.
         * - If the login is not successful, the response status should be 401. (Unauthorized)
         * 
         */
        app.post("/login", ctx -> {
            //retrieve the json string from the request body
            String jsonString = ctx.body();

            //utilize jackson to convert the json string to an account object
            Account account = om.readValue(jsonString, Account.class);

            //Attempt login

            //return the account as the response body, but also have Javalin convert it to JSON
            ctx.status(200);
            ctx.json(account);
        });

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}