package com.raheemspence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"user_id", "note_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    @Column(name = "permission_level", nullable = false)

    /*
        This annotation tells JPA/Hibernate how to stor a Java enum in the db because enums dont naturally exist in SQL
        db's, so hibernate needs a rule.

        EnumType.STRING means db stores text for example --> permission_level = "VIEWER"

        EnumType.ORDINAL (default, if not specified) means db stores numbers like permission_level = 0 so 0 would mean
        EDITOR which is wrong and data gets corrupted.
     */
    @Enumerated(EnumType.STRING)
    private PermissionLevel permissionLevel;
}
