package org.randi3.randomization

import org.randi3.randomization.configuration._
import org.randi3.dao.TruncatedRandomizationDao
import org.randi3.model._
import org.randi3.model.criterion.Criterion
import org.randi3.model.criterion.constraint.Constraint
import org.scalaquery.ql._
import org.scalaquery.ql.extended.ExtendedProfile
import org.scalaquery.session.Database
import scalaz._

import org.apache.commons.math3.random._


class TruncatedRandomizationPlugin(database: Database, driver: ExtendedProfile) extends RandomizationMethodPlugin(database, driver) {


  val name = classOf[TruncatedRandomization].getName

  val i18nName = name

  val description = "Truncated randomization method (ignores stratification options)"

  val canBeUsedWithStratification = false

  private val TruncatedRandomizationDao = new TruncatedRandomizationDao(database, driver)

  def randomizationConfigurationOptions(): (List[ConfigurationType[Any]], List[Criterion[_ <: Any, Constraint[_ <: Any]]])= {
    (Nil, Nil)
  }

  def getRandomizationConfigurations(id: Int): List[ConfigurationProperty[Any]] = {
    Nil
  }

  def randomizationMethod(random: RandomGenerator, trial: Trial, configuration: List[ConfigurationProperty[Any]]): Validation[String, RandomizationMethod] = {
    Success(new TruncatedRandomization()(random = random))
  }

  def databaseTables(): Option[DDL] = {
    None
  }

  def updateDatabase() {
    //Nothing to do
  }

  def create(randomizationMethod: RandomizationMethod, trialId: Int): Validation[String, Int] = {
    TruncatedRandomizationDao.create(randomizationMethod.asInstanceOf[TruncatedRandomization], trialId)
  }

  def get(id: Int): Validation[String, Option[RandomizationMethod]] = {
    TruncatedRandomizationDao.get(id)
  }

  def getFromTrialId(trialId: Int):  Validation[String, Option[RandomizationMethod]] = {
    TruncatedRandomizationDao.getFromTrialId(trialId)
  }

  def update(randomizationMethod: RandomizationMethod): Validation[String, RandomizationMethod] = {
    TruncatedRandomizationDao.update(randomizationMethod.asInstanceOf[TruncatedRandomization])
  }

  def delete(randomizationMethod: RandomizationMethod) {
    TruncatedRandomizationDao.delete(randomizationMethod.asInstanceOf[TruncatedRandomization])
  }


}
