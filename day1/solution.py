import re


def part1():
  l1 = []
  l2 = []
  with open('input.txt', 'r') as file:
    for line in file:
      parts = re.split('\\s+', line)
      l1.append(int(parts[0]))
      l2.append(int(parts[1]))

  l1.sort()
  l2.sort()
  print(sum([abs(l1[i] - l2[i]) for i in range(len(l1))]))  


def part2():
  l1 = []
  c2 = {}
  with open('input.txt', 'r') as file:
    for line in file:
      parts = re.split('\\s+', line)
      l1.append(int(parts[0]))
      n2 = int(parts[1])
      if n2 in c2:
        c2[n2] += 1
      else:
        c2[n2] = 1

  print(sum([n * c2[n] if n in c2 else 0 for n in l1]))


if __name__ == '__main__':
  # part1()
  part2()


