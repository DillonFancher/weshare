Migratifier
===========

###App Purpose
This app handles the creation and migration of our database(s)

###Adding a migration:
Migrations should be added in the ```src/main/resources/migrations/DB_NAME/``` folder, and written in MySql. Format for migrations is as follows:
  - Tables names must be plural, with no spaces, and capital letters denoting words i.e.
      - ```Users``` is correct
      - ```user``` is incorrect
      - ```users``` is incorrect
      - ```AdventuresTable``` is correct
      - ```Adventures table``` is incorrect
      - ```Adventures_Table``` is incorrect
      - ```adventuresTable``` is incorrect


  - Column names are lower case, singular, and snake cased i.e.
      - ```id``` is correct
      - ```ID``` is incorrect
      - ```iD``` is incorrect and silly
      - ```user_id``` is correct
      - ```userId``` is incorrect
      - ```user id``` is incorrect

  - Migration file names should follow the format ```VYEARMONTHDAY.VERSION__NAME.sql```,
  where YEAR is the full year, MONTH is the two digit month (01, 11, etc.) day is the
  two digit day (01, 23, 17, etc.) and the NAME is a CamelCase description i.e.
    - ```V20150201.00__CreateUsers.sql``` is correct
    - ```2015020100__CreateUsers.sql``` is incorrect
    - ```v2015020100.00__CreateUsers.sql``` is incorrect
    - ```v2015020100.00__create_users.sql``` is incorrect


Data Base Tables
================

Users Table
--------------
Id  | UserName  | AdventureStatus | Lattitude| Longitude
---| --------- | --------------  | -------- | ---------
1  | Dillon | 0 | 40.027400 | 105.251900
2  | Nick   | 0 | 40.027400 | 105.251901
3  | Katie  | 0 | 40.027400 | 105.251902
---------------------
User Location Table
--------------------
Id | User_Id | Lattitude| Longitude | TimeStamp
---| --------|--------- | --------- | ---------
1  | 1 | 40.027400 | 105.251900 | 39418674
2  | 2 | 40.027400 | 105.251901 | 23490874
3  | 2 | 40.027400 | 105.251902 | 23049578
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
