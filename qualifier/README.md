Qualifier
==========
####Application Contract

The qualifier should read json with the following format off of a Kafka topic:

  ```json
  {
    "geo": {
      "lat": DOUBLE,
      "lon": DOUBLE
    },
    "adventure_token": "ADVENTURE_TOKEN",
    "user_id": UUID,
    "image_url_to_S3": "URL_TO_S3"
  }
  ```

##Scenarios

###Scenario #1
####Initial setup:

Users Table:

| UserId  | AdventureStatus |
| ------ | --------------  |
| 1 | 0 |
| 2 | 0 |
| 3 | 0 |

Events:
 - UserId 1 takes a picture in the weshare app
 - Check if 1 is in an adventure or not => no
 - Find friends of 1 and determine if any of them are within the Boundary (general radius of where picture was taken)
 - Determine if any friends in the Boundary are already in an adventure or not => no
 - Create adventure in adventure table, and send invite to friends in the boundary
