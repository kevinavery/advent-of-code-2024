object Main {

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.fromFile("input.txt").getLines
    val m = lines.toVector.map(_.toVector.map(_.toString.toInt))

    var sumScore = 0
    var sumRating = 0
    0.until(m.size).foreach { y =>
      0.until(m(y).size).foreach { x =>
        if (m(y)(x) == 0) {
          sumScore += findDestinations(m, x, y).size
          sumRating += countTrails(m, x, y)
        }
      }
    }
    println(sumScore)
    println(sumRating)
  }

  def findDestinations(m: Vector[Vector[Int]], x: Int, y: Int): Set[(Int, Int)] = {
    if (m(y)(x) == 9) {
      Set((x, y))
    } else {
      Set((1, 0), (0, 1), (-1, 0), (0, -1)).filter { case (dx, dy) =>
        y + dy >= 0 && y + dy < m.size && x + dx >= 0 && x + dx < m(0).size && m(y + dy)(x + dx) == m(y)(x) + 1
      }.flatMap { case (dx, dy) =>
        findDestinations(m, x + dx, y + dy)
      }
    }
  }

  def countTrails(m: Vector[Vector[Int]], x: Int, y: Int): Int = {
    if (m(y)(x) == 9) {
      1
    } else {
      List((1, 0), (0, 1), (-1, 0), (0, -1)).filter { case (dx, dy) =>
        y + dy >= 0 && y + dy < m.size && x + dx >= 0 && x + dx < m(0).size && m(y + dy)(x + dx) == m(y)(x) + 1
      }.map { case (dx, dy) =>
        countTrails(m, x + dx, y + dy)
      }.sum
    }
  }
}
