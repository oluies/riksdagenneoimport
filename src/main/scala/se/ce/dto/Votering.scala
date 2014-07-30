package se.ce.dto

import java.util.Date

import se.ce.dto.Rost.Rost
import se.ce.dto.VoteringTyp._


/**
 * Created by orjan on 14-07-29(31).
 */

case class Votering(
            rm: String,
            beteckning: String,
            hangar_id: HangarGuid,
            votering_id:VoteringID,
            punkt: String,
            intressent_id: IntressentID,
            rost: Rost,
            avser: String,
            votering: VoteringTyp = VoteringTyp.huvud,
            banknummer: Int,
            datum: Date
                )


case class DokVotering(votering: List[Votering])


