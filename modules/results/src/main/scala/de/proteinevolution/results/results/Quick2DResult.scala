package de.proteinevolution.results.results

import de.proteinevolution.results.results.General.SingleSeq
import de.proteinevolution.results.results.Quick2DResult._
import io.circe.{ Decoder, HCursor }

case class Quick2DResult(
    jobID: String,
    query: SingleSeq,
    psipred: Psipred,
    pipred: Pipred,
    marcoil: Marcoil,
    coils: Coils,
    pcoils: Pcoils,
    tmhmm: Tmhmm,
    phobius: Phobius,
    polyphobius: Polyphobius,
    spider2: Spider2,
    spotd: Spotd,
    iupred: Iupred,
    disopred3: Disopred3,
    signal: Signal,
    psspred: Psspred,
    deepcnf: Deepcnf
)

object Quick2DResult {

  implicit val quick2dDecoder: Decoder[Quick2DResult] = (c: HCursor) => {
    for {
      jobId       <- c.downField("jobID").as[String]
      query       <- c.downField("query").as[SingleSeq]
      psipred     <- c.downField(jobId).as[Psipred]
      pipred      <- c.downField(jobId).as[Pipred]
      marcoil     <- c.downField(jobId).as[Marcoil]
      coils       <- c.downField(jobId).as[Coils]
      pcoils      <- c.downField(jobId).as[Pcoils]
      tmhmm       <- c.downField(jobId).as[Tmhmm]
      phobius     <- c.downField(jobId).as[Phobius]
      polyphobius <- c.downField(jobId).as[Polyphobius]
      spider2     <- c.downField(jobId).as[Spider2]
      spotd       <- c.downField(jobId).as[Spotd]
      iupred      <- c.downField(jobId).as[Iupred]
      disopred3   <- c.downField(jobId).as[Disopred3]
      signal      <- c.downField(jobId).as[Signal]
      psspred     <- c.downField(jobId).as[Psspred]
      deepcnf     <- c.downField(jobId).as[Deepcnf]
    } yield {
      new Quick2DResult(
        jobId,
        query,
        psipred,
        pipred,
        marcoil,
        coils,
        pcoils,
        tmhmm,
        phobius,
        polyphobius,
        spider2,
        spotd,
        iupred,
        disopred3,
        signal,
        psspred,
        deepcnf
      )
    }
  }

  case class Psipred(name: String, seq: String, conf: String)

  object Psipred {
    implicit val psipredDecoder: Decoder[Psipred] = (c: HCursor) =>
      for {
        conf <- c.downField("psipred_conf").as[Option[String]]
        seq  <- c.downField("psipred").as[Option[String]]
      } yield {
        new Psipred("psipred", seq.getOrElse(""), conf.getOrElse(""))
    }
  }

  case class Pipred(name: String, seq: String, conf: String)

  object Pipred {
    implicit val pipredDecoder: Decoder[Pipred] = (c: HCursor) =>
      for {
        conf <- c.downField("pipred_conf").as[Option[String]]
        seq  <- c.downField("pipred").as[Option[String]]
      } yield {
        new Pipred("pipred", seq.getOrElse(""), conf.getOrElse(""))
    }
  }

  case class Marcoil(name: String, seq: String)

  object Marcoil {
    implicit val marcoilDecoder: Decoder[Marcoil] = (c: HCursor) =>
      for {
        seq <- c.downField("marcoil").as[Option[String]]
      } yield {
        new Marcoil("marcoil", seq.getOrElse(""))
    }
  }

  case class Coils(name: String, seq: String)

  object Coils {
    implicit val coilsDecoder: Decoder[Coils] = (c: HCursor) =>
      for {
        seq <- c.downField("coils_w28").as[Option[String]]
      } yield {
        new Coils("coils", seq.getOrElse(""))
    }
  }

  case class Pcoils(name: String, seq: String)

  object Pcoils {
    implicit val pcoilsDecoder: Decoder[Pcoils] = (c: HCursor) =>
      for {
        seq <- c.downField("pcoils_w28").as[Option[String]]
      } yield {
        new Pcoils("pcoils", seq.getOrElse(""))
    }
  }

  case class Tmhmm(name: String, seq: String)

  object Tmhmm {
    implicit val tmhmmDecoder: Decoder[Tmhmm] = (c: HCursor) =>
      for {
        seq <- c.downField("tmhmm").as[Option[String]]
      } yield {
        new Tmhmm("tmhmm", seq.getOrElse(""))
    }
  }

  case class Phobius(name: String, seq: String)

  object Phobius {
    implicit val phobiusDecoder: Decoder[Phobius] = (c: HCursor) =>
      for {
        seq <- c.downField("phobius").as[Option[String]]
      } yield {
        new Phobius("phobius", seq.getOrElse(""))
    }
  }

  case class Polyphobius(name: String, seq: String)

  object Polyphobius {
    implicit val decodePolyphobius: Decoder[Polyphobius] = (c: HCursor) =>
      for {
        seq <- c.downField("polyphobius").as[Option[String]]
      } yield {
        new Polyphobius("polyphobius", seq.getOrElse(""))
    }
  }

  case class Spider2(name: String, seq: String)

  object Spider2 {
    implicit val decodeSpider2: Decoder[Spider2] = (c: HCursor) =>
      for {
        seq <- c.downField("spider2").as[Option[String]]
      } yield {
        new Spider2("spider2", seq.getOrElse(""))
    }
  }

  case class Spotd(name: String, seq: String)

  object Spotd {
    implicit val spotdDecoder: Decoder[Spotd] = (c: HCursor) =>
      for {
        seq <- c.downField("spot-d").as[Option[String]]
      } yield {
        new Spotd("spotd", seq.getOrElse(""))
    }
  }

  case class Iupred(name: String, seq: String)

  object Iupred {
    implicit val iupredDecoder: Decoder[Iupred] = (c: HCursor) =>
      for {
        seq <- c.downField("iupred").as[Option[String]]
      } yield {
        new Iupred("iupred", seq.getOrElse(""))
    }
  }

  case class Disopred3(name: String, seq: String)

  object Disopred3 {
    implicit val dispored3Decoder: Decoder[Disopred3] = (c: HCursor) =>
      for {
        seq <- c.downField("disopred3").as[Option[String]]
      } yield {
        new Disopred3("disopred3", seq.getOrElse(""))
    }
  }

  case class Signal(name: String, seq: String)

  object Signal {
    implicit val signalDecoder: Decoder[Signal] = (c: HCursor) =>
      for {
        seq <- c.downField("signal").as[Option[String]]
      } yield {
        new Signal("signal", seq.getOrElse(""))
    }
  }

  case class Psspred(name: String, seq: String)

  object Psspred {
    implicit val psspredDecoder: Decoder[Psspred] = (c: HCursor) =>
      for {
        seq <- c.downField("psspred").as[Option[String]]
      } yield {
        new Psspred("psspred", seq.getOrElse(""))
    }
  }

  case class Deepcnf(name: String, seq: String)

  object Deepcnf {
    implicit val deepcnfDecoder: Decoder[Deepcnf] = (c: HCursor) =>
      for {
        seq <- c.downField("deepcnf").as[Option[String]]
      } yield {
        new Deepcnf("deepcnf", seq.getOrElse(""))
    }
  }

}
