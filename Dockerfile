# Use a slim image for smaller base image size
FROM openjdk:11-jdk-slim

# Set environment variables
ENV SBT_VERSION=1.8.0 \
    SBT_HOME=/opt/sbt \
    PATH=${PATH}:/opt/sbt/bin \
    SBT_OPTS="-Xms128m -XX:+UseG1GC -XX:+UseStringDeduplication -Dfile.encoding=UTF-8"

# Install required dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl unzip && \
    rm -rf /var/lib/apt/lists/*

# Install SBT
RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zip && \
    unzip sbt-$SBT_VERSION.zip -d /opt && \
    rm sbt-$SBT_VERSION.zip

# Copy only the files needed for the sbt build
# COPY build.sbt /app/
# COPY project /app/project

# Set working directory
WORKDIR /app

ADD . /app

# Compile the application
RUN sbt compile

# Expose application port
EXPOSE 9000

# Use 'sbt stage' to create a start script and use it as the CMD
CMD sbt runProd
