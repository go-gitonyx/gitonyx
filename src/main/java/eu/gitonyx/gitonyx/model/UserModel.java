package eu.gitonyx.gitonyx.model;

import de.tnttastisch.polydb.core.annotations.*;

@Entity
@Table(name = "users")
public class UserModel {
    
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

}
