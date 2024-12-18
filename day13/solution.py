from dataclasses import dataclass, replace
import re
from typing import Optional

@dataclass(frozen=True)
class Claw:
  a_x: int
  a_y: int 
  b_x: int 
  b_y: int
  prize_x: int
  prize_y: int


def part1(claws):
  total = 0
  for c in claws:
    t = find_min_tokens(c)
    if t:
      total += t
  print(total)


def find_min_tokens(claw) -> Optional[int]:
  min_tokens = None

  for a in range(1 + max(claw.prize_x // claw.a_x, claw.prize_y // claw.a_y)):
    px = claw.prize_x - a * claw.a_x 
    py = claw.prize_y - a * claw.a_y 

    b = px / claw.b_x 
    if b == int(b) and py == claw.b_y * b:
      cost = a * 3 + int(b)
      if not min_tokens or cost < min_tokens:
        min_tokens = cost

  return min_tokens


def part2(claws):
  total = 0
  offset = 10000000000000
  for c in claws:
    t = find_min_tokens_optimized(replace(c, prize_x=c.prize_x+offset, prize_y=c.prize_y+offset))
    if t:
      total += t
  print(total)


def find_min_tokens_optimized(c) -> Optional[int]:
  # Solve the system of equations:
  # axA + bxB = px 
  # ayA + byB = py 
  #
  # A = (px - bxB) / ax
  #
  # ay ((px - bxB) / ax) + byB = py 
  # (aypx - aybxB) / ax + byB = py 
  # aypx - aybxB + axbyB = axpy 
  # axbyB - aybxB = axpy - aypx 
  # B = (axpy - aypx) / (axby - aybx)

  b = (c.a_x * c.prize_y - c.a_y * c.prize_x) // (c.a_x * c.b_y - c.a_y * c.b_x)
  a = (c.prize_x - c.b_x * b) // c.a_x

  if c.a_x * a + c.b_x * b == c.prize_x and c.a_y * a + c.b_y * b == c.prize_y:
    return a * 3 + b
  else:
    return None


if __name__ == '__main__':
  claws = []
  with open('input.txt', 'r') as file:
    for line in file:
      if len(line.strip()) > 0:
        a_match = re.search(r'X\+(\d+), Y\+(\d+)', line)
        b_match = re.search(r'X\+(\d+), Y\+(\d+)', next(file))
        p_match = re.search(r'X=(\d+), Y=(\d+)', next(file))
        claws.append(Claw(
          a_x=int(a_match[1]), 
          a_y=int(a_match[2]), 
          b_x=int(b_match[1]),
          b_y=int(b_match[2]),
          prize_x=int(p_match[1]),
          prize_y=int(p_match[2]),
        ))

  part1(claws)
  part2(claws)


