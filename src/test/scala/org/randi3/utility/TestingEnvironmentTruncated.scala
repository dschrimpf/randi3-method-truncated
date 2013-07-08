package org.randi3.utility

import scala.slick.driver.ExtendedProfile
import scala.slick.session.Database
import org.apache.commons.math3.random.MersenneTwister
import org.randi3.model._
import scala.collection.mutable.ListBuffer
import java.util.{Locale, Date}
import org.randi3.randomization.RandomizationPluginManagerComponent
import org.randi3.dao._
import org.joda.time.LocalDate
import org.randi3.service.{TrialSiteServiceComponent, TrialServiceComponent, UserServiceComponent}
import org.randi3.configuration.{ConfigurationServiceComponent, ConfigurationValues, ConfigurationSchema, ConfigurationService}
import org.randi3.schema.{LiquibaseUtil, DatabaseSchema}
import DatabaseSchema._
import scala.slick.session.Database
import org.scalaquery.meta.MTable


object TestingEnvironmentTruncated extends TestingEnvironment {

  lazy val truncatedRandomizationDao = new TruncatedRandomizationDao(database, driver)


}