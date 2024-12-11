object Main {

  def main(args: Array[String]): Unit = {
    val ns = scala.io.Source.fromFile("input.txt").mkString.trim.split(" ").toList.map(_.toLong)
    println(rearrange(ns, 25).size)
    println(optimized(ns, 75))
  }

  def rearrange(ns: List[Long], blinks: Int): List[Long] = {
    if (blinks == 0) {
      ns
    } else {
      rearrange(ns.flatMap(next), blinks - 1)
    }
  }

  def optimized(ns: List[Long], blinks: Int): Long = {
    val s = ns.foldLeft(Map.empty[Long, Long]) { (acc, n) =>
      acc.updated(n, 1 + acc.getOrElse(n, 0L))
    }

    0.until(blinks).foldLeft(s) { (s, _) =>
      s.foldLeft(Map.empty[Long, Long]) { case (acc, (n, k)) =>
        next(n).foldLeft(acc) { (acc, m) =>
          acc.updated(m, (k + acc.getOrElse(m, 0L)))
        }
      }
    }.values.sum
  }

  def next(n: Long): List[Long] = {
    n match {
      case 0 =>
        List(1L)
      case x if x.toString.length % 2 == 0 =>
        val s = x.toString  
        val c = s.length / 2
        List(s.take(c).toLong, s.drop(c).toLong)
      case x =>
        List(x * 2024)
    }
  }
}
