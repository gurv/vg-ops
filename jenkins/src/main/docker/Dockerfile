ARG JENKINS_VERSION=2.142
FROM jenkins/jenkins:${JENKINS_VERSION}-alpine

ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false"

RUN /usr/local/bin/install-plugins.sh \
    git:3.9.1 \
    gradle:1.29 \
    workflow-aggregator:2.5 \
    cloudbees-folder:6.6

COPY --chown=jenkins:jenkins src/main/groovy/init.groovy /var/jenkins_home/init.groovy.d/

EXPOSE 8080