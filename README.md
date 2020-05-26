# Transaction-Management-Approach
@Transactional(Declarative), TransactionTemplate(Programmatic) => Both use low-level PlatformTransactionManager

# In Transaction

1. Inject DataSource to JdbcTemplate
2. Inject DataSource to LocalContainerEntityManagerFactoryBean then create EntityManager
3. Use JdbcTemplate(JDBC) or EntityManager(JPA) to manipulate Database.
