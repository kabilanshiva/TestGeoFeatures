# TestGeoFeatures

SpringBoot microservice to store GeoJson file in Postgis DB and retrieve based on optional filters
EndPoints : 
1. http://localhost:8080/geoalert/features - consumes GeoJson multipart file
2.http://localhost:8080/geoalert/query   - consumes query parameter classId and json body containing Feature
