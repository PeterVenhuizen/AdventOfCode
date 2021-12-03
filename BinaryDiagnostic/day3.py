#!/usr/bin/env python3

from collections import Counter

def get_gamma_rate(rates):
    gamma_rate = []
    for r in rates:
        gamma_rate.append(max(r, key=r.get))

    return ''.join(gamma_rate)

def get_epsilon_rate(rates):
    epsilon_rate = []
    for r in rates:
        epsilon_rate.append(min(r, key=r.get))
    return ''.join(epsilon_rate)

def to_decimal(b):
    return int(b, 2)

rates = []

for line in open('day_3_input.txt'):
    # print(line.rstrip())
    bit_rate = line.rstrip()
    for i, b in enumerate(bit_rate):

        if len(rates) < i + 1:
            rates.append(Counter({ '0': 0, '1': 0}))
        rates[i][b] += 1

# print(rates)
# gamma_rate = get_gamma_rate(rates)
# print(to_decimal(gamma_rate))

# epsilon_rate = get_epsilon_rate(rates)
# print(to_decimal(epsilon_rate))

# print (to_decimal(gamma_rate) * to_decimal(epsilon_rate))

numbers = []
for line in open('day_3_input.txt'):
    numbers.append(line.rstrip())

def find_value(numbers, idx, min_or_max, default):

    if (len(numbers) == 1):
        return numbers[0]

    # get most frequent value
    ones, zeroes = 0, 0
    for n in numbers:
        if (int(n[idx]) == 0):
            zeroes += 1
        else:
            ones += 1

    # determine value to use
    if (ones == zeroes):
        val_to_select = default
    elif (min_or_max == "min"):
        if (ones < zeroes):
            val_to_select = 1
        else:
            val_to_select = 0
    else:
        if (ones > zeroes):
            val_to_select = 1
        else:
            val_to_select = 0

    print(ones, zeroes)
    print(idx, val_to_select)

    # subselect
    new_array = []
    for n in numbers:
        if (int(n[idx]) == val_to_select):
            new_array.append(n)

    # run again
    return find_value(new_array, idx + 1, min_or_max, default)

O2 = find_value(numbers, 0, "max", 1)


CO2 = find_value(numbers, 0, "min", 0)
print(O2, to_decimal(O2))
print(CO2, to_decimal(CO2))

print(to_decimal(O2) * to_decimal(CO2))