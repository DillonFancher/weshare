package com.weshare.commoner.geo

object CircleBoundary {
  def apply(lat: Double, lon: Double, distanceInMeters: Double): CircleBoundary = {
    val center = new LatLon(math.toRadians(lat), math.toRadians(lon))
    new CircleBoundary(center, distanceInMeters)
  }
}

case class CircleBoundary(center: LatLon, radiusInMeters: Double) {

  def pointIsInBoundary(point: LatLon): Boolean = Locator.withinCircleBoundary(this, center)._1

}
