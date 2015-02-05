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
