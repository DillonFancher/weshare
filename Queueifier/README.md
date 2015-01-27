Queueifier
==========

###App Conntract
Application should accept pictures with the format:

```json
{
  geo: {
    lat: DOUBLE,
    LON: DOUBLE
  },
  timestamp: UNIX_TIMESTAMP_MILLIS,
  user_id: "USER_ID",
  auth_token: "AUTH_TOKEN",
  picture: binary_picture_data_here
}
```

Application should accept phone geo information in the following format:
```json
{
  user_id: "USER_ID",
  geo: {
    lat: DOUBLE,
    long: DOUBLE
  }
  timestamp: UNIX_TIMESTAMP_MILLIS,
  auth_token: "AUTH_TOKEN"
}
```

The app should produce to a picture Kafka topic with the following format:
```json
{
  geo: {
    lat: DOUBLE,
    lon: DOUBLE
  },
  session_token: "SESSION_TOKEN",
  picture_id: "UUID",
  activity_type: "photo",
  image_url_to_S3: "URL_TO_S3"
}
```

The app should produce to a tracking Kafka topic with the following format:
```json
{
  geo: {
    lat: DOUBLE,
    lon: DOUBLE
  },
  session_token: "SESSION_TOKEN",
  activity_type: "tracking"  
}
```

Things to think about:
 - How do we create sessions
 - How do we edit sessions
 - How many sessions can you be a part of?
 - How are sessions persisted? And from which app?
