# SF-Movies

VIMP Note : Server will take time to get started as it fetches all the API data at the time of startup.
            So that all further server request will get completed very fast.

Notes :
1) Project runs on Maven.
2) Used Java version - 8.
2) Used tomcat version - 9.0.


Deployement Instructions : 
1) Open project in IDE and compile.
2) Change port as per your convenience in application.properties. Current port is 8083.
3) Run the project on localhost.
4) No more configuration needed to deploy and run.


Description to project :
# Functionlities in the project
1) Get all movies list from provided api on the start up of server.
2) Filter movie list to get list with movies which are only in san Francisco.
3) Filter movie list to get movies by location.
