package api.tool.repo

import java.io.File

import api.tool.resource.ToolResource
import javax.inject.Inject
import play.api.{Application, Environment}
import utils.IO

import scala.concurrent.Future
import scala.io.Source

final class SimpleRunscriptToolRepository @Inject()(environment: Environment) extends ToolRepository {

  private def runscriptToTool(file: File) = {

    // Get all the Labels from the runscript
    val labels = IO.autoClose(Source.fromFile(file))(bufferedSource =>

      bufferedSource.getLines()
        .withFilter(line => line.startsWith("#?") && line.contains("LABEL"))
        .map(line => {
          val spt = line.split("\\s+")
          (spt(2), spt(3))
        }).toMap
    )
    for (shortName <- labels.get("shortName"); longName <- labels.get("longName")) yield ToolResource(shortName, longName)
  }

  /*
   * Contains the Tools from the Runscripts as loaded from the runscript directory
   */
  private val tools: Array[ToolResource] = {

    // Get the runscript resource from the classpath
    val in = environment.resource("runscripts").getOrElse(
      throw new IllegalStateException("Attempt to load SimpleRunscriptToolRepository, but 'runscripts' directory is not in classpath"))

    // Process all the files in the directory
    new File(in.toURI).listFiles().flatMap(file => runscriptToTool(file))
  }

  /**
    * Lists all the tools that this repository has to offer
    */
  override def list(): Future[Iterable[ToolResource]] = Future.successful(this.tools)
}
