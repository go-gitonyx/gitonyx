package eu.gitonyx.gitonyx.config;

import eu.gitonyx.gitonyx.config.annotation.Config;
import eu.gitonyx.gitonyx.config.annotation.DefaultValue;
import eu.gitonyx.gitonyx.config.annotation.Key;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Config(path = "db", value =  "database")
public class DatabaseConfig {

    @Key("host")
    @DefaultValue("127.0.0.1")
    private String host;

    @Key("port")
    @DefaultValue("3306")
    private int port;

    @Key("database")
    @DefaultValue("gitonyx")
    private String database;

    @Key("user")
    @DefaultValue("root")
    private String user;

    @Key("password")
    @DefaultValue("")
    private String password;

}
