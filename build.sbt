 lazy val root = (project in file("."))
   .configs(IntegrationTest extend Test)
   .settings(
     name := """coupons-meli""",
     organization := "com.meli",
     scalaVersion := "2.13.10",
     Compile / doc / scalacOptions += "-no-java-comments",
     IntegrationTest / javaSource := baseDirectory.value / "it",
     resolvers ++= Seq(
       "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
       "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
       Resolver.mavenLocal
     ),
     bashScriptExtraDefines ++= Seq(
       """addJava "-XX:OnOutOfMemoryError='kill -9 %p'""""
     ),
     isSnapshot := true,
     buildInfoKeys := Seq[BuildInfoKey](name, version),
     buildInfoPackage := "com.meli.build",
     buildInfoObject := "BuildInfo",
     buildInfoOptions += BuildInfoOption.ToJson,
     libraryDependencies ++= Seq(
       guice,
       filters,
       evolutions,
       javaJdbc,
       ws,
       "io.vavr"                         % "vavr-jackson"                    % "1.0.0-alpha-3",
       "io.vavr"                         % "vavr"                            % "1.0.0-alpha-4",
       "org.immutables"                  % "value"                           % "2.10.0",
       "org.jdbi"                        % "jdbi3-core"                      % "3.37.1",
       "org.jdbi"                        % "jdbi3-sqlobject"                 % "3.37.1",
       "org.postgresql"                  % "postgresql"                      % "42.5.4",
       "com.fasterxml.jackson.module"    %% "jackson-module-scala"           % "2.14.2" ,
       "com.fasterxml.jackson.module"    % "jackson-module-parameter-names"  % "2.14.2",
       "com.fasterxml.jackson.datatype"  % "jackson-datatype-jsr310"         % "2.14.2",
       "org.mockito"                     % "mockito-core"                    % "5.1.1"    % "test",
     )
 )
 .enablePlugins(PlayJava, JavaAgent, BuildInfoPlugin)
 ThisBuild / evictionErrorLevel := Level.Info

 addCommandAlias("review", ";clean; compile; test; it:test")

 PlayKeys.devSettings := Seq("play.server.http.port" -> "9000")

 Universal / javaOptions ++= Seq(
   "-Dpidfile.path=/dev/null"
 )