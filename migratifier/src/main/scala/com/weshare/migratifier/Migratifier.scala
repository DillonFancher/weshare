package com.weshare.migratifier

import java.net.URLClassLoader
import com.weshare.commoner.config.WellKnownConfigKeys
import java.sql.DriverManager
import java.util.Properties
import com.typesafe.config.Config
import org.flywaydb.core._
import org.slf4j.LoggerFactory

/**
 * Executable that runs migrations for (currently only one) database
 *
 * Migrations should be put in the following location within the app:
 *
 *  resources/migrations/DB_NAME/
 *
 */
object Migratifier extends App {

  override def main (args: Array[String]): Unit = {
    val config = com.weshare.commoner.config.config
    val flyway = setFlywayConfig(config, new Flyway)
    flyway.migrate()
    log.info("!!!!!! Finished running migrations !!!!!!")
  }
  
  def log = LoggerFactory.getLogger("Migratifier")

  /**
   * Method that configures a Flyway object for a Typesafe config
   */
  def setFlywayConfig(config: Config, flyway: Flyway, databaseName: String = Constants.HierDb): Flyway = {
    val properties = config2FlywayProperties(config)
    flyway.configure(properties)
    flyway.setLocations(migrationsPathForDb(databaseName))
    flyway
  }

  /**
   * Method that takes a Typesafe config object and returns a java.util.Properties
   * formatted for use by Flyway
   */
  def config2FlywayProperties(config: Config, databaseName: String = Constants.HierDb): Properties = {
    val properties = new Properties
    properties.setProperty("flyway.driver",    config.getString(WellKnownConfigKeys.hierDbDriver))
    properties.setProperty("flyway.url",       config.getString(WellKnownConfigKeys.hierDbURL))
    properties.setProperty("flyway.user",      config.getString(WellKnownConfigKeys.hierDbUser))
    properties.setProperty("flyway.password",  config.getString(WellKnownConfigKeys.hierDbPassword))
    properties
  }

  def createDatabase(name: String, host: String, username: String, password: String): Int = {
    val conn = DriverManager.getConnection(s"jdbc:mysql://$host/?user=$username&password=$password")
    val s = conn.createStatement
    s.executeUpdate("CREATE DATABASE "+ name)
  }

  def migrationsPathForDb(dbName: String) = s"migrations/$dbName"
  
  def dumpClasspath = ClassLoader.getSystemClassLoader
    .asInstanceOf[URLClassLoader]
    .getURLs()
    .toSeq.map(_.getFile)
  
}
