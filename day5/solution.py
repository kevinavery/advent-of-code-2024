
def part1(rules, tests):
  sum = 0

  for ns in tests:
    satisfies_rules = True

    for [a, b] in rules:
      if a in ns and b in ns and ns.index(a) > ns.index(b):
        satisfies_rules = False
        break 

    if satisfies_rules:
      sum += ns[len(ns) // 2]

  print(sum)

def part2(rules, tests):
  sum = 0

  for ns_orig in tests:
    ns = ns_orig.copy()
    changed = True 
    while changed:
      changed = False 
      for [a, b] in rules:
        if a in ns and b in ns:
          a_index = ns.index(a) 
          b_index = ns.index(b) 
          if a_index > b_index:
            ns.remove(a)
            ns.insert(b_index, a)
            changed = True

    if ns != ns_orig:
      sum += ns[len(ns) // 2]

  print(sum)

if __name__ == '__main__':
  rules = []
  tests = []

  with open('input.txt', 'r') as file:
    for line in file:
      if '|' in line:
        r = [int(x) for x in line.split('|')]
        rules.append(r)
      elif ',' in line:
        t = [int(x) for x in line.split(',')]
        tests.append(t)

  #part1(rules, tests)
  part2(rules, tests)
