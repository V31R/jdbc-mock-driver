[![Java CI with Maven](https://github.com/V31R/jdbc-mock-driver/actions/workflows/maven.yml/badge.svg)](https://github.com/V31R/jdbc-mock-driver/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/V31R/jdbc-mock-driver/branch/master/graph/badge.svg?token=7FSW8N93SZ)](https://codecov.io/gh/V31R/jdbc-mock-driver)
***
# JDBC mock driver
***
[Примеры работы с драйвером](https://github.com/V31R/jdbc-mock-example)
***
## Использование драйвера

### Зависимости для проекта
Подключение драйвера к проекту выглядит также, как и у других драйверов, нужно указать зависимость.

Для `Maven` в `pom.xml`:
```
<dependency>
    <groupId>org.mock.jdbc</groupId>
    <artifactId>HttpDriver</artifactId>
    <version>1.0</version>
</dependency>
```
Также для логирования драйвер использует `slf4j`, поэтому к проекту нужно подключить реализацию логгера.
***
### URL для подключения к драйверу

Для работы с драйвером при подключении нужно указать `url`:
```
jdbc:wm:<host>:<port>/<url>
```
К примеру:
```
jdbc:wm://localhost:8080/sql-mock
```
Для корректной работы драйвера нужно наличие сервера(или подмены сервера, к примеру с помощью [WireMock](https://wiremock.org/)), 
к которому можно будет делать `http` запросы и получать ответ в формате `csv`. 
***
### Ответы на запросы

Все ответы должны быть в формате `csv`, к примеру:
```
"field1", "field2"
    1   ,   name1
    2   ,   name2
```
Для упрощения работы с `select` запросами драйвер сам выберет имена для полей в результате запроса.*

К примеру для запроса:
```
select data1, data2 as name1 from table
``` 
Драйвер выберет имена `data1` и `name1`, соответственно,
***вне зависимости от того, какие имена столбцов были возвращены***. 
Для драйвера важно только, чтобы в результате было такое же количество столбцов, сколько было в `select` запросе.

**- Не работает с `select *` запросами, в их результатах нужно следить за именами столбцов.*
***
### Использование вместе с Spring Boot

Для работы с этим драйвером, как и с любым другим `jdbc` драйвером, 
необходимо указать его данные в `application-XXX.properties` файле:
```
spring.datasource.url=jdbc:wm://localhost:8080/sql-mock
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=org.example.HttpDriver
```
Указание `spring.datasource.username` и `spring.datasource.password` необязательно.

Также для корректной работы `Hibernate` с драйвером необходимо указать `hibernate.dialect` в 
`application-XXX.properties` файле следующим образом:
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
***
## Диаграмма покрытия кода:

[![Диаграмма покрытия кода](https://codecov.io/gh/V31R/jdbc-mock-driver/branch/master/graphs/sunburst.svg?token=7FSW8N93SZ)](https://codecov.io/gh/V31R/jdbc-mock-driver)