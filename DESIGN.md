# Database Schema: 3 tables
1. Users
  - id (primary key)
  - email
  - username
  - password

2. Notes
  - id (primary key)
  - owner_id (foreign key: Users.id)
  - title
  - content (full note)
  - timestamps (created_at, updated_at)

3. Permissions
  - id (primary key)
  - user_id (who can access) (foreign key: Users.id)
  - note_id (what note can be accessed) (foreign key: Notes.id)
  - permission_level (view, edit, etc.)
  - UNIQUE(user_id, note_id) -- make sure each user can have one permission entry per note

![Database Schema](note_sharing_project_schema.png)
