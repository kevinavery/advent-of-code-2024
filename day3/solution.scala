object Main {

  def main(args: Array[String]): Unit = {
    var text = scala.io.Source.fromFile("input.txt").mkString

    val sum = text.split("""do\(\)""").map { s =>
      val i = s.indexOf("don't()")
      if (i == -1) {
        sumMul(s)
      } else {
        sumMul(s.substring(0, i))
      }
    }.sum

    println(sum)
  }

  def sumMul(s: String): Int = {
    val p = """mul\((\d{1,3}),(\d{1,3})\)""".r
    p.findAllMatchIn(s).map { m =>
      m.group(1).toInt * m.group(2).toInt
    }.sum
  }
}
