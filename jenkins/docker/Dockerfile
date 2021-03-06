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
    blueocean:1.8.4 \
    ansible:1.0

COPY --chown=jenkins:jenkins ini/init.groovy /var/jenkins_home/init.groovy.d/

# Gradle
ARG GRADLE_VERSION=5.3
ARG GRADLE_HOME=/opt/gradle-$GRADLE_VERSION

USER root

RUN mkdir -p /opt && \
    apk --update add --no-cache unzip && \
    mkdir -p /tmp/download && \
    # Gradle
    curl -L -o /tmp/download/gradle-${GRADLE_VERSION}-bin.zip https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip /tmp/download/gradle-${GRADLE_VERSION}-bin.zip -d /opt/ && \
    # NodeJs
    apk add nodejs-npm && \
    # Ansible
    apk add ansible=2.5.5-r0 \
    #
    rm -rf /tmp/download && \
    rm -rf /var/cache/apk/*

# Gradle
COPY gradle/init.gradle /opt/gradle-${GRADLE_VERSION}/init.d/
ENV GRADLE_HOME=$GRADLE_HOME
ENV PATH=$PATH:$GRADLE_HOME/bin

USER jenkins

# NodeJs
RUN npm config set registry http://nexus:8081/repository/npm-group/

EXPOSE 8080