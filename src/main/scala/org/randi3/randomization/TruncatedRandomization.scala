package org.randi3.randomization

import org.randi3.model.{TrialSubject, TreatmentArm, Trial}
import collection.mutable.{HashMap, ListBuffer}
import org.apache.commons.math3.random.{MersenneTwister, RandomGenerator}


case class TruncatedRandomization(id: Int = Int.MinValue, version:Int = 0)(val random: RandomGenerator) extends RandomizationMethod {



  def randomize(trial: Trial, subject: TrialSubject): TreatmentArm = {

    val possibleArms = trial.treatmentArms.filter(arm => arm.subjects.size < arm.plannedSize)

    if(possibleArms.isEmpty){
      null
    }else{
      val arms = generateRawBlock(possibleArms)

      arms(random.nextInt(arms.size))
    }

  }

  def generateRawBlock(treatmentArms: List[TreatmentArm]): List[TreatmentArm] = {
    val tmpBlock = new ListBuffer[TreatmentArm]

    val sizes = treatmentArms.map(arm => arm.plannedSize)

    var divide = sizes(0)

    for (plannedSize <- sizes) {
      divide = gcd(divide, plannedSize)
    }

    for (arm <- treatmentArms) {
      val size = arm.plannedSize / divide
      for (i <- 0 until size) tmpBlock.append(arm)
    }
    tmpBlock.toList
  }

  def gcd(x: Int, y: Int): Int = {
    if (x == 0) y
    else if (x < 0) gcd(-x, y)
    else if (y < 0) -gcd(x, -y)
    else gcd(y % x, x)
  }

}
