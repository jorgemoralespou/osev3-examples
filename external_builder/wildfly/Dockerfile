FROM openshift/base-centos7

MAINTAINER Jorge

EXPOSE 8080

ENV WILDFLY_VERSION 8.1

# Install Maven, Wildfly 8
RUN yum install -y --enablerepo=centosplus tar unzip bc which lsof java-1.8.0-openjdk && \
    yum clean all -y && \
    mkdir -p /wildfly && \
    (curl -0 http://download.jboss.org/wildfly/8.1.0.Final/wildfly-8.1.0.Final.tar.gz | tar -zx --strip-components=1 -C /wildfly) && \
    chown 1001 /wildfly && chmod -R go+rw /wildfly

# Create wildfly group and user, set file ownership to that user.
USER 1001

CMD ["/wildfly/bin/run.sh"]
