package eu.gitonyx.gitonyx.pages;

import de.eztxm.blatt.core.Component;
import de.eztxm.blatt.core.Text;
import de.eztxm.blatt.element.*;
import de.eztxm.blatt.routing.Page;
import de.eztxm.blatt.routing.Route;

@Route("/login")
public class LoginRoute implements Page {

    @Override
    public Component root() {
        return new Div(
            authNav(),
            new Main(
                new Div(
                    new Div(
                        new Div(
                            new Heading(1, new Text("Welcome back")).cssClass("auth-title"),
                            new Paragraph(new Text("Sign in to your GitOnyx account")).cssClass("auth-sub")
                        ).cssClass("auth-header"),
                        new Div(
                            errorBanner(),
                            new Div(
                                new Div(
                                    new Span(new Text("Username or Email")).cssClass("field-label"),
                                    new Input("text")
                                        .name("login")
                                        .placeholder("you@example.com")
                                        .cssClass("field-input")
                                ).cssClass("field-group"),
                                new Div(
                                    new Div(
                                        new Span(new Text("Password")).cssClass("field-label"),
                                        new Anchor(new Text("Forgot password?")).href("#").cssClass("forgot-link")
                                    ).cssClass("field-label-row"),
                                    new Input("password")
                                        .name("password")
                                        .placeholder("••••••••")
                                        .cssClass("field-input")
                                ).cssClass("field-group"),
                                new Button(new Text("Sign in"))
                                    .type("submit")
                                    .onClick("handleLogin(event)")
                                    .cssClass("btn btn-primary btn-full")
                            ).cssClass("auth-form").id("loginForm"),
                            new Div(
                                new Text("Don't have an account? "),
                                new Anchor(new Text("Create one")).href("/register")
                            ).cssClass("auth-switch")
                        ).cssClass("auth-body")
                    ).cssClass("auth-card")
                ).cssClass("auth-wrapper")
            )
        );
    }

    private Component authNav() {
        return new Nav(
            new Anchor(
                new Div().cssClass("brand-icon").add(new Text("◈")),
                new Text("GitOnyx")
            ).href("/").cssClass("navbar-brand")
        ).cssClass("navbar navbar-minimal");
    }

    private Component errorBanner() {
        return new Div(new Text("")).cssClass("auth-error hidden").id("loginError");
    }
}
