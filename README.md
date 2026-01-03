### Mongo DB Connection in Spring Boot 4.0

```kts
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb-test")
}
```

```properties
spring.data.mongo.uri=<YOUR_MONGODB_CONNECTION_URI>
spring.data.mongo.database=<YOUR_MONGODB_DATABASE_NAME>
```

##### Usage of MongoDB in Spring Boot

```kotlin
@Document(collection = "tasks")
data class Task(
    @org.springframework.data.annotations.Id
    val taskId: String,
    val name: String,
    val severity: Int,
    val assignee: String
)

@Repository
interface TaskRepository : org.springframework.data.mongodb.repository.MongoRepository<Task, String> {
    fun findBySeverityAndAssignee(severity: Int, assignee: String): List<Task>

    @org.springframework.data.mongodb.repository.Query("{ name: ?0 , assignee: ?1 }")
    fun getTaskByNameAndAssignee(name: String, assignee: String): Task?
}
```