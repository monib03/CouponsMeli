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
     buildInfoPackage := "com.mymo.build",
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
       "org.antlr"                       % "ST4"                             % "4.3.4",
       "org.postgresql"                  % "postgresql"                      % "42.5.4",
       "org.apache.commons"              % "commons-lang3"                   % "3.12.0",
       "com.fasterxml.jackson.module"    %% "jackson-module-scala"           % "2.14.2" ,
       "com.fasterxml.jackson.module"    % "jackson-module-parameter-names"  % "2.14.2",
       "com.fasterxml.jackson.datatype"  % "jackson-datatype-jsr310"         % "2.14.2",
       "org.mockito"                     % "mockito-core"                    % "5.1.1"    % "test",
       "commons-io"                      % "commons-io"                      % "2.11.0",
       "io.reactivex.rxjava2"            % "rxjava"                          % "2.2.21",
     )
 )
 .enablePlugins(PlayJava, JavaAgent, BuildInfoPlugin)
 ThisBuild / evictionErrorLevel := Level.Info

 addCommandAlias("review", ";clean; compile; test; it:test")

 PlayKeys.devSettings := Seq("play.server.http.port" -> "9000")

 Universal / javaOptions ++= Seq(
   "-Dpidfile.path=/dev/null"
 )