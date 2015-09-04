# wildflyswarm-s2i
FROM openshift/base-centos7
MAINTAINER Jorge Morales <jmorales@redhat.com>

# Install build tools on top of base image
# Java jdk 8, Maven 3.3, Gradle 2.6
ENV GRADLE_VERSION 2.6
ENV MAVEN_VERSION 3.3.3

RUN yum install -y --enablerepo=centosplus \
    tar unzip bc which lsof java-1.8.0-openjdk java-1.8.0-openjdk-devel && \
    yum clean all -y && \
    (curl -0 http://apache.rediris.es/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | \
    tar -zx -C /usr/local) && \
    mv /usr/local/apache-maven-$MAVEN_VERSION /usr/local/maven && \
    ln -sf /usr/local/maven/bin/mvn /usr/local/bin/mvn && \
    curl -sL -0 https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip /tmp/gradle-${GRADLE_VERSION}-bin.zip -d /usr/local/ && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    mv /usr/local/gradle-${GRADLE_VERSION} /usr/local/gradle && \
    ln -sf /usr/local/gradle/bin/gradle /usr/local/bin/gradle && \
    mkdir -p /opt/openshift && chmod -R a+rwX /opt/openshift &&\
    mkdir -p /opt/app-root/source && chmod -R a+rwX /opt/app-root/source && \
    mkdir -p /opt/s2i/destination && chmod -R a+rwX /opt/s2i/destination && \
    mkdir -p /opt/app-root/src && chmod -R a+rwX /opt/app-root/src && \
    mkdir -p /opt/.m2 && chmod -R a+rwX /opt/.m2

ENV PATH=/opt/maven/bin/:/opt/gradle/bin/:$PATH M2_LOCAL=/opt/.m2


ENV BUILDER_VERSION 1.0

LABEL io.k8s.description="Platform for building Wildfly Swarm applications with maven or gradle" \
      io.k8s.display-name="WIldfly Swarm builder 1.0" \
      io.openshift.expose-services="8080:http" \
      io.openshift.tags="builder,maven-3,gradle-2.6,wildflyswarm"

# TODO (optional): Copy the builder files into /opt/openshift
# COPY ./<builder_folder>/ /opt/openshift/
# COPY Additional files,configurations that we want to ship by default, like a default setting.xml

LABEL io.openshift.s2i.scripts-url=image:///usr/local/sti
COPY ./.sti/bin/ /usr/local/sti

RUN chown -R 1001:1001 /opt/openshift /opt/.m2

# This default user is created in the openshift/base-centos7 image
USER 1001

# Set the default port for applications built using this image
EXPOSE 8080

# Set the default CMD for the image
# CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/openshift/app.jar"]
CMD ["usage"]
