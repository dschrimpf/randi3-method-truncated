package org.org.randi3.dao

import org.apache.commons.math3.random.MersenneTwister

import org.junit.runner.RunWith
import scala.slick.lifted.Query
import scala.slick.session.Database.threadLocalSession

import org.scalatest.matchers.{MustMatchers, ShouldMatchers}
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import scala.Left
import org.randi3.randomization.TruncatedRandomization
import scala.Right
import scala.Some
import org.randi3.model.Trial


@RunWith(classOf[JUnitRunner])
class TruncatedRandomizationDaoSpec extends FunSpec with MustMatchers {

  import org.randi3.utility.TestingEnvironmentTruncated._

  import driver.Implicit._

  import schema._

  describe("TruncatedRandomizationDao create method") {

    it("should be able to create a new truncated randomization method") {
    database withSession {
      val randomizationMethodsBefore =  Query(RandomizationMethods).list
      val countRandomizationeMethodsBefore = randomizationMethodsBefore.size
      
      val truncatedRandomization: TruncatedRandomization = new TruncatedRandomization()(random = new MersenneTwister)
      val trialDB: Trial = trialDao.get(trialDao.create(createTrial.copy(randomizationMethod = None)).toOption.get).toOption.get.get

      val id = truncatedRandomizationDao.create(truncatedRandomization, trialDB.id).toEither match {
        case Left(x) => fail(x)
        case Right(id) => id
      }

 
        val allRandomizationMethods = Query(RandomizationMethods).list
        allRandomizationMethods.size must be(countRandomizationeMethodsBefore + 1)
        val newRandomizationMethod = allRandomizationMethods.filter(method => !randomizationMethodsBefore.map(_._1).contains(method._1) )
        newRandomizationMethod.size must be(1)
        newRandomizationMethod.head._4 must be(classOf[TruncatedRandomization].getName)
      }
    }


  }
}
