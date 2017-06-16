FROM zlp11313.vci.att.com:5100/com.att.ajsc/anapsix/alpine-java:8_jdk
VOLUME /tmp
ADD salesappservice-0.0.1-SNAPSHOT.jar app.jar
VOLUME /etc
ADD /etc/ /etc/
ADD startService.sh /startService.sh
RUN chmod 700 /startService.sh
ENTRYPOINT ./startService.sh 
