javac -cp libs/junit-platform-console-standalone-1.11.0.jar ./src/main/java/ru/nsu/shirokov/HeapSort.java ./src/main/java/ru/nsu/shirokov/HeapSortTest.java -d ./builddir
read
if [[ $? -ne 0 ]]; then
    echo "epic fail! (compilation)"
    exit 101
fi