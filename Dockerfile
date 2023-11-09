#!/bin/bash

# Extract the finalName from pom.xml
FINAL_NAME=$(xmllint --xpath 'string(/project/build/finalName)' pom.xml)

# Check if finalName is not empty
if [ -z "$FINAL_NAME" ]; then
  echo "Error: Failed to extract finalName from pom.xml"
  exit 1
fi

# Create the Dockerfile with the extracted finalName
cat <<EOF > Dockerfile
FROM openjdk:11-jdk
EXPOSE 8089
ADD ./target/$FINAL_NAME.jar waelhcine-$FINAL_NAME.jar
ENTRYPOINT ["java", "-jar", "/waelhcine-$FINAL_NAME.jar"]

LABEL app.finalName=$FINAL_NAME
EOF
