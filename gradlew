#!/bin/bash

# Переменные
SRC_DIR="src/main/java/ru/nsu/shirokov"
OUT_DIR="out"
DOC_DIR="docs"
TEST_DIR="src/test/java/ru/nsu/shirokov"
JAR_NAME="HeapSort.jar"

# Создаем директории для компиляции и документации
mkdir -p $OUT_DIR
mkdir -p $DOC_DIR

# Компилируем исходный код
echo "Компиляция исходников..."
javac -d $OUT_DIR $SRC_DIR/main/java/ru/nsu/shirokov/HeapSort.java

# Создание JAR файла
echo "Создание JAR файла..."
jar cfe $JAR_NAME com.example.HeapSort -C $OUT_DIR .

# Генерация документации
echo "Генерация JavaDoc..."
javadoc -d $DOC_DIR $SRC_DIR/com/example/HeapSort.java

# Запуск программы
echo "Запуск программы..."
java -jar $JAR_NAME

# Компиляция и запуск тестов
echo "Компиляция тестов..."
javac -cp $OUT_DIR:$TEST_DIR:. -d $OUT_DIR $TEST_DIR/com/example/HeapSortTest.java

echo "Запуск тестов..."
java -cp $OUT_DIR org.junit.platform.console.ConsoleLauncher --scan-classpath --include-classname '.*Test'