import java.util

name := "base_clean"

version := "1.0"

lazy val `base_clean` = (project in file(".")).enablePlugins(PlayScala)
  .aggregate(configuration, core, dataproviders, entrypoints)
  .dependsOn(configuration)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
scalaVersion := "2.12.2"
libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

lazy val commonSettings = Seq(
  scalaVersion := "2.12.2",
  scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
  scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
  resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
  resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources",
  unmanagedBase := baseDirectory.value / "lib",
  unmanagedJars in Compile := (baseDirectory.value ** "*.jar").classpath,
  libraryDependencies ++= Seq(
    specs2 % Test,
    "ch.qos.logback" % "logback-classic" % "1.1.+",
    "com.github.maricn" % "logback-slack-appender" % "1.3.0",
    "org.apache.velocity" % "velocity" % "1.7",
    "com.github.tototoshi" %% "scala-csv" % "1.3.5"
  )
)

lazy val dataProvidersSetting = Seq(
  libraryDependencies ++= Seq(
    "org.skinny-framework" %% "skinny-orm" % "3.0.0",
    "org.flywaydb" %% "flyway-play" % "5.3.2",
    "mysql" % "mysql-connector-java" % "5.1.44",
    "org.scalikejdbc" %% "scalikejdbc" % "3.0.2",
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.0.2",
    "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.0"
  )
)


lazy val entryPointsSetting = Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % "2.6.10"
  )
)

lazy val coreSetting = Seq(
  libraryDependencies ++= Seq(
    "org.skinny-framework" %% "skinny-orm" % "2.3.7",
    "org.flywaydb" %% "flyway-play" % "4.0.0",
    "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.2",
    "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.0-scalikejdbc-3.3",
    "org.apache.commons" % "commons-csv" % "1.4"
  )
)

lazy val utilSetting = Seq(
  libraryDependencies ++= Seq(
  )
)

lazy val configuration =
  (project in file("application/configuration"))
    .enablePlugins(PlayScala)
    .dependsOn(
      dataproviders,
      entrypoints,
      core,
      util
    )
    .settings(commonSettings)
    .settings(libraryDependencies ++= Seq(guice))
    .settings(
      routesGenerator := InjectedRoutesGenerator
    )

lazy val core =
  Project(id = "core", base = file("application/core"))
    .enablePlugins(PlayScala)
    .settings(commonSettings)
    .settings(coreSetting)

lazy val dataproviders =
  Project(id = "dataproviders", base = file("application/dataproviders"))
    .settings(commonSettings)
    .settings(dataProvidersSetting)
    .dependsOn(core, util)

lazy val entrypoints =
  Project(id = "entrypoints", base = file("application/entrypoints"))
    .enablePlugins(PlayScala)
    .dependsOn(core, util)
    .settings(commonSettings)
    .settings(entryPointsSetting)

lazy val util =
  (project in file("utils"))
    .enablePlugins(PlayScala)
    .settings(commonSettings)
    .settings(utilSetting)
    .settings(libraryDependencies ++= Seq(guice))
    .settings(
      routesGenerator := InjectedRoutesGenerator
    )

//flywayLocations := Seq("filesystem:port/secondary/database/src/main/resources/db/migration/db.migration.default")