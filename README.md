# vicarius
Task:

As a Backend developer, your task is to improve the efficiency of our Web API usage by
preventing abuse caused by excessive requests from multiple users (quota).

To accomplish this, implement a Spring Boot application with a robust access-limiting
mechanism that ensures optimal performance and resource utilization.

Make sure your application is protected from edge cases, and that it is runnable.
Focus on clean code, proper http responses and both OOP & RESTful API best
practices.
Take also scalability, readability and maintainability into consideration.

● The application is consist of the following resources and models, and should
implement the following APis:
○ User (ID, First name, Last name).
APIs:
■ Create
■ Delete
■ Update
■ Get single
■ Get all
○ QuotaResourceOne (ID, Blocking-Threshold).
APIs:
■ ConsumeQuotaResourceOne(UserId)
■ Get user's QuotaResourceOne status(UserId)
○ QuotaResourceTwo (ID, Blocking-Threshold).
APIs:
■ ConsumeQuotaResourceTwo(UserId)
■ Get user’s QuotaResourceTwo status(UserId)

● The APIs ConsumeQuotaResourceOne and ConsumeQuotaResourceTwo should
accept up to [Blocking-Threshold] API requests per user.
Once the user passed the quota of the [Blocking-Threshold] requests of this API,
he should be always blocked when calling this API.

● For business reasons, we’re storing our data in 2 different databases -
MySql and NotARealDB.
○ MySql is active during the first 3/4 of the day (12 am -> 5:59 pm)
and NotARealDB is active during the other 1/4 (6 pm -> 11:59 pm).
We only read/write data from/to the active database.
○ You only need to have a real implementation of MySql database, using local
docker.
○ NotARealDB can be implemented with printing methods or just mock responses.
○ You do not need to sync between the databases.

● Please note:
○ Do not use third-party libraries for the quota mechanism (spring boot starter etc.).
○ Assume there will be only 1 instance of your application at any given time.
○ Both authentication and authorization are not required to be implemented.

● Bonus points:
1. Add Unit tests.
2. Support scaling your application to more than 1 instance.
