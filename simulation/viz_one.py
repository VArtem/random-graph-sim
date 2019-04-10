import matplotlib
import matplotlib.pyplot as plt
import math
import sys

xs, ys = [], []
for line in open(sys.argv[1], 'r'):
	x, y = map(float, line.split())
	xs.append(x)
	ys.append(y)

plt.plot(xs, ys, color=sys.argv[2] if len(sys.argv) >= 3 else 'red')
plt.show()