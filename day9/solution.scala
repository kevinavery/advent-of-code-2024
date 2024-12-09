import scala.collection.immutable.SortedMap

object Main {

  def main(args: Array[String]): Unit = {
    val line = scala.io.Source.fromFile("input.txt").mkString
    val ns = line.trim.toVector.map(_.toString.toInt)

    var id = 0
    var v = Vector.empty[Int]
    0.until(ns.length).foreach { i =>
      0.until(ns(i)).foreach { _ =>
        if (i % 2 == 0) {
          v = v :+ id 
        } else {
          v = v :+ -1
        }
      }

      if (i % 2 == 0) {
        id += 1
      }
    }

    part1(v)
    part2b(v)
  }

  def part1(_v: Vector[Int]): Unit = {
    var v = _v
    var left = 0
    var right = v.length - 1
    while (left < right) {
      if (v(left) == -1) {
        val t = v(right)
        v = v.updated(right, -1)
        v = v.updated(left, t)
        right -= 1
      }
      while (v(right) == -1) {
        right -= 1
      }
      left += 1
    }

    println(checksum(v))
  }

  // Simple but slow.
  def part2a(_v: Vector[Int]): Unit = {
    var v = _v
    var id = v.max 

    while (id > 0) {
      println(id)
      val right = v.indexOf(id)
      val c = v.count(_ == id)

      0.until(v.size - c).foreach { left =>
        if (left < right && left.until(left + c).forall(i => v(i) == -1)) {
          0.until(c).foreach { i =>
            val t = v(right + i)
            v = v.updated(right + i, -1)
            v = v.updated(left + i, t)
          }
        }
      }

      id -= 1
    }

    println(checksum(v))
  }

  def part2b(_v: Vector[Int]): Unit = {
    var v = _v
    var gaps = SortedMap.empty[Int, Int]
    var blocks = List.empty[(Int, (Int, Int))]
    var i = 0
    while (i < v.size) {
      val start = i 
      while (i < v.size && v(i) == v(start)) {
        i += 1
      }

      if (v(start) == -1) {
        gaps = gaps.updated(start, i - start)
      } else {
        blocks = (v(start), (start, i - start)) :: blocks
      }
    }

    blocks.foreach { case (id, (blockIndex, blockSize)) =>
      gaps.filter(_._1 < blockIndex).find(_._2 >= blockSize).foreach { (gapIndex, gapSize) =>
        0.until(blockSize).foreach { i =>
          v = v.updated(gapIndex + i, id)
          v = v.updated(blockIndex + i, -1)
        }
        gaps = gaps.removed(gapIndex)
        if (gapSize > blockSize) {
          gaps = gaps.updated(gapIndex + blockSize, gapSize - blockSize)
        }
      }
    }

    println(checksum(v))
  }

  def checksum(v: Vector[Int]): Long = {
    v.zipWithIndex.filter(_._1 > 0).map((id, i) => id * i.toLong).sum
  }
}
