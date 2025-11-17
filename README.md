# DB Setup
  database: mysql  
  sql file: setup.sql  
  
# Swagger
http://localhost:8080/swagger-ui/index.htm  

# CI Settings
.github/workflows/ci.yml  

# Main Function
## User
1) Register: /api/users/register  
2) Login: /api/users/login  
## Authorization
1) Create role: /api/roles  
2) Add role to user: /api/roles/{roleId}/users/{userId}  
3) Add permission for user: /api/permissions/users/{userId}/todos/{todoId}  
4) Add permission for role: /api/permissions/roles/{roleId}/todos/{todoId}
## todo
Require user login first and will get user from session 
1) Create: /api/todo(POST)  
Add edit permission for user at the same time
2) Update: /api/todo(PUT)  
Require edit permission
3) Get: /api/todo/{id}(GET)  
Require view or edit permission
4) Delete: /api/todo/{id}(DELETE)  
Require edit permission
5) Query: /api/todo/query(GET)  
Query all todo with edit or view permission
