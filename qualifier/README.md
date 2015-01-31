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

####Workflow

If the user_id is in an adventure send photo to all user_id in adventure
If the user_id is NOT in an adventure create adventure, and invite nearby friends

