package eu.gitonyx.gitonyx.model;

import java.sql.Timestamp;
import java.util.UUID;

import de.tnttastisch.polydb.core.annotations.Column;
import de.tnttastisch.polydb.core.annotations.Entity;
import de.tnttastisch.polydb.core.annotations.Id;
import de.tnttastisch.polydb.core.annotations.Table;
import de.tnttastisch.polydb.core.annotations.Unique;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class UserModel {
    
    @Id
    @Column(name = "id")
    private UUID id;

    @Unique
    @Column(name = "username", nullable = false, length = 32)
    private String username;

    @Unique
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at")
    private Timestamp createdAt;

}
