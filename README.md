# java-professional-otus-homeworks

Автор: Данильченко Иван Сергеевич

Описание: домашние задания для курса java-professional

Настройка версий зависимостей осуществляется в файле gradle/libs.versions.toml

Команды:
- Для сборки fat-jar( исполняемого jar ) используете команду: ./gradlew fatjar
- Для очистки проекта используйте команду: ./gradlew clean
- Для запуска fat-jar используйте команду java -jar ./{subproject-name}-{version}-application.jar, например: java -jar ./hw01-gradle-0.0.1-application.jar 