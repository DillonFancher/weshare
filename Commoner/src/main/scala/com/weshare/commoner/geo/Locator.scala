package com.weshare.commoner.geo

object Locator {

  def withinBoundary(geoCoord: (Double, Double)): (Boolean, Double) = {
    val lat2 = math.toRadians(geoCoord._1)
    val lon2 = math.toRadians(geoCoord._2)
    val dlon = math.abs(lat1-lat2)
    val dlat = math.abs(lon1-lon2)

    val a = (math.pow(math.sin(dlat/2),2)) +
    (math.cos(lat1)*math.cos(lat2)*math.pow(math.sin(dlon/2),2))
    val c = 2*math.atan2(math.sqrt(a), math.sqrt(1-a))
    val distance = 3961*c

    val inB: Boolean = distance < 20
    (inB, distance)
  }

  def

}
