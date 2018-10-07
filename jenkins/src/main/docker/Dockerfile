ARG JENKINS_VERSION=2.142
FROM jenkins/jenkins:${JENKINS_VERSION}-alpine

ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false"

RUN /usr/local/bin/install-plugins.sh \
    git:3.9.1 \
    gradle:1.29 \
    workflow-aggregator:2.5 \
    cloudbees-folder:6.6 \
    prometheus:1.2.2 \
    nodejs:1.2.6 \
    blueocean:1.8.4

COPY --chown=jenkins:jenkins src/main/groovy/init.groovy /var/jenkins_home/init.groovy.d/

# Gradle
ARG GRADLE_VERSION=4.9
ARG GRADLE_HOME=/opt/gradle-$GRADLE_VERSION
# NodeJs
ARG NODEJS_VERSION=8.11.4
ARG NODE_HOME=/opt/node-v${NODEJS_VERSION}-linux-x86

USER root

RUN mkdir -p /opt && \
    apk --update add --no-cache unzip && \
    mkdir -p /tmp/download && \
    # Gradle
    curl -L -o /tmp/download/gradle-${GRADLE_VERSION}-bin.zip https://services.gradle.org/distributions/gradle-4.9-bin.zip && \
    unzip /tmp/download/gradle-${GRADLE_VERSION}-bin.zip -d /opt/ && \
    # NodeJs
    apk add nodejs-npm==${NODEJS_VERSION}-r0 && \
    #
    rm -rf /tmp/download && \
    rm -rf /var/cache/apk/*

# Gradle
COPY src/main/gradle/init.gradle /opt/gradle-${GRADLE_VERSION}/init.d/
ENV GRADLE_HOME=$GRADLE_HOME
ENV PATH=$PATH:$GRADLE_HOME/bin

USER jenkins

EXPOSE 8080