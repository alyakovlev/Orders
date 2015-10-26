This is a simple implementation of a task I had once. I used Java 7 and SWING to solve it.

Imagine you are a company that wants to assist some US-based companies to ship their
goods to customers. To achieve this, lets assume that every order can be
represented by a tuple (orderId, companyName, customerAdress, orderedItem).
Thus, some sample data might look like this (c.f. sample.txt):

001, SuperTrader, Steindamm 80, Macbook
002, Cheapskates, Reeperbahn 153, Macbook
003, MegaCorp, Steindamm 80, Book "Guide to Hamburg"
004, SuperTrader, Sternstrasse 125, Book "Cooking 101"
005, SuperTrader, Ottenser Hauptstrasse 24, Inline Skates
006, MegaCorp, Reeperbahn 153, Playstation
007, Cheapskates, Lagerstrasse 11, Flux compensator
008, SuperTrader, Reeperbahn 153, Inline Skates

Implement a working solution to read the data
from an input file, store them in a data structure in memory, and then perform the
following kind of operations on the data:
1) show all orders from a particular company
2) show all orders to a particular address
3) delete a particular order given an OrderId
4) display how often each item has been ordered, in descending order (ie in the
above example, 2x for Macbook and Inline skates, 1x for the rest)