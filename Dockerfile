FROM maven:3.5.2-jdk-8 as builder

ADD . /build/
WORKDIR /build/
RUN ["mvn","-DskipTests=true","package"]

FROM gizmotronic/openfire:4.2.2

#默认utf-8
ENV LANGUAGE=en_US.UTF-8
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
RUN locale-gen en_US.UTF-8

#调整原入口
RUN mv /sbin/entrypoint.sh /sbin/entrypoint-org.sh
COPY ./entry.sh /sbin/entrypoint.sh
RUN chmod +x /sbin/entrypoint.sh
# 我们编译的plugins
RUN mkdir /fixed_plugins
COPY --from=builder /build/plugins/accountCenter/target/accountCenter.jar /fixed_plugins/
COPY --from=builder /build/plugins/offlinePush/target/offlinePush.jar /fixed_plugins/
COPY --from=builder /build/plugins/restAPI/target/restAPI.jar /fixed_plugins/
COPY --from=builder /build/plugins/mucPlugin/target/mucPlugin.jar /fixed_plugins/

#or curl with --output <file> --directory-prefix=
RUN wget http://www.igniterealtime.org/projects/openfire/plugins/clientControl.jar --directory-prefix=/fixed_plugins/
RUN wget http://www.igniterealtime.org/projects/openfire/plugins/presence.jar --directory-prefix=/fixed_plugins/
RUN wget http://www.igniterealtime.org/projects/openfire/plugins/websocket.jar --directory-prefix=/fixed_plugins/
#javascript:downloadPlugin('http://www.igniterealtime.org/projects/openfire/plugins/clientControl.jar', '1682004675')
#COPY ./chinese.sh /data/
#CMD ["/sbin/entry.sh"]
