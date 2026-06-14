package eu.gitonyx.gitonyx;

import java.nio.file.Paths;

import de.eztxm.blatt.core.HeadConfig;
import de.eztxm.blatt.http.BlattServer;
import de.eztxm.blatt.routing.RouteScanner;
import de.eztxm.blatt.routing.Router;
import de.tnttastisch.polydb.PolyDB;
import de.tnttastisch.polydb.query.Repository;
import eu.gitonyx.gitonyx.config.DatabaseConfig;
import eu.gitonyx.gitonyx.factory.ConfigFactory;
import eu.gitonyx.gitonyx.model.UserModel;
import eu.gitonyx.gitonyx.route.OnyxRouter;
import eu.gitonyx.gitonyx.route.user.UserRoute;
import eu.gitonyx.gitonyx.session.SessionStore;

public class GitOnyx {

    private DatabaseConfig databaseConfig;
    private PolyDB polyDB;

    private Repository<UserModel> userRepository;
    private OnyxRouter router;
    private final SessionStore sessionStore = new SessionStore();

    public GitOnyx() {
        try {
            ConfigFactory configFactory = new ConfigFactory();

            this.databaseConfig = configFactory.loadConfig(DatabaseConfig.class);
            configFactory.saveConfig(this.databaseConfig);

            this.polyDB = PolyDB.builder()
                    .url(dbConfigToJdbc(databaseConfig))
                    .username(this.databaseConfig.getUser())
                    .password(this.databaseConfig.getPassword())
                    .entityPackage("eu.gitonyx.gitonyx.model")
                    .autoMigration(true)
                    .start();

            this.registerModels();
            new Thread(this::registerRoutes).start();
            this.setupFrontend();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void registerModels() {
        this.userRepository = this.polyDB.repository(UserModel.class);
    }

    private void registerRoutes() {
        this.router = new OnyxRouter("/api/v1");
        this.router.registerRoutes(new UserRoute(this.userRepository, this.sessionStore));
        this.router.start(3493);
    }

    private void setupFrontend() {
        Router router = new Router()
                .staticFiles(Paths.get("public"));

        new RouteScanner("eu.gitonyx.gitonyx.pages").scan(router);

        HeadConfig headConfig = new HeadConfig()
                .lang("en")
                .title("GitOnyx")
                .stylesheet("/style.css")
                .script("/auth.js")
                .script("/dashboard.js");

        new BlattServer(router, headConfig, 3492).start();
    }

    private String dbConfigToJdbc(DatabaseConfig databaseConfig) {
        return String.format("jdbc:mariadb://%s:%d/%s",
                databaseConfig.getHost(),
                databaseConfig.getPort(),
                databaseConfig.getDatabase());
    }

    public static void main(String[] args) {
        new GitOnyx();
    }
}
