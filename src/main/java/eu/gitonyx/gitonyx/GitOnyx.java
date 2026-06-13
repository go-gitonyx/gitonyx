package eu.gitonyx.gitonyx;

import de.tnttastisch.polydb.PolyDB;
import de.tnttastisch.polydb.query.Repository;
import eu.gitonyx.gitonyx.config.DatabaseConfig;
import eu.gitonyx.gitonyx.factory.ConfigFactory;
import eu.gitonyx.gitonyx.model.UserModel;
import eu.gitonyx.gitonyx.route.Router;
import eu.gitonyx.gitonyx.route.user.UserRoute;

public class GitOnyx {

    private DatabaseConfig databaseConfig;
    private PolyDB polyDB;

    private Repository<UserModel> userRepository;
    private Router router;

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
            this.registerRoutes();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void registerModels() {
        this.userRepository = this.polyDB.repository(UserModel.class);
    }

    private void registerRoutes() {
        this.router = new Router("/api/v1");
        this.router.registerRoutes(new UserRoute(this.userRepository));
        this.router.start(7070);
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
