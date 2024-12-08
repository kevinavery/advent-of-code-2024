object Main {

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.fromFile("input.txt").getLines.toList

    var m = Map.empty[Char, List[(Int, Int)]]

    lines.zipWithIndex.foreach { (l, row) =>
      l.zipWithIndex.foreach { (c, col) =>
        if (c != '.') {
          m = m.updated(c, (col, row) :: m.getOrElse(c, Nil))
        }
      }
    }

    val yMax = lines.size - 1
    val xMax = lines.head.size - 1

    var nodes = Set.empty[(Int, Int)]

    m.foreach { case (c, positions) =>
      computePairs(positions).foreach { positionPair =>
        nodes ++= findNodesB(positionPair.head, positionPair.last, xMax, yMax)
      }
    }

    println(nodes.size)
  }

  def computePairs(positions: List[(Int, Int)]): List[List[(Int, Int)]] = {
    positions match {
      case Nil => Nil 
      case a :: Nil => Nil
      case a :: rest =>
        val as = rest.map { b =>
          List(a, b)
        }
        as ++ computePairs(rest)
    }
  }

  def findNodesA(a: (Int, Int), b: (Int, Int), xMax: Int, yMax: Int): Set[(Int, Int)] = {
    val (ax, ay) = a 
    val (bx, by) = b 
    val dx: Double = bx - ax 
    val dy: Double = by - ay 

    Set(
      (ax - dx, ay - dy),
      (ax + dx / 3.0, ay + dy / 3.0),
      (bx - dx / 3.0, by - dy / 3.0),
      (bx + dx, by + dy)
    ).filter { case (x, y) =>
      x >= 0 && x <= xMax && y >= 0 && y <= yMax && x.toInt == x && y.toInt == y
    }.map { case (x, y) =>
      (x.toInt, y.toInt)
    }
  }

  def findNodesB(a: (Int, Int), b: (Int, Int), xMax: Int, yMax: Int): Set[(Int, Int)] = {
    val (ax, ay) = a 
    val (bx, by) = b 
    val dx = bx - ax 
    val dy = by - ay 

    def checkBounds(x: Int, y: Int) = x >= 0 && x <= xMax && y >= 0 && y <= yMax

    var nodes = Set.empty[(Int, Int)]

    var x = ax 
    var y = ay
    while (checkBounds(x, y)) {
      nodes += (x, y) 
      x -= dx 
      y -= dy 
    }

    x = bx 
    y = by
    while (checkBounds(x, y)) {
      nodes += (x, y) 
      x += dx 
      y += dy 
    }

    nodes
  }
}
