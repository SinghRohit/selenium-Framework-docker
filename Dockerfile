FROM amazoncorretto:11.0.23

#install curl and jq
RUN yum install -y curl jq dos2unix wget telnet

#workspace
WORKDIR /home/Selenium-docker

#add the required source and lib files
ADD target/docker-resources .
ADD runner.sh runner.sh

#Run the tests
#ENTRYPOINT java "-Dselenium.grid.enabled=true" "-Dselenium.grid.hubHost=192.168.1.10" -cp 'libs/*' org.testng.TestNG test-suites/flight-reservation.xml -threadcount 2
#ENTRYPOINT java -cp 'libs/*' \
#     -Dselenium.grid.enabled=true \
#     -Dselenium.grid.hubHost="${HUB_HOST:-hub}" \
#     -Dbrowser="${BROWSER:-chrome}" \
#     org.testng.TestNG \
#     -threadcount "${THREAD_COUNT:-1}" \
#     test-suites/"${TEST_SUITE}"
#RUN dos2unix runner.sh
RUN chmod +x runner.sh
ENTRYPOINT sh runner.sh