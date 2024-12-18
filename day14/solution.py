from dataclasses import dataclass
import re

@dataclass
class Robot:
  x: int
  y: int 
  dx: int 
  dy: int


def part1(robots):
  rows = 103
  cols = 101

  t = [0, 0, 0, 0]
  for r in robots:
    for _ in range(100):
      step(r, rows, cols)

    if r.x < cols // 2:
      if r.y < rows // 2:
        t[0] += 1
      elif r.y > rows // 2:
        t[1] += 1
    if r.x > cols // 2:
      if r.y < rows // 2:
        t[2] += 1
      elif r.y > rows // 2:
        t[3] += 1

  print(t[0] * t[1] * t[2] * t[3])


def part2(robots):
  rows = 103
  cols = 101

  for steps in range(10000):
    for r in robots:
      step(r, rows, cols)
    display(robots, rows, cols, steps)


def display(robots, rows, cols, steps):
  d = [[' '] * cols for _ in range(rows)]
  for r in robots:
    d[r.y][r.x] = 'X'

  connections = 0
  for y in range(rows):
    for x in range(cols):
      if d[y][x] == 'X':
        for dy in [-1,0,1]:
          for dx in [-1,0,1]:
            ny = y + dy 
            nx = y + dx
            if (nx, ny) != (x, y) and 0 <= nx < cols and 0 <= ny < rows and d[ny][nx] == 'X':
              connections += 1

  if connections / len(robots) > 1.5:
    print('\n'.join([''.join(d[row]) for row in range(rows)]))
    print(f'========== {steps} ==========')


def step(r, rows, cols):
  r.x += r.dx
  if r.x < 0:
    r.x += cols
  elif r.x >= cols:
    r.x -= cols

  r.y += r.dy 
  if r.y < 0:
    r.y += rows
  elif r.y >= rows:
    r.y -= rows


if __name__ == '__main__':
  robots = []
  with open('input.txt', 'r') as file:
    for line in file:
      if len(line.strip()) > 0:
        m = re.search(r'p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)', line)
        robots.append(Robot(
          x=int(m[1]), 
          y=int(m[2]), 
          dx=int(m[3]),
          dy=int(m[4]),
        ))

  #part1(robots)
  part2(robots)


