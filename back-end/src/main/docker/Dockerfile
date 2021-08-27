FROM openjdk:8-jre-alpine

# 替换阿里云的源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

# 更新源、同步时间
RUN apk update \
    && apk add ca-certificates \
    && apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && date

ENV DJ_SLEEP 0
ENV PROFILE dev
ARG JAR_FILE

# add directly the war
ADD ${JAR_FILE} /code.war

RUN sh -c 'touch /code.war'
VOLUME /tmp
EXPOSE 8887
CMD echo "The application will start in ${DJ_SLEEP}s..." && \
    sleep ${DJ_SLEEP} && \
    java -jar -Ddefault.client.encoding="UTF-8" -Dfile.encoding="UTF-8" -Duser.language="Zh" -Duser.region="CN" code.war --spring.profiles.active=${PROFILE}
