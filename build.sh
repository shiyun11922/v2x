mvn clean package -DskipTests

rm -rf output && mkdir -p output

cp ./target/seed-*.jar output/