# 1. Project Overview 
A web application that allows users to create, store, and share notes with other users.
Notes are private by default, and owners can control access through fine-grained permissions.
The goal of this project is to demonstrate backend fundamentals, access control, and full-stack thinking.

# 2. Backend Foundation
Set up the server, database connection, and basic project structure. 
Establish a clear separation between controllers, services, and data access so the codebase is easy to reason about and extend.

# 3. Database Schema
Design SQL tables for users, notes, and permissions. 
Define clear relationships so ownership and access rules can be enforced reliably at the data level.

# 4. Authentication
Allow users to sign up, log in, and stay authenticated across requests. 
Ensure protected routes cannot be accessed by unauthenticated users.

# 5. Note CRUD Operations
Enable authenticated users to create, read, update, and delete their own notes. 
Ensure users cannot access or modify notes they do not own unless explicitly permitted.

# 6. Fine-Grained Permissions (Core Feature)
Allow note owners to grant specific users view or edit access to individual notes. 
Enforce permissions server-side so access rules cannot be bypassed from the frontend.

# 7. Authorization Enforcement
Centralize permission checks so every request verifies whether the user is allowed to perform the action. 
Unauthorized requests should fail safely and consistently.

# 8. Frontend Foundation
Create a simple UI that allows users to sign up, log in, and navigate the app. 
Focus on clarity and usability rather than visual polish.

# 9. Frontend Note Management
Build UI flows for creating, viewing, editing, and deleting notes. 
Display different actions depending on whether the user is an owner, editor, or viewer.

# 10. Sharing & Permissions UI
Allow note owners to manage who has access to each note and at what level. 
The UI should clearly reflect current permissions and prevent invalid actions.

# 11. Error Handling & Validation
Handle invalid input, unauthorized actions, and edge cases gracefully. 
Errors should be clear to users and safe from a security standpoint.

# 12. Testing & Manual Verification
Manually test critical flows such as login, note sharing, and permission enforcement. 
Verify that security rules hold even when requests are manipulated.

# 13. Documentation & Polish
Update the README with a project overview, features, and key design decisions. 
Ensure the repo clearly communicates what was built and why.
