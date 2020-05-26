# Transaction-Management-Approach

1. Declarative: @Transactional
2. Programmatic: TransactionTemplate
* Both use PlatformTransactionManager API(lower-level) to manage their transactions internally.

----------------------------------------------------------------------------------------------------

# In Transaction

1. Inject DataSource to JdbcTemplate
2. Inject DataSource to LocalContainerEntityManagerFactoryBean then create EntityManager
3. Use JdbcTemplate(JDBC) or EntityManager(JPA) to manipulate Database.
