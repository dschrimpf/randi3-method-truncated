package org.randi3.dao


import org.randi3.schema.DatabaseSchema._

import scala.slick.session.Database
import Database.threadLocalSession
import org.randi3.randomization.TruncatedRandomization
import scala.slick.driver.ExtendedProfile
import scalaz._
import scalaz.Scalaz._
import org.randi3.schema.DatabaseSchema


class TruncatedRandomizationDao(database: Database, driver: ExtendedProfile) extends AbstractRandomizationMethodDao(database, driver) {


  import driver.Implicit._

  val schemaCore = new DatabaseSchema(driver)
  import schemaCore._


  def create(randomizationMethod: TruncatedRandomization, trialId: Int): Validation[String, Int] = {
    database withSession {
      threadLocalSession withTransaction {
        val seed = randomizationMethod.random.nextLong()
        randomizationMethod.random.setSeed(seed)
        RandomizationMethods.noId insert (trialId, generateBlob(randomizationMethod.random).get, randomizationMethod.getClass().getName(), seed)
      }
      getId(trialId)
    }

  }

  def get(id: Int): Validation[String, Option[TruncatedRandomization]] = {
    database withSession {
      val resultList = queryRandomizationMethodFromId(id).list
      if (resultList.isEmpty) Success(None)
      else if (resultList.size == 1) {
        val rm = resultList(0)
        if (rm._4 == classOf[TruncatedRandomization].getName()) {
          Success(Some(new TruncatedRandomization(rm._1.get, 0)(deserializeRandomGenerator(rm._3))))
        } else {
          Failure("Wrong plugin")
        }

      } else Failure("More than one method with id=" + id + " found")
    }
  }

  def getFromTrialId(trialId: Int): Validation[String, Option[TruncatedRandomization]] = {
    database withSession {
      val resultList = queryRandomizationMethodFromTrialId(trialId).list
      if (resultList.isEmpty) Success(None)
      else if (resultList.size == 1) {
        val rm = resultList(0)
        if (rm._4 == classOf[TruncatedRandomization].getName()) {
          Success(Some(new TruncatedRandomization(rm._1.get, 0)(deserializeRandomGenerator(rm._3))))
        } else {
          Failure("Wrong plugin")
        }
      } else Failure("More than one method for trial with id=" + trialId + " found")
    }
  }

  def update(randomizationMethod: TruncatedRandomization): Validation[String, TruncatedRandomization] = {
    database withSession {
      queryRandomizationMethodFromId(randomizationMethod.id).mutate { r =>
        r.row = r.row.copy(_3 = generateBlob(randomizationMethod.random).get, _4 = randomizationMethod.getClass().getName())
      }
    }

    get(randomizationMethod.id).toEither match {
      case Left(x) => Failure(x)
      case Right(None) => Failure("Method not found")
      case Right(Some(randomizationMethod)) => Success(randomizationMethod)
    }
  }

  def delete(randomizationMethod: TruncatedRandomization) {
    database withSession {
      queryRandomizationMethodFromId(randomizationMethod.id).mutate { r =>
        r.delete()
      }
    }
  }

}
