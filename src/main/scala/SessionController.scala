import scala.math

object SessionController {
   
  def main(args: Array[String]) {
    println("We are going to make this happen!")
    val start: Boundary = Boundary(args(0).toDouble, args(1).toDouble)
    val inBoundary: (Boolean, Double) = start.withinBoundary(args(2).toDouble, args(3).toDouble)
    if(inBoundary._1) println("You should get a picture, because you are "+ inBoundary._2+ "away from LIFE!")
    else println("You are no longer in the session, because you are " + inBoundary._2+ " miles away from DEATH:!")
  }

  def geoLocator(geoCoordinates: (Double, Double)): Boolean = {
    ???
  }

  case class Boundary(initialGeoCoord: (Double, Double)) {    val lat1 = math.toRadians(initialGeoCoord._1)
    val lon1 = math.toRadians(initialGeoCoord._2)
    
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
  }
}
