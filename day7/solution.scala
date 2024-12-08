object Main {

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.fromFile("input.txt").getLines

    val matching = lines.map { l =>
      val colonIndex = l.indexOf(":")
      val total = l.take(colonIndex).toLong
      val operands = l.drop(colonIndex + 2).split(" ").toList.map(_.toLong)
      if (checkB(total, operands)) {
        println(l)
        total
      } else {
        0
      }
    }

    println(matching.sum)
  }

  def checkA(total: Long, operands: List[Long]): Boolean = {

    def rec(operands: List[Long]): List[Long] = {
      operands match {
        case Nil => Nil
        case x :: Nil => List(x)
        case x :: rest =>
          rec(rest).flatMap { computedTotal =>
            List(x + computedTotal, x * computedTotal)
          }
      }
    }

    rec(operands.reverse).exists(_ == total)
  }

  def checkB(total: Long, operands: List[Long]): Boolean = {

    def rec(operands: List[Long]): List[Long] = {
      operands match {
        case Nil => Nil
        case x :: Nil => List(x)
        case x :: rest =>
          rec(rest).flatMap { computedTotal =>
            List(x + computedTotal, x * computedTotal, (computedTotal.toString + x.toString).toLong)
          }
      }
    }

    rec(operands.reverse).exists(_ == total)
  }
}