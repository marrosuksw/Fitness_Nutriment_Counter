# Fitness Nutriment Counter 
### Backend REST API for the FNC application made with Spring Boot

#### Main technologies and libraries used:
- **Hibernate** ORM for entity mapping
- **MySQL** configuration within a **Docker** container
- **OpenFoodFacts API** connection via custom RESTClient (API docs: https://world.openfoodfacts.org/)(https://openfoodfacts.github.io/openfoodfacts-server/api/))
- **Spring Security** configuration with CORS for React client connection
- **JWT** for creating and configuring access and refresh tokens
- **Lombok** for eliminating repetitive code with annotations
- **JUnit5** for unit testing

#### Features:
- fetch food product data from a global database
- create and store users and user personal data
- calculate calorie intake for users based on their personal data
