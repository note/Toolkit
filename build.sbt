import sbtbuildinfo.BuildInfoPlugin.autoImport._

inThisBuild(
  Seq(
    organization := "de.proteinevolution",
    scalaVersion := "2.12.8"
  )
)

lazy val commonSettings = Seq(
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  TwirlKeys.templateImports := Nil
)

lazy val buildInfoSettings = Seq(
  buildInfoKeys := Seq[BuildInfoKey](
    name,
    version,
    scalaVersion,
    "scalaJSVersion" -> scalaJSVersion,
    sbtVersion,
    "playVersion" -> play.core.PlayVersion.current
  ),
  buildInfoPackage := "build"
)

lazy val coreSettings = commonSettings ++ Settings.compileSettings ++ Release.settings

lazy val disableDocs = Seq[Setting[_]](
  sources in (Compile, doc) := Seq.empty,
  publishArtifact in (Compile, packageDoc) := false
)

import sbtcrossproject.{ crossProject, CrossType }

lazy val common = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file("modules/common"))
  .settings(
    name := "common",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    disableDocs
  )
  .settings(addCompilerPlugin(("org.scalamacros" % "paradise" % "2.1.0").cross(CrossVersion.full)))

lazy val commonJS  = common.js.dependsOn(base, tel)
lazy val commonJVM = common.jvm.dependsOn(base, tel)

lazy val results = (project in file("modules/results"))
  .enablePlugins(PlayScala, JavaAppPackaging, SbtTwirl)
  .dependsOn(commonJVM, auth, jobs, help, ui, base, tools, util)
  .settings(
    name := "de.proteinevolution.results",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .settings(addCompilerPlugin(("org.scalamacros" % "paradise" % "2.1.0").cross(CrossVersion.full)))
  .settings(addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9"))
  .disablePlugins(PlayLayoutPlugin)

lazy val help = (project in file("modules/help"))
  .enablePlugins(PlayScala, JavaAppPackaging, SbtTwirl)
  .dependsOn(commonJVM, base)
  .settings(
    name := "de.proteinevolution.help",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val jobs = (project in file("modules/jobs"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, auth, base, clusterApi, tel, tools, util)
  .settings(
    name := "de.proteinevolution.jobs",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .settings(addCompilerPlugin(("org.scalamacros" % "paradise" % "2.1.0").cross(CrossVersion.full)))
  .disablePlugins(PlayLayoutPlugin)

lazy val auth = (project in file("modules/auth"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base, tel, util)
  .settings(
    name := "de.proteinevolution.auth",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val base = (project in file("modules/base"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .settings(
    name := "de.proteinevolution.base",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val cluster = (project in file("modules/cluster"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base, jobs, clusterApi)
  .settings(
    name := "de.proteinevolution.cluster",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val clusterApi = (project in file("modules/cluster-api"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base)
  .settings(
    name := "de.proteinevolution.cluster.api",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val backend = (project in file("modules/backend"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base, auth, jobs, tel, tools)
  .settings(
    name := "de.proteinevolution.backend",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val search = (project in file("modules/search"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base, auth, jobs, tools)
  .settings(
    name := "de.proteinevolution.search",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val ui = (project in file("modules/ui"))
  .enablePlugins(PlayScala, JavaAppPackaging, SbtTwirl, BuildInfoPlugin)
  .dependsOn(commonJVM, base, tools)
  .settings(
    name := "de.proteinevolution.ui",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs,
    buildInfoSettings
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val message = (project in file("modules/message"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base, auth, cluster, jobs, tools)
  .settings(
    name := "de.proteinevolution.message",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val verification = (project in file("modules/verification"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, base, auth, ui, message, tel, tools)
  .settings(
    name := "de.proteinevolution.verification",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val migrations = (project in file("modules/migrations"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .settings(
    name := "de.proteinevolution.migrations",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .settings(scalacOptions --= Seq("-Ywarn-unused:imports"))
  .disablePlugins(PlayLayoutPlugin)

lazy val tel = (project in file("modules/tel"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .settings(
    name := "de.proteinevolution.tel",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val tools = (project in file("modules/tools"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM, params)
  .settings(addCompilerPlugin(("org.scalamacros" % "paradise" % "2.1.0").cross(CrossVersion.full)))
  .settings(
    name := "de.proteinevolution.tools",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val util = (project in file("modules/util"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM)
  .settings(
    name := "de.proteinevolution.util",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .disablePlugins(PlayLayoutPlugin)

lazy val params = (project in file("modules/params"))
  .enablePlugins(PlayScala, JavaAppPackaging)
  .dependsOn(commonJVM)
  .settings(
    name := "de.proteinevolution.params",
    libraryDependencies ++= Dependencies.commonDeps,
    Settings.compileSettings,
    TwirlKeys.templateImports := Seq.empty,
    disableDocs
  )
  .settings(addCompilerPlugin(("org.scalamacros" % "paradise" % "2.1.0").cross(CrossVersion.full)))
  .disablePlugins(PlayLayoutPlugin)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, PlayAkkaHttp2Support, JavaAppPackaging, SbtWeb)
  .dependsOn(
    client,
    commonJVM,
    results,
    jobs,
    auth,
    base,
    cluster,
    help,
    backend,
    search,
    ui,
    message,
    verification,
    migrations,
    tel,
    tools,
    util
  )
  .aggregate(
    client,
    commonJVM,
    results,
    jobs,
    auth,
    base,
    cluster,
    help,
    backend,
    search,
    ui,
    message,
    verification,
    clusterApi,
    migrations,
    tel,
    tools,
    util,
    params
  )
  .settings(
    coreSettings,
    name := "mpi-toolkit",
    libraryDependencies ++= (Dependencies.commonDeps ++ Dependencies.testDeps ++ Dependencies.frontendDeps),
    pipelineStages := Seq(digest, gzip),
    compile in Compile := (compile in Compile).dependsOn(scalaJSPipeline).value,
    // TODO: TypescriptKeys.configFile := "/app/app/assets/tsconfig.json"
    // https://github.com/ArpNetworking/sbt-typescript#tsconfigjson-support-version--030
  )

resolvers += "scalaz-bintray".at("http://dl.bintray.com/scalaz/releases")
resolvers ++= Resolver.sonatypeRepo("releases") :: Resolver.sonatypeRepo("snapshots") :: Nil

lazy val client = (project in file("modules/client"))
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(commonJS)
  .settings(
    Settings.sjsCompileSettings,
    scalaJSUseMainModuleInitializer := true,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    scalaJSUseMainModuleInitializer in Test := false,
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv,
    libraryDependencies ++= Dependencies.clientDeps.value
  )

fork := true // required for "sbt run" to pick up javaOptions
javaOptions += "-Dplay.editor=http://localhost:63342/api/file/?file=%s&line=%s"
fork in Test := true
logLevel in Test := Level.Info

scalacOptions in Test ++= Seq("-Yrangepos")
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

PlayKeys.devSettings := Seq("play.server.http.idleTimeout" -> "220s")
