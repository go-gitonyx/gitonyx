package eu.gitonyx.gitonyx.pages;

import de.eztxm.blatt.core.Component;
import de.eztxm.blatt.core.Text;
import de.eztxm.blatt.element.*;
import de.eztxm.blatt.routing.Page;
import de.eztxm.blatt.routing.Route;

@Route("/")
public class MainRoute implements Page {

    @Override
    public Component root() {
        return new Div(
            navbar(),
            hero(),
            stats(),
            features(),
            cta(),
            footer()
        );
    }

    private Component navbar() {
        return new Nav().cssClass("navbar").add(
            new Anchor(
                new Div().cssClass("brand-icon").add(new Text("◈")),
                new Text("GitOnyx")
            ).href("/").cssClass("navbar-brand"),
            new Div(
                new Anchor(new Text("Features")).href("#features").cssClass(""),
                new Anchor(new Text("Docs")).href("#").cssClass(""),
                new Anchor(new Text("Pricing")).href("#").cssClass(""),
                new Anchor(new Text("Sign in")).href("/login").cssClass("btn btn-outline"),
                new Anchor(new Text("Get started")).href("/register").cssClass("btn btn-primary")
            ).cssClass("navbar-links")
        );
    }

    private Component hero() {
        return new Section(
            new Div(new Text("Now in open beta")).cssClass("hero-badge"),
            new Heading(1,
                new Text("The self-hosted "),
                new Span(new Text("Git platform")).cssClass("highlight"),
                new Text(" built for teams")
            ),
            new Paragraph(
                new Text("GitOnyx gives you full control over your repositories, CI pipelines, and access management — on your own infrastructure.")
            ).cssClass("hero-sub"),
            new Div(
                new Anchor(new Text("Get started for free")).href("/register").cssClass("btn btn-primary"),
                new Anchor(new Text("View on GitHub")).href("https://github.com").cssClass("btn btn-outline")
            ).cssClass("hero-actions")
        ).cssClass("hero");
    }

    private Component stats() {
        return new Section(
            new Div(
                statItem("10k+", "Repositories hosted"),
                statItem("500+", "Active teams"),
                statItem("99.9%", "Uptime SLA"),
                statItem("< 50ms", "Avg. response time")
            ).cssClass("stats-inner")
        ).cssClass("stats");
    }

    private Component statItem(String value, String label) {
        return new Div(
            new Div(new Text(value)).cssClass("stat-value"),
            new Div(new Text(label)).cssClass("stat-label")
        );
    }

    private Component features() {
        return new Section(
            new Heading(2, new Text("Everything you need")).cssClass("section-title"),
            new Paragraph(new Text("A complete Git hosting solution, from repositories to access control.")).cssClass("section-sub"),
            new Div(
                featureCard("🗂️", "Repository Management", "Create, fork, and mirror repositories with fine-grained access control and branch protection rules."),
                featureCard("🔐", "Access Control", "Role-based permissions at organization, team, and repository level — fully auditable."),
                featureCard("🔄", "CI / CD Pipelines", "Built-in pipeline runner with YAML-based workflow definitions and parallel job support."),
                featureCard("🔍", "Code Review", "Pull requests with inline comments, review assignments, and merge queue support."),
                featureCard("📦", "Package Registry", "Host Maven, npm, Docker images, and more alongside your source code."),
                featureCard("⚡", "Webhooks & API", "REST API and webhook support for integrating with any external tool or service.")
            ).cssClass("features-grid").id("features")
        ).cssClass("features");
    }

    private Component featureCard(String icon, String title, String description) {
        return new Div(
            new Div(new Text(icon)).cssClass("feature-icon"),
            new Heading(3, new Text(title)),
            new Paragraph(new Text(description))
        ).cssClass("feature-card");
    }

    private Component cta() {
        return new Section(
            new Heading(2, new Text("Ready to take control?")).cssClass(""),
            new Paragraph(new Text("Set up your own GitOnyx instance in minutes. No vendor lock-in, no usage limits.")).cssClass(""),
            new Div(
                new Anchor(new Text("Create your account")).href("/register").cssClass("btn btn-primary"),
                new Anchor(new Text("Read the docs")).href("/docs").cssClass("btn btn-outline")
            ).cssClass("hero-actions")
        ).cssClass("cta");
    }

    private Component footer() {
        return new Footer(
            new Paragraph(new Text("© 2026 GitOnyx · ")),
            new Anchor(new Text("Terms")).href("/terms"),
            new Anchor(new Text("Privacy")).href("/privacy"),
            new Anchor(new Text("Status")).href("/status"),
            new Anchor(new Text("GitHub")).href("https://github.com")
        ).cssClass("footer");
    }
}
