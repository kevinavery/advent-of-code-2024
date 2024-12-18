
dirs = {
  '^': (0, -1),
  'v': (0, 1),
  '>': (1, 0),
  '<': (-1, 0),
}

def part1(b, moves):
  y = [i for i, v in enumerate(b) if '@' in v][0]
  x = b[y].index('@')

  for m in moves:
    x, y = push1(b, m, x, y)

  sum = 0
  for y in range(len(b)):
    for x in range(len(b[y])):
      if b[y][x] == 'O':
        sum += y * 100 + x
  print(sum)


def push1(b, m, x, y):
  dx, dy = dirs[m]
  nx, ny = x, y
  while b[ny][nx] != '#' and b[ny][nx] != '.':
    nx += dx 
    ny += dy 

  if b[ny][nx] == '#':
    return x, y 
  else:
    b[ny][nx] = b[y + dy][x + dx]
    b[y + dy][x + dx] = '@'
    b[y][x] = '.'
    return x + dx, y + dy


def part2(b, moves):
  b = expand(b)

  y = [i for i, v in enumerate(b) if '@' in v][0]
  x = b[y].index('@')

  for m in moves:
    x, y = push2(b, m, x, y)

  sum = 0
  for y in range(len(b)):
    for x in range(len(b[y])):
      if b[y][x] == '[':
        sum += y * 100 + x
  print(sum)


def push2(b, m, x, y):
  dx, dy = dirs[m]
  if dx != 0 and move_horizontal(b, x, y, dx):
    return x + dx, y
  elif move_vertical(b, x, y, dy):
    return x, y + dy
  return x, y


def move_vertical(b, x, y, dy):
  positions = set()

  def find_positions(x, y):
    if b[y + dy][x] == '.':
      positions.add((x, y, b[y][x]))
      return True
    elif b[y + dy][x] == '[' and find_positions(x, y + dy) and find_positions(x + 1, y + dy):
      positions.add((x, y, b[y][x]))
      return True
    elif b[y + dy][x] == ']' and find_positions(x - 1, y + dy) and find_positions(x, y + dy):
      positions.add((x, y, b[y][x]))
      return True
    else:
      return False

  can_move = find_positions(x, y)

  if can_move:
    positions = list(positions)
    positions.sort(key=lambda p: -dy * p[1])
    for px, py, pc in positions:
      b[py + dy][px] = pc
      b[py][px] = '.'

  return can_move


def move_horizontal(b, x, y, dx):
  nx = x + dx
  while b[y][nx] != '#' and b[y][nx] != '.':
    nx += dx

  if b[y][nx] == '#':
    return False
  else:
    while nx != x - dx:
      b[y][nx] = b[y][nx - dx]
      nx -= dx

    b[y][x] = '.'

    return True


def expand(b):
  bb = []
  for y in range(len(b)):
    bb.append([])
    for x in range(len(b[y])):
      if b[y][x] == '@':
        bb[y].extend(['@', '.'])
      elif b[y][x] == '.':
        bb[y].extend(['.', '.'])
      elif b[y][x] == 'O':
        bb[y].extend(['[', ']'])
      elif b[y][x] == '#':
        bb[y].extend(['#', '#'])
  return bb


if __name__ == '__main__':
  parsing_board = True
  b = []
  moves = ''
  with open('input.txt', 'r') as file:
    for line in file:
      if parsing_board and len(line.strip()) == 0:
        parsing_board = False
      elif parsing_board:
        b.append(list(line.strip()))
      else:
        moves += line.strip()

  #part1(b, moves)
  part2(b, moves)




