object Main {

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.fromFile("input.txt").getLines

    val m = lines.map(_.toVector).toVector

    var sum = 0
    0.until(m.length).foreach { row =>
      0.until(m(0).length).foreach { col =>
        sum += count2b(m, row, col)
      }
    }

    println(sum)
  }

  def count2(m: Vector[Vector[Char]], row: Int, col: Int): Int = {
    if (row <= 0 || row >= m.length - 1|| col <= 0 || col >= m(0).length - 1 || m(row)(col) != 'A') {
      0
    } else {
      val d1 = m(row - 1)(col - 1) == 'M' && m(row + 1)(col + 1) == 'S' 
      val d2 = m(row - 1)(col - 1) == 'S' && m(row + 1)(col + 1) == 'M' 

      val d3 = m(row + 1)(col - 1) == 'M' && m(row - 1)(col + 1) == 'S' 
      val d4 = m(row + 1)(col - 1) == 'S' && m(row - 1)(col + 1) == 'M' 

      if ((d1 || d2) && (d3 || d4)) {
        1
      } else {
        0
      }
    }
  }

  def count2b(m: Vector[Vector[Char]], r: Int, c: Int): Int = {
    try {
      val a = List(m(r-1)(c-1), m(r)(c), m(r+1)(c+1)).mkString
      val b = List(m(r-1)(c+1), m(r)(c), m(r+1)(c-1)).mkString
      val terms = Set("MAS", "SAM")
      if (terms.contains(a) && terms.contains(b)) 1 else 0
    } catch {
      case _ => 0
    }
  }

  def count1(m: Vector[Vector[Char]], row: Int, col: Int): Int = {
    val directions = List(
      (0, 1),   // right
      (0, -1),  // left
      (1, 0),   // down
      (-1, 0),  // up
      (1, 1),   // down-right
      (1, -1),  // down-left
      (-1, 1),  // up-right
      (-1, -1)  // up-left
    )
    
    val target = "XMAS"
    
    directions.count { case (dr, dc) =>
      0.until(target.length).forall { i =>
        val newRow = row + (dr * i)
        val newCol = col + (dc * i)
        newRow >= 0 && newRow < m.length &&
        newCol >= 0 && newCol < m(0).length &&
        m(newRow)(newCol) == target(i)
      }
    }
  }
}