package com.sncf.fab.myfirstproject.parser
/* créé parSamir Gafsi
  Pour la validation des données en amont - Avant de passer au parsing
   et transformation des données s'il ya lieu*/


/* ce code ne couvre pas tous les sous-domaines */

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.joda.time.{DateTime, Interval, LocalDate}
import scalaz.Scalaz._
import scalaz.ValidationNel
import com.sncf.fab.myfirstproject.sparkz.utils.Pimps._



case class TgaTgdEvent(gare:String, maj:Long , train:String,
                       ordes:String, num:String, `type`:String,
                       picto:String, attribut_voie:String, voie:String,
                       heure:Long, etat:String, retard:String)



sealed trait InvalidEventCause
case object InvalidGare extends InvalidEventCause
case object InvalidMaj extends InvalidEventCause
case object InvalidTrain extends InvalidEventCause
case object InvalidOrDes extends InvalidEventCause
case object InvalidNum extends InvalidEventCause
case object InvalidType extends InvalidEventCause
case object InvalidPicto extends InvalidEventCause
case object InvalidAttribut_Voie extends InvalidEventCause
case object InvalidVoie extends InvalidEventCause
case object InvalidHeure extends InvalidEventCause
case object InvalidEtat extends InvalidEventCause
case object InvalidRetard extends InvalidEventCause

case class InvalidEvent(event: TgaTgdEvent, cause: InvalidEventCause)

case object DataValidationTgaTgd {
 / * La fonction de validation */
  def validationFunction(events: RDD[TgaTgdEvent],
                         eligibleGares: Set[String], /* ces gares sont définies dans le référentiel */
                         eligibleMajs: Set[Long],
                         eligibleTrains: Set[String] /* les trains sont définis dans le référentiel*/
                        /* rajouter toutes les fonctions de validation qu'on souhaite exécuter*/
                        ): TgaTgdEvent => ValidationNel[InvalidEvent, TgaTgdEvent] = {

    val sc = events.context

                      /* on utilise la variable broadCast */
    val eligibleMajsBV: Broadcast[Set[Long]] = sc.broadcast(eligibleMajs)

                            /* fonction qui retourne un InvalidMaj */
    val nonreconnuMaj: PartialFunction[TgaTgdEvent, InvalidEventCause] = {
      case event if !eligibleMajsBV.value.contains(event.maj) => InvalidMaj
    }

    val eligibleGaresBV: Broadcast[Set[String]] = sc.broadcast(eligibleGares)

    val noneligibleGare: PartialFunction[TgaTgdEvent, InvalidEventCause] = {
      case event if !eligibleGaresBV.value.contains(event.gare) => InvalidGare
    }

    val eligibleTrainsBV: Broadcast[Set[Int]] = sc.broadcast(eligibleTrains)

    val noneligibleTrains: Broadcast[Set[String]] = sc.broadcast(
      events.filter(event => eligibleTrainsBV.value.contains(event.train))
        .map(_.train).distinct().collect().toSet
    )


/* les règles de validation basées sur les fonctions partielles définies pour chaque sous-domaines TGATGD*/
    val validationRules: List[PartialFunction[TgaTgdEvent, InvalidEventCause]] =
      List(nonreconnuMaj, noneligibleGare, noneligibleTrains
       )


    (event: TgaTgdEvent) => validationRules.map(_.toFailureNel(event, InvalidEvent(event, _))).reduce(_ |+++| _)
  }

  /*fonction qui retourne seulement les evenements valides*/

  def evenementsValides(events: RDD[TgaTgdEvent],
                      validationFunc: TgaTgdEvent => ValidationNel[InvalidEvent, TgaTgdEvent]): RDD[TgaTgdEvent] =
    events.map(validationFunc).flatMap(_.toOption)

  /*fonction qui retourne seulement les evenements valides*/

  def evenementsInvalides(events: RDD[TgaTgdEvent],
                    validationFunc: TgaTgdEvent => ValidationNel[InvalidEvent, TgaTgdEvent]): RDD[InvalidEvent] =
    events.map(validationFunc).flatMap(_.swap.toOption).flatMap(_.toList)






}


