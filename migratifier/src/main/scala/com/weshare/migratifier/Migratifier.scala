package com.weshare.migratifier

object Migratifier extends App {

  override def main (args: Array[String]): Unit = {
    // Create the Flyway instance
    val flyway = new Flyway

    // Point it to the database
    flyway.setDataSource("jdbc:h2:file:target/foobar", "sa", null)

    // Start the migration
    flyway.migrate()
  }

}