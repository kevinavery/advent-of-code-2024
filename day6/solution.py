from copy import deepcopy

def part1(m, x, y):
  while check_bounds(m, x, y):
    print(x, y)
    (x, y) = step(m, x, y)

  print(sum([len([c for c in l if c == 'X']) for l in m]))


def part2(m, x, y):
  loop_count = 0

  for yo in range(len(m)):
    for xo in range(len(m[yo])):
      if m[yo][xo] == '.':
        m_test = deepcopy(m)
        m_test[yo][xo] = '#'
        (x_test, y_test) = (x, y)

        visited = set()
        loop_detected = False

        while check_bounds(m_test, x_test, y_test) and not loop_detected:
          v = (x_test, y_test, m_test[y_test][x_test])
          loop_detected = v in visited

          if loop_detected:
            loop_count += 1
            print(xo, yo, loop_count)

          visited.add(v)
          (x_test, y_test) = step(m_test, x_test, y_test)

  print(loop_count)


def step(m, x, y):
  directions = {
    '^': (0, -1, '>'),
    '>': (1, 0, 'v'),
    'v': (0, 1, '<'),
    '<': (-1, 0, '^'),
  }
  t = m[y][x]
  (dx, dy, next_t) = directions[t]

  while check_bounds(m, x + dx, y + dy) and m[y + dy][x + dx] == '#':
    t = next_t
    (dx, dy, next_t) = directions[t]

  m[y][x] = 'X'
  x += dx
  y += dy
  if check_bounds(m, x, y):
    m[y][x] = t

  return (x, y)


def check_bounds(m, x, y):
  return 0 <= y < len(m) and 0 <= x < len(m[y])


if __name__ == '__main__':
  m = []
  with open('input.txt', 'r') as file:
    for line in file:
      m.append(list(line.strip()))

  y = [i for i, v in enumerate(m) if '^' in v][0]
  x = m[y].index('^')

  #part1(deepcopy(m), x, y)
  part2(deepcopy(m), x, y)


