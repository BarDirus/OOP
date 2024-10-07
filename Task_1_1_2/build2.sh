#!/bin/bash

# 1. Генерация документации с помощью javadoc
javadoc -d doc src/main/java/ru/nsu/shirokov/BlackJack.java

# Проверяем, завершилась ли команда успешно
if [ $? -ne 0 ]; then
  echo "Ошибка при генерации документации"
  read
  exit 1
fi

# 2. Компиляция исходного кода
javac -cp libs/junit-platform-console-standalone-1.11.0.jar ./src/main/java/ru/nsu/shirokov/BlackJack.java  -d ./build/classes/java/main
javac -cp libs/junit-platform-console-standalone-1.11.0.jar ./src/main/java/ru/nsu/shirokov/BlackJack.java ./src/test/java/ru/nsu/shirokov/BJtest.java  -d ./build/classes/java/test
# Проверяем, завершилась ли компиляция успешно
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции исходного кода"
  read
  exit 1
fi
# 3. Создание JAR-файла
jar -cmf manifest.mf BlackJack.jar -C build/classes/java/main .

# Проверяем, завершилось ли создание JAR-файла успешно
if [ $? -ne 0 ]; then
  echo "Ошибка при создании JAR-файла"
  read
  exit 1
fi

# 4. Запуск тестов с использованием JaCoCo
java -javaagent:libs/lib/jacocoagent.jar=destfile=build/jacoco/test.exec \
  -jar libs/junit-platform-console-standalone-1.11.0.jar \
  --class-path build/classes/java/test \
  --scan-classpath

# Проверяем, завершился ли запуск тестов успешно
if [ $? -ne 0 ]; then
  echo "Ошибка при запуске тестов"
  read
  exit 1
fi

# 5. Генерация отчета о покрытии кода
java -jar libs/lib/jacococli.jar report build/jacoco/test.exec \
    --classfiles build \
    --sourcefiles src/main/java \
    --html build/reports/jacoco \
    --name "Coverage Report"

# Проверяем, завершилась ли генерация отчета о покрытии успешно
if [ $? -ne 0 ]; then
  echo "Ошибка при генерации отчета о покрытии кода"
  read
  exit 1
fi
# 6. Запуск приложения (опционально)
java -jar BlackJack.jar

# Проверяем, завершился ли запуск приложения успешно
if [ $? -ne 0 ]; then
  echo "Ошибка при запуске приложения"
  read
  exit 1
fi
# 7. Сообщение об успешной сборке
read
echo "Сборка завершена успешно"