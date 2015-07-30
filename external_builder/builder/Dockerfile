FROM openshift/base-centos7

MAINTAINER Jorge

# Install Maven, Wildfly 8
RUN yum install -y --enablerepo=centosplus tar unzip bc which lsof git java-1.8.0-openjdk java-1.8.0-openjdk-devel && \
    yum clean all -y && \
    (curl -0 http://mirror.sdunix.com/apache/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz | tar -zx -C /usr/local) && \
    ln -sf /usr/local/apache-maven-3.0.5/bin/mvn /usr/local/bin/mvn && \
    mkdir -p /sti/source && \
    mkdir -p /sti/deployments && \
    mkdir -p /sti/modules && \
    mkdir -p /sti/configuration && \
    mkdir -p /.m2/repository

# STI scripts will be in /usr/local/sti
COPY ./sti/bin/ /sti/scripts/

# Deployments folder
RUN chmod -R go+rw /sti && chmod go+x /sti/scripts/* 
VOLUME ["/sti"]

COPY settings.xml /.m2/
RUN chmod -R go+rw /.m2 
VOLUME ["/.m2"]


USER 1001

CMD ["usage"]
