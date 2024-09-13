java -javaagent:libs/jacocoagent.jar=destfile=build/jacoco/test.exec \
  -jar libs/junit-platform-console-standalone-1.11.0.jar \
  --class-path build/classes/java/main \
  --scan-classpath
read