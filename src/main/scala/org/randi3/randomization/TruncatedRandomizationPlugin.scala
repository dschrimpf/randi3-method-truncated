package org.randi3.randomization

import org.randi3.randomization.configuration._
import org.randi3.dao.TruncatedRandomizationDao
import org.randi3.model._
import org.randi3.model.criterion.Criterion
import org.randi3.model.criterion.constraint.Constraint
import scala.slick.driver.ExtendedProfile
import scala.slick.session.Database
import scalaz._

import org.apache.commons.math3.random._
import org.randi3.utility.{I18NHelper, I18NRandomization, AbstractSecurityUtil}
import scala.slick.lifted.DDL


class TruncatedRandomizationPlugin(database: Database, driver: ExtendedProfile, securityUtil: AbstractSecurityUtil) extends RandomizationMethodPlugin(database, driver, securityUtil) {

  private val i18n = new I18NRandomization(I18NHelper.getLocalizationMap("truncatedRandomizationM", getClass.getClassLoader), securityUtil)

  val name = classOf[TruncatedRandomization].getName

  def i18nName = i18n.text("name")

  def description = i18n.text("description")

  val canBeUsedWithStratification = false

  private val TruncatedRandomizationDao = new TruncatedRandomizationDao(database, driver)

  def randomizationConfigurationOptions(): (List[ConfigurationType[Any]], Map[String, List[Criterion[_ <: Any, Constraint[_ <: Any]]]])= {
    (Nil, Map())
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
