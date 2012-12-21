package org.randi3.randomization

import org.junit.runner.RunWith
import org.scalatest.matchers.MustMatchers
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import collection.mutable.ListBuffer
import org.randi3.model.{TrialSubject, TreatmentArm}
import org.apache.commons.math3.random.MersenneTwister

@RunWith(classOf[JUnitRunner])
class TruncatedRandomizationTest extends FunSpec with MustMatchers {

  import org.randi3.utility.TestingEnvironment._

  describe("A truncated randomization method") {

    it("should be balanced after the end of the trial (two arms)") {

      val testArms = List((20, 20),(50, 100),(50, 150), (33, 100))

      for(testCase <- testArms){


      val arms = new ListBuffer[TreatmentArm]()

      //create the arms
      arms.append(createTreatmentArm.copy(id = 1, plannedSize = testCase._1))
      arms.append(createTreatmentArm.copy(id = 2, plannedSize = testCase._2))

      val truncatedRandomizationMethod = new TruncatedRandomization()(random = new MersenneTwister())
      val trial = createTrial.copy(treatmentArms = arms.toList, randomizationMethod = Some(truncatedRandomizationMethod))

      for (i <- 1 to trial.plannedSubjects) {
        val subject = TrialSubject(identifier = "subject" + i, investigatorUserName = "investigator", trialSite = trial.participatingSites.head, properties = Nil).toOption.get
        trial.randomize(subject).isSuccess must be(true)
        trial.getSubjects.size must be(i)
        trial.treatmentArms.find(arm => arm.id == 1).get.subjects.size must be <=(testCase._1)
        trial.treatmentArms.find(arm => arm.id == 2).get.subjects.size must be <=(testCase._2)
      }

        trial.treatmentArms.find(arm => arm.id == 1).get.subjects.size must be(testCase._1)
        trial.treatmentArms.find(arm => arm.id == 2).get.subjects.size must be(testCase._2)
    }

    }


    it("should be balanced after the end of the trial (three arms)") {

      val testArms = List((20, 20, 20),(50, 100, 50),(50, 150, 100), (33, 100, 123))

      for(testCase <- testArms){


        val arms = new ListBuffer[TreatmentArm]()

        //create the arms
        arms.append(createTreatmentArm.copy(id = 1, plannedSize = testCase._1))
        arms.append(createTreatmentArm.copy(id = 2, plannedSize = testCase._2))
        arms.append(createTreatmentArm.copy(id = 3, plannedSize = testCase._3))

        val truncatedRandomizationMethod = new TruncatedRandomization()(random = new MersenneTwister())
        val trial = createTrial.copy(treatmentArms = arms.toList, randomizationMethod = Some(truncatedRandomizationMethod))

        for (i <- 1 to trial.plannedSubjects) {
          val subject = TrialSubject(identifier = "subject" + i, investigatorUserName = "investigator", trialSite = trial.participatingSites.head, properties = Nil).toOption.get
          trial.randomize(subject).isSuccess must be(true)
          trial.getSubjects.size must be(i)
          trial.treatmentArms.find(arm => arm.id == 1).get.subjects.size must be <=(testCase._1)
          trial.treatmentArms.find(arm => arm.id == 2).get.subjects.size must be <=(testCase._2)
          trial.treatmentArms.find(arm => arm.id == 3).get.subjects.size must be <=(testCase._3)
        }

        trial.treatmentArms.find(arm => arm.id == 1).get.subjects.size must be(testCase._1)
        trial.treatmentArms.find(arm => arm.id == 2).get.subjects.size must be(testCase._2)
        trial.treatmentArms.find(arm => arm.id == 3).get.subjects.size must be(testCase._3)
      }

    }

    it("should be balanced after the end of the trial (four arms)") {

      val testArms = List((20, 20, 20, 20),(50, 100, 50, 150),(50, 150, 100, 100), (33, 100, 123, 100))

      for(testCase <- testArms){


        val arms = new ListBuffer[TreatmentArm]()

        //create the arms
        arms.append(createTreatmentArm.copy(id = 1, plannedSize = testCase._1))
        arms.append(createTreatmentArm.copy(id = 2, plannedSize = testCase._2))
        arms.append(createTreatmentArm.copy(id = 3, plannedSize = testCase._3))
        arms.append(createTreatmentArm.copy(id = 4, plannedSize = testCase._4))

        val truncatedRandomizationMethod = new TruncatedRandomization()(random = new MersenneTwister())
        val trial = createTrial.copy(treatmentArms = arms.toList, randomizationMethod = Some(truncatedRandomizationMethod))

        for (i <- 1 to trial.plannedSubjects) {
          val subject = TrialSubject(identifier = "subject" + i, investigatorUserName = "investigator", trialSite = trial.participatingSites.head, properties = Nil).toOption.get
          trial.randomize(subject).isSuccess must be(true)
          trial.getSubjects.size must be(i)
          trial.treatmentArms.find(arm => arm.id == 1).get.subjects.size must be <=(testCase._1)
          trial.treatmentArms.find(arm => arm.id == 2).get.subjects.size must be <=(testCase._2)
          trial.treatmentArms.find(arm => arm.id == 3).get.subjects.size must be <=(testCase._3)
          trial.treatmentArms.find(arm => arm.id == 4).get.subjects.size must be <=(testCase._4)
        }

        trial.treatmentArms.find(arm => arm.id == 1).get.subjects.size must be(testCase._1)
        trial.treatmentArms.find(arm => arm.id == 2).get.subjects.size must be(testCase._2)
        trial.treatmentArms.find(arm => arm.id == 3).get.subjects.size must be(testCase._3)
        trial.treatmentArms.find(arm => arm.id == 4).get.subjects.size must be(testCase._4)
      }

    }


  }


}