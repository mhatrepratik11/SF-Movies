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
1) Get all movies list from provided api and stored to the list on the start up of server.
2) Get the latitude and the longitude of the above locations using the Maps API.
2) Send all the data along with the coordinates of all locations as response to the JavaScript POST request as well as Android    POST request.
3) Filter movie list to get movies by location and send response to the JavaScript POST request as well as Android POST            request.
4) Filter list to get movies only in San Francisco and send response to the JavaScript POST request as well as Android POST        request.
