package eu.gitonyx.gitonyx.pages;

import de.eztxm.blatt.core.Component;
import de.eztxm.blatt.core.Text;
import de.eztxm.blatt.element.*;
import de.eztxm.blatt.routing.Page;
import de.eztxm.blatt.routing.Route;

@Route("/register")
public class RegisterRoute implements Page {

    @Override
    public Component root() {
        return new Div(
            authNav(),
            new Main(
                new Div(
                    new Div(
                        new Div(
                            new Heading(1, new Text("Create your account")).cssClass("auth-title"),
                            new Paragraph(new Text("Start hosting your repositories on GitOnyx")).cssClass("auth-sub")
                        ).cssClass("auth-header"),
                        new Div(
                            new Div(new Text("")).cssClass("auth-error hidden").id("registerError"),
                            new Div(
                                new Div(
                                    new Div(
                                        new Span(new Text("First Name")).cssClass("field-label"),
                                        new Input("text")
                                            .name("firstName")
                                            .placeholder("Jane")
                                            .cssClass("field-input")
                                    ).cssClass("field-group"),
                                    new Div(
                                        new Span(new Text("Last Name")).cssClass("field-label"),
                                        new Input("text")
                                            .name("lastName")
                                            .placeholder("Doe")
                                            .cssClass("field-input")
                                    ).cssClass("field-group")
                                ).cssClass("field-row"),
                                new Div(
                                    new Span(new Text("Username")).cssClass("field-label"),
                                    new Input("text")
                                        .name("username")
                                        .placeholder("janedoe")
                                        .cssClass("field-input")
                                ).cssClass("field-group"),
                                new Div(
                                    new Span(new Text("Email")).cssClass("field-label"),
                                    new Input("text")
                                        .name("email")
                                        .placeholder("jane@example.com")
                                        .cssClass("field-input")
                                ).cssClass("field-group"),
                                new Div(
                                    new Span(new Text("Password")).cssClass("field-label"),
                                    new Input("password")
                                        .name("password")
                                        .placeholder("••••••••")
                                        .cssClass("field-input")
                                ).cssClass("field-group"),
                                new Button(new Text("Create account"))
                                    .type("submit")
                                    .onClick("handleRegister(event)")
                                    .cssClass("btn btn-primary btn-full")
                            ).cssClass("auth-form").id("registerForm"),
                            new Div(
                                new Text("Already have an account? "),
                                new Anchor(new Text("Sign in")).href("/login")
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
}
