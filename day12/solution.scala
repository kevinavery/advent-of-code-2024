object Main {

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.fromFile("input.txt").getLines
    val m = lines.toVector.map(_.trim.toVector)

    var seen = Set.empty[(Int, Int)]
    var totalA = 0
    var totalB = 0
    0.until(m.length).foreach { y =>
      0.until(m(y).length).foreach { x =>
        if (!seen.contains((x, y))) {
          val region = getRegion(m, x, y)
          val perimeter = getPerimeter(m, region)
          val sides = getSides(m, region)

          seen ++= region
          totalA += region.size * perimeter
          totalB += region.size * sides
        }
      }
    }

    println(totalA)
    println(totalB)
  }

  def getRegion(m: Vector[Vector[Char]], x: Int, y: Int): Set[(Int, Int)] = {
    val c = m(y)(x)
    var region = Set.empty[(Int, Int)]

    def rec(x: Int, y: Int): Unit = {
      region += (x, y)
      Set((0, 1), (1, 0), (0, -1), (-1, 0)).foreach { (dx, dy) =>
        val nx = x + dx 
        val ny = y + dy 
        if (ny >= 0 && ny < m.length && nx >= 0 && nx < m(ny).length && m(ny)(nx) == c && !region.contains((nx, ny))) {
          rec(nx, ny)
        }
      }
    }

    rec(x, y)

    region
  }

  def getPerimeter(m: Vector[Vector[Char]], region: Set[(Int, Int)]): Int = {    
    val (cx, cy) = region.head
    val c = m(cy)(cx)
    region.toList.flatMap { (x, y) =>
      List((0, 1), (1, 0), (0, -1), (-1, 0)).map { (dx, dy) =>
        val nx = x + dx 
        val ny = y + dy 

        if (ny < 0 || ny >= m.length || nx < 0 || nx >= m(ny).length || m(ny)(nx) != c) {
          1
        } else {
          0
        }
      }
    }.sum
  }

  def getSides(m: Vector[Vector[Char]], region: Set[(Int, Int)]): Int = {
    def segmentsX(s: List[(Int, Int)]): Int = {
      ((-1, -1) :: s).sliding(2).count {
        case List((ax, ay), (bx, by)) => ay + 1 != by || ax != bx
        case _ => false
      }
    }

    def leftsRights: Int = {
      val ordered = region.toList.sortBy(t => (t._1, t._2))
      val lefts = ordered.filter { (x, y) =>
        x == 0 || m(y)(x) != m(y)(x - 1)
      }
      val rights = ordered.filter { (x, y) =>
        x == m(y).length - 1 || m(y)(x) != m(y)(x + 1)
      }
      segmentsX(lefts) + segmentsX(rights)
    }

    def segmentsY(s: List[(Int, Int)]): Int = {
      ((-1, -1) :: s).sliding(2).count {
        case List((ax, ay), (bx, by)) => ax + 1 != bx || ay != by
        case _ => false
      }
    }

    def topsBottoms: Int = {
      val ordered = region.toList.sortBy(t => (t._2, t._1))
      val tops = ordered.filter { (x, y) =>
        y == 0 || m(y)(x) != m(y - 1)(x)
      }
      val bottoms = ordered.filter { (x, y) =>
        y == m.length - 1 || m(y)(x) != m(y + 1)(x)
      }
      segmentsY(tops) + segmentsY(bottoms)
    }

    leftsRights + topsBottoms
  }
}