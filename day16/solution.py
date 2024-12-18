
def part1(m):
  paths = find_paths(m)
  print(min([p[1] for p in paths]))


def part2(m):
  paths = find_paths(m)
  min_score = min([p[1] for p in paths])
  positions = [xy for p in paths if p[1] == min_score for xy in p[0]]
  print(len(set(positions)))


def find_paths(m):
  dirs = {
    '^': (0, -1),
    'v': (0, 1),
    '>': (1, 0),
    '<': (-1, 0),
  }

  sx, sy = 1, len(m) - 2
  ex, ey = len(m[0]) - 2, 1

  paths = []
  visited = {}
  queue = [(0, '>', [(sx, sy)])]

  while queue:
    score, direction, path = queue.pop(0)
    x, y = path[-1]

    if (x, y) == (ex, ey):
      paths.append((path, score))
      continue 

    if (x, y, direction) in visited and visited[(x, y, direction)] < score:
      continue

    visited[(x, y, direction)] = score

    for nd, (dx, dy) in dirs.items():
      nx, ny = x + dx, y + dy
      if m[ny][nx] != '#':
        score_delta = 1 if nd == direction else 1001
        queue.append((score + score_delta, nd, path + [(nx, ny)]))

  return paths


if __name__ == '__main__':
  m = []
  with open('input.txt', 'r') as file:
    for line in file:
      m.append(list(line.strip()))

  part1(m)
  part2(m)




