# Description

This repository contains feature flags and eventing for a simple todo application.
Each todo has a single field, description, that is used to describe the todo task.
When the todo is created the logger subscribes to a published event and stored in
the database. The different functionalities of the application (create, edit, delete)
are gated through several feature flags. Create is a simple on/off flag, edit is deployed
for a specific username, and delete is rolled out to 50% of users. Feature Togglz can
be configured at the address: http://localhost:8080/togglz-console.

# Getting Started
To start the frontend, go into the myapp directory and type:
```
npm install
npm start
```

Navigate to http://localhost:3000 and the website UI should be available.

To start the backend, go into the demo 2 folder and type:
```
mvn spring-boot:run
```

To see the database values, grab the output for the memory address
of the h2 console, which could be something like the below in the terminal:
```
2021-08-22 08:50:04.726  INFO 12063 --- [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:8d147fec-bf80-4c5e-867b-1bbf30382af4'
```

To open the database H2 Console perform the following:
```
1. navigate in the browser to : http://localhost:8080/h2-console
2. in the JDBC URL type the memory address, in this case: jdbc:h2:mem:8d147fec-bf80-4c5e-867b-1bbf30382af4
3. leave the rest as the default and click "connect"
```

To open Togglz Feature Flagging open the browser at:
http://localhost:8080/togglz-console