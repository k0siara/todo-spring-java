package com.patrykkosieradzki.todo;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrations implements InitializingBean {

    @Value("${JDBC_DATABASE_URL}")
    private String url;

    @Value("${JDBC_DATABASE_USERNAME}")
    private String username;

    @Value("${JDBC_DATABASE_PASSWORD}")
    private String password;

    @Override
    public void afterPropertiesSet() throws Exception {
        migrate();
    }

    private void migrate() {
        Flyway flyway = Flyway.configure().dataSource(url, username, password).load();
        flyway.migrate();
    }

}
