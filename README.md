# Package Challenge

Given package challenge is based on the 0/1 knapsack problem. Knapsack means bag. All the bags has maximum allowed weight limit. Challenge is to find best available sets of items to put into the bag to produce maximum output. Items cannot be broken, so either item can be added to the bag or it can be ignored. Knapsack problems appear in real-world decision-making processes in a wide variety of fields, such as finding the least wasteful way to cut raw materials.

## Problem statement

This program should accept as its first argument a path to a filename. The input file contains several lines. Each line is one test case.

Each line contains the weight that the package can take (before the colon) and the list of things you need to choose. Each thing is enclosed in parentheses where the 1st number is a thing's index number, the 2nd is its weight and the 3rd is its cost. 

### Input Example

81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)

8 : (1,15.3,€34)

75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)

56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)

For each set of things that you put into the package provide a list (items’ index numbers are separated by comma).
 

### Output Example

4

\-

7, 2

8, 9

### Constraints

You should write a class com.mobiquityinc.packer.Packer with a static method named pack. This method accepts the absolute path to a test file as a String. It does return the solution as a String. Your class should throw an com.mobiquityinc.exception.APIException if incorrect parameters are being passed.

Additional constraints:

1. Max weight that a package can take is ≤ 100

2. There might be up to 15 items you need to choose from

3. Max weight and cost of an item is ≤ 100

 
### Solution Logic


I have used Dynamic programming using concept of dominance. Following is the details algorithm.

 
For each line in the input file, package object will be created with maximum allowed weight and list of items, say itemlist. In each package only the
items which contains weight <= maximum allowed weight will be added. This way items which are not contributing the solution will be removed at first 
and number of iterations will be reduced.

Now item list is prepared for each package object. Firstly, item with (0,0,0) will be added to the package itemlist and the same list is sorted by 
cost/weight ratio in decending order to keep most eligible item on top.

For each package 2 steps are done as below:

-	Create items lists with all combinatio
- 	Search the best items from combinations

## Create Item Lists With All Combinations 

- Create processing list with first item from current package's itemlist.

For i=1 to itemlist size : 

1. Take processing list's i-1, L(i-1) package & current package's item list's ith item i(i).
2. Add cost and weight of i(i) to each item of list L(i-1), filter out if weight is > maximum allowed weight in package and prepare the extended list.
3. Combine L(i-1) & extended list and remove dublicates.
4. Remove the item from combinations list prepared in step 3, if another item is better than that. It is identified if it matches any of the below condition.
	-	if any other item has less weight and higher or equal cost. 
	-	if any other item has same weight and higher cost.
5. Create the package object with list prepared in step 4 and allowed maximum weight of the package. 

Now packages with all combination of itemlist are ready, having best possible items which may contribute to solution. In next step, we will search the 
best items from this combination.

## 	Search The Best Items From Combinations

- Start from last itemlist, pick up the best item which is with maximum cost. Pair its weight and cost, say pair(w,c).
- Traverse backward in combination list from i=n-1 (n is last item list index) to 0 , if the best item's weight and cost pair(w,c) is found in previous list then it not part of solution, keep traversing backward.
- If the pair(w,c) is not found in previous list with index i, then i+1 item from package itemlist is part of solution. Add the item to result list.
- Subtract the cost and weight of that id from pair(w,c) and remaining will be new pair(w,c) for which, we will continue traversing backward. 

Then we will join the ids in result list by ' ,' and That's our answer!! 

## Design Pattern

- Design pattern used in this approach is strategy pattern. PackageChallenge class follows the strategy design pattern.
