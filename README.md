The project contains below functionalities : 

1. SPRING-SECURITY usage to filter the API Requests based on roles assigned to the user.
2. Token based accessibility to API that expired in 5 minutes.
3. App can be dockerized. 

APIs:

1. Register User
   Method - POST - http://localhost:8080/demo-api/auth/register 
   Body -  {
              "username": "john",
              "password": "john123",
              "role": "USER"
  }

  Valid role values : [USER,ADMIN]

2. Generate Token 
    Method - POST - http://localhost:8080/demo-api/auth/login
    Body - {
              "username": "john",
              "password": "john123"
            }
3. List Users
   Method - GET - http://localhost:8080/demo-api/ven-users  [Allowed only for "ADMIN" roles]
4. List Todos
   Method - GET - http://localhost:8080/demo-api/ven-todos  [Allowed for both "ADMIN" and "USER" roles].

