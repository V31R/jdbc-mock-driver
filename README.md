[![Java CI with Maven](https://github.com/V31R/jdbc-mock-driver/actions/workflows/maven.yml/badge.svg)](https://github.com/V31R/jdbc-mock-driver/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/V31R/jdbc-mock-driver/branch/master/graph/badge.svg?token=7FSW8N93SZ)](https://codecov.io/gh/V31R/jdbc-mock-driver)
# JDBC mock driver

[Примеры работы с драйвером](https://github.com/V31R/jdbc-mock-example)

## TODO: сделать driver и readme

## Использование драйвера

### Использование вместе с Spring Boot

Для правильной работы `Hibernate` с драйвером нобоходимо указать `hibernate.dialect` в `application-XXX.properties` файле следующим образом:
```
spring.jpa.database-platform = org.hibernate.dialect.<Диалект для СУБД, под которую нужно мимикрировать>
```
Примеры:
1.  Для MySQL 8 версии:
```
spring.jpa.database-platform = org.hibernate.dialect.MySQL8Dialect
```
2. Для PostgreSQL:
```
spring.jpa.database-platform = org.hibernate.dialect.PostgreSQLDialect
```

## Диаграмма покрытрия кода:

[![Диаграмма покрытия кода](https://codecov.io/gh/V31R/jdbc-mock-driver/branch/master/graphs/sunburst.svg?token=7FSW8N93SZ)](https://codecov.io/gh/V31R/jdbc-mock-driver)