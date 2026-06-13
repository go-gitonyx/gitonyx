package eu.gitonyx.gitonyx.model;

import eu.gitonyx.gitonyx.annotation.database.Column;
import eu.gitonyx.gitonyx.annotation.database.ColumnType;
import eu.gitonyx.gitonyx.annotation.database.Table;

@Table("users")
public class UserModel {
    
    @Column(name = "id", type = ColumnType.INT, nullable = false, unique = true)
    private int id;

    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

}
