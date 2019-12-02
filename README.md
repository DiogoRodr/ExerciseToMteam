# ExerciseToMteam
My Android exercice


FLICKR API is not RESTfull in any way, no mediatypes used and wrong use of http status codes as should be (returning 200 in case of error, for example ).
This situation turns error handling very ugly, since is needed to handle http errors and flickr api errors in different ways, going against the REST conventions written by Roy Fielding that state that anykind of server/client error must be thrown with the proper HTTP status code.

Is used GSON for JSON serialization and VOLLEY for optimized HTTP requests.
