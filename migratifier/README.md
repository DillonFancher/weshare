Data Base Tables
================

Users Table
--------------
Id | UUID | UserName  | AdventureStatus | Lattitude| Longitude
---| --------- | --------------  | -------- | ---------
1  | 123456789 |Dillon | 0 | 40.027400 | 105.251900
2  | 134567892 | Nick   | 0 | 40.027400 | 105.251901
3  | 145678923 | Katie  | 0 | 40.027400 | 105.251902
----------------------
Friends Table
-------------------
This table is a map of  user_uuid : friend_user_uuid

(hint: Dillon, Nick, and Katie are all friends in this example)  

Id | 123456789  | 134567892 | 145678923 |
---| ------------ | ---------- | ---------
1  | 134567892  | 123456789 | 123456789
2  | 145678923  | 145678923 | 13456789
----------------------
Adventure Table
-----------------------
Id | Adventure Token  | Adventure Leader |
---| ---------------- | ---------------  |
1  | ASDKJWq3948SD45  | 123456789
----------------------  
Adventure Participants Table
-------------------------
This table is a map of adventure_token : user_uuid

Id | ASDKJWq3948SD45
---| ------------  
2  | 134567892
3  | 145678923  
----------------------------
Adventure Pictures Table
-------------------------
This table is a map of adventure_token : picure_url

Id | ASDKJWq3948SD45 |
---| ------------ |
1  | http://aws.s3.picture.download/?picture_id=2345094832  
2  | http://aws.s3.picture.download/?picture_id=234509483
3  | http://aws.s3.picture.download/?picture_id=234509483  
