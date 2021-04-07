### Запуск локально
* склонировать репозиторий
* `mvn clean install` из корня (должен быть прописан путь к home директории Maven в системных переменных)
* `cd target`
* `java -jar aeon-0.0.1-SNAPSHOT.jar` (должен быть прописан путь к home директории java в системных переменных)

### Тестирование
#### Логин
* POST запрос на <i>localhost:8080/login</i> Тело запроса должно быть типа <b>form-data</b> и содержать ключ-значение <b>username</b> и <b>password</b>

#### Логаут
* POST запрос на <i>localhost:8080/logout</i>

#### Payment
* POST запрос на <i>localhost:8080/api/payment</i> без тела запроса

#### База данных
* Используется in-memory база данных H2. Войти в консоль можно по url <i>localhost:8080/h2-console</i>

Данные для входа:
* строка подключения - <b>jdbc:h2:mem:testdb</b>
* логин и пароль - <b>postgres</b>

