package com.raheemspence.model;

/*
    An enum is a special Java type that defines a fixed set of allowed values for a field
    This enum means a permission cna ONLY be VIEWER or EDITOR.

    W/o enum permission level can be any string like "banana" etc. With it, mistakes are caught at compile time

    Moreover:
    Enum represents a fixed set of constants for a type.

    PermissionLevel defines all valid permission states in the system.
    A permission can ONLY be one of these values:
    - VIEWER
    - EDITOR

    This prevents invalid values like "bananas" or typos like "viwer".

    Using an enum also makes the code type-safe and enforces correctness at compile time.
 */
public enum PermissionLevel {
    VIEWER,
    EDITOR
}
