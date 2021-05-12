From openjdk:8
Expose 8083
ADD /raleyLogin.jar raleyLogin.jar
ENTRYPOINT ["java","-jar","raleyLogin.jar"]
CMD java - jar raleyLogin.jar