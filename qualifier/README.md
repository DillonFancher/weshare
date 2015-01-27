Qualifier
==========
  ####Application Contract
  The qualifier should read json with the following format off of a Kafka topic:

  ```json
  {
    geo: {
      lat: DOUBLE,
      lon: DOUBLE
    },
    session_token: SESSION_TOKEN,
    picture_id: UUID,
    image_url_to_S3: URL_TO_S3
  }
  ```
