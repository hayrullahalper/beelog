package com.playground.auth.controller;

import com.beehive.lib.Controller.Controller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.playground.auth.service.AuthService;

import javax.validation.ConstraintViolation;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/auth")
public class AuthController extends Controller {

    private final AuthService authService = service(AuthService.class);

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String req) {
        LoginRequest request = new Gson().fromJson(req, LoginRequest.class);
        Set<ConstraintViolation<LoginRequest>> violations = validate(request);

        if (!violations.isEmpty()) {
            JsonObject errors = new JsonObject();

            for (ConstraintViolation<LoginRequest> violation : violations) {
                errors.addProperty(violation.getPropertyPath().toString(), violation.getMessage());
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(errors.toString())
                    .build();
        }

        boolean success = authService.login(request.username, request.password);

        if (success) {
            return Response
                    .status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("Kralsın")
                    .build();
        }  else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("Ağla")
                    .build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(String req) {
        RegisterRequest request = new Gson().fromJson(req, RegisterRequest.class);
        Set<ConstraintViolation<RegisterRequest>> violations = validate(request);

        if (!violations.isEmpty()) {
            JsonObject errors = new JsonObject();

            for (ConstraintViolation<RegisterRequest> violation : violations) {
                errors.addProperty(violation.getPropertyPath().toString(), violation.getMessage());
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(errors.toString())
                    .build();
        }

        authService.register(request.name, request.username, request.email, request.password);

        return Response
                .status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(request)
                .build();
    }
}
