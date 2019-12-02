# ExerciseToMteam
My Android exercice


FLICKR API is not RESTfull in any way, no mediatypes used and wrong use of http status codes as should be (is returning 200 in case of client error while should be trowing a status code 4xx, for example ).
This situation turns error handling very ugly, since is needed to handle http errors and flickr api errors in different ways, going against the REST conventions written by Roy Fielding that state that anykind of server/client error handling behaviour, must be based in the info obtained by the metadata(status code, mainly) obtained in the HTTP interactions.

Is used GSON for JSON serialization and VOLLEY for optimized HTTP requests.
