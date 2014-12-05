import scala.math

object SessionController {
   
  def main(args: Array[String]) {
    println("We are going to make this happen!")
    val start: Boundary = Boundary(-90.05, 29.9667)
    val inBoundary: Boolean = start.withinBoundary(-90.485, 30.1750)
    if(inBoundary) println("You should get a picture!")
    else println("You are no longer in the session")
  }

  def geoLocator(geoCoordinates: (Double, Double)): Boolean = {
    ???
  }

  case class Boundary(initialGeoCoord: (Double, Double)) {    val lat1 = math.toRadians(initialGeoCoord._1)
    val lon1 = math.toRadians(initialGeoCoord._2)
    
    def withinBoundary(geoCoord: (Double, Double)): Boolean = {
      val lat2 = math.toRadians(geoCoord._1)
      val lon2 = math.toRadians(geoCoord._2)
      val dlon = math.abs(lat1-lat2)
      val dlat = math.abs(lon1-lon2)
      
      val a = (math.pow(math.sin(dlat/2),2)) + 
              (math.cos(lat1)*math.cos(lat2)*math.pow(math.sin(dlon/2),2))
      val c = 2*math.atan2(math.sqrt(a), math.sqrt(1-a))
      val distance = 3961*c

      if(distance < 20) true
      else false
    }
  }
}
