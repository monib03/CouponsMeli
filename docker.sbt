dockerRepository := Some("336204769228.dkr.ecr.us-east-1.amazonaws.com")

dockerUsername := Some("coupon-staging")

dockerExposedPorts := Seq(9000)

dockerExposedVolumes := Seq("/opt/docker/logs")
