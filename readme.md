## Use JPA + Hibernate + MySQL in Spring Boot

See here for more informations: 
http://blog.netgloo.com/2014/10/06/spring-boot-data-access-with-jpa-hibernate-and-mysql/

### Usage

- Setup mysql database with database name insantani and use port 3036
- Run the application and go on http://localhost:8080/
- Use the following urls to invoke controllers methods and see the interactions
  with the database:
    * `api/vegetable/find?name=[name]`: find a vegetable based on name.
    * `api/vegetable/sugesstion?name=[name]`: find sugesstions of vegetable maximum 4 sugesstions.

### Build and run

#### Configurations

Open the `application.properties` file and set your own configurations for the
database connection.

#### Prerequisites

- Java 7
- Maven 3

#### From terminal

Go on the project's root folder, then type:

    $ mvn spring-boot:run

#### From Eclipse (Spring Tool Suite)

Import as *Existing Maven Project* and run it as *Spring Boot App*.
