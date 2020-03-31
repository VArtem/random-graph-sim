import matplotlib
import matplotlib.pyplot as plt
import math

m = 4

xs, ys = [], []
for line in open(str(m) + '.1.txt', 'r'):
	x, y = map(float, line.split())
	xs.append(x)
	ys.append(y)

plt.scatter(xs, ys, color='red', s=3)

xs, ys = [], []
for line in open(str(m) + '.2.txt', 'r'):
	x, y = map(float, line.split())
	xs.append(x)
	ys.append(y)

plt.scatter(xs, ys, color='blue', s=3)

xs, ys = [], []
for line in open(str(m) + '.2.txt', 'r'):
	x, y = map(float, line.split())
	xs.append(x)
	x *= 2
	y = math.factorial(3 * m - 3) * x**(m-1) / math.factorial(m) / math.factorial(2 * m - 1) / (1 + x)**(3*m-2)
	ys.append(y * m)

plt.plot(xs, ys, color='yellow')



plt.show()