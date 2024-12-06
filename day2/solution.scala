object Main {

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.fromFile("input.txt").getLines
    println(lines.filter(isSafeDamper).size)
  }

  def isSafeDamper(l: String): Boolean = {
    val nums = l.split("\\s+").map(_.toInt).toVector

    def combos = 0.until(nums.length).map { i =>
      nums.take(i) ++ nums.drop(i + 1)
    }.map(_.mkString(" "))

    combos.exists(isSafe)
  }

  def isSafe(l: String): Boolean = {
    val nums = l.split("\\s+").map(_.toInt).toVector

    if (nums == nums.sorted) {
      1.until(nums.length).forall { i =>
        val diff = nums(i) - nums(i - 1)
        diff >= 1 && diff <= 3
      }
    } else if (nums == nums.sorted.reverse) {
      1.until(nums.length).forall { i =>
        val diff = nums(i - 1) - nums(i)
        diff >= 1 && diff <= 3
      }
    } else {
      false
    }
  }
}