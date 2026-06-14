package eu.gitonyx.gitonyx.pages;

import de.eztxm.blatt.core.Component;
import de.eztxm.blatt.core.Text;
import de.eztxm.blatt.element.*;
import de.eztxm.blatt.routing.Page;
import de.eztxm.blatt.routing.Route;

@Route("/dashboard")
public class DashboardRoute implements Page {

    @Override
    public Component root() {
        return new Div(
            navbar(),
            new Div(
                sidebar(),
                new Main(
                    header(),
                    statsRow(),
                    new Div(
                        recentRepos(),
                        activity()
                    ).cssClass("dash-grid")
                ).cssClass("dash-main")
            ).cssClass("dash-layout")
        );
    }

    private Component navbar() {
        return new Nav(
            new Anchor(
                new Div().cssClass("brand-icon").add(new Text("◈")),
                new Text("GitOnyx")
            ).href("/").cssClass("navbar-brand"),
            new Div(
                new Input("text").placeholder("Search repositories…").cssClass("dash-search"),
                new Div(
                    new Anchor(new Text("◉")).href("/dashboard").cssClass("avatar-btn"),
                    new Button(new Text("Sign out"))
                        .onClick("logout()")
                        .cssClass("btn btn-outline btn-sm")
                ).cssClass("navbar-user")
            ).cssClass("navbar-links")
        ).cssClass("navbar");
    }

    private Component sidebar() {
        return new Div(
            navItem("⊞", "Overview", "/dashboard", true),
            navItem("◫", "Repositories", "/repositories", false),
            navItem("⑂", "Pull Requests", "/pulls", false),
            navItem("◎", "Issues", "/issues", false),
            navItem("⊳", "Pipelines", "/pipelines", false),
            navItem("◈", "Packages", "/packages", false),
            new Div().cssClass("sidebar-divider"),
            navItem("⊙", "Settings", "/settings", false)
        ).cssClass("sidebar");
    }

    private Component navItem(String icon, String label, String href, boolean active) {
        String cls = "sidebar-item" + (active ? " sidebar-item-active" : "");
        return new Anchor(
            new Span(new Text(icon)).cssClass("sidebar-icon"),
            new Text(label)
        ).href(href).cssClass(cls);
    }

    private Component header() {
        return new Div(
            new Div(
                new Heading(1, new Text("Overview")).cssClass("dash-page-title"),
                new Paragraph(new Text("Welcome back")).cssClass("dash-page-sub").id("dashGreeting")
            ),
            new Anchor(new Text("+ New repository")).href("/repositories/new").cssClass("btn btn-primary")
        ).cssClass("dash-header");
    }

    private Component statsRow() {
        return new Div(
            statCard("Repositories", "0", "◫"),
            statCard("Pull Requests", "0", "⑂"),
            statCard("Open Issues", "0", "◎"),
            statCard("Pipeline Runs", "0", "⊳")
        ).cssClass("dash-stats");
    }

    private Component statCard(String label, String value, String icon) {
        return new Div(
            new Div(
                new Span(new Text(label)).cssClass("stat-card-label"),
                new Span(new Text(icon)).cssClass("stat-card-icon")
            ).cssClass("stat-card-top"),
            new Div(new Text(value)).cssClass("stat-card-value")
        ).cssClass("stat-card");
    }

    private Component recentRepos() {
        return new Div(
            new Div(
                new Heading(2, new Text("Repositories")).cssClass("panel-title"),
                new Anchor(new Text("View all")).href("/repositories").cssClass("panel-link")
            ).cssClass("panel-header"),
            new Div(
                repoRow("gitonyx", "The GitOnyx platform itself", "Java", "2 hours ago"),
                repoRow("infra-config", "Infrastructure as code", "YAML", "1 day ago"),
                repoRow("api-client", "REST client library", "TypeScript", "3 days ago"),
                repoRow("docs", "Documentation site", "Markdown", "1 week ago")
            ).cssClass("repo-list"),
            new Anchor(new Text("New repository")).href("/repositories/new").cssClass("repo-new-link")
        ).cssClass("panel");
    }

    private Component repoRow(String name, String description, String lang, String updated) {
        return new Div(
            new Div(
                new Anchor(new Text(name)).href("/repositories/" + name).cssClass("repo-name"),
                new Paragraph(new Text(description)).cssClass("repo-desc")
            ).cssClass("repo-info"),
            new Div(
                new Span(new Text(lang)).cssClass("repo-lang"),
                new Span(new Text(updated)).cssClass("repo-updated")
            ).cssClass("repo-meta")
        ).cssClass("repo-row");
    }

    private Component activity() {
        return new Div(
            new Div(
                new Heading(2, new Text("Recent Activity")).cssClass("panel-title")
            ).cssClass("panel-header"),
            new Div(
                activityItem("Pushed to", "gitonyx", "main", "10 min ago"),
                activityItem("Opened PR #12 in", "api-client", "feat/auth", "3 hours ago"),
                activityItem("Closed issue #7 in", "infra-config", "", "Yesterday"),
                activityItem("Pushed to", "docs", "main", "2 days ago"),
                activityItem("Created repository", "api-client", "", "3 days ago")
            ).cssClass("activity-list")
        ).cssClass("panel");
    }

    private Component activityItem(String action, String repo, String ref, String time) {
        return new Div(
            new Div().cssClass("activity-dot"),
            new Div(
                new Paragraph(
                    new Text(action + " "),
                    new Anchor(new Text(repo)).href("/repositories/" + repo).cssClass("activity-repo"),
                    ref.isEmpty() ? new Text("") : new Text(" → " + ref)
                ).cssClass("activity-text"),
                new Span(new Text(time)).cssClass("activity-time")
            )
        ).cssClass("activity-item");
    }
}
