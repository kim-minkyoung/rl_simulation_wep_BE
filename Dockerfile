# 기본 이미지로 latest JDK 버전을 사용
FROM openjdk:latest

# 빌드 과정에서 생성된 JAR 파일을 참조하는데 사용할 ARG (인자) 선언
ARG JAR_FILE=build/libs/*.jar

# JAR 파일을 Docker 이미지 내부의 'app.jar'로 복사
COPY ${JAR_FILE} app.jar

#ENV docker-app 'Hello, Docker App !'

# 컨테이너가 시작될 때 실행될 명령 정의
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]