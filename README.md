# DaggerTutorial

## Introduction

Like many people I struggled with understanding dependency injection when the topic was first
introduced to me. I believe this struggle was due to a few reasons, and I hope this guide will
help clarify those issues.

First I would like to address the reasons I believe dependency injection, and specifically dagger,
can be so challenging for developers to learn. I think this boils down to three main points:

1. The term "dependency injection" is not well defined, or at least it is not used in a consistent
manner out in the wild.
2. The dagger documentation, although much better now, is somewhat lacking and doesn't always give
a systematic and clear overview of the steps involved in setting up, using and understanding the
internals of dagger.
3. Dependency injection *is* complicated, despite what more experienced developers might have you
believe.

### What Dagger is Doing Under the Hood

This section is probably the closest to a TL;DR in this guide, but it does require a bit of
background. You'll notice I don't mention the words "dependency" or "injection" in this explanation,
as the actual mechanics of what dagger does doesn't require such concepts (if you understand the
underlying systems).

When a developer writes an object oriented computer program that will execute code, there are two
broad categories of actions that code can take:

1. It can execute code that does some kind of logic or system calls - for instance it can add two
numbers or it could request the current time from the system.
2. It can create an object of some Type and store it in memory for later use.

Dagger is concerned with the second action - it helps a developer keep track of creating objects
and storing them for later.

In Kotlin there are two main main ways to create objects. We can either

1. Invoke the constructor of a class which will create the new object and return it to us.
2. Invoke a function that will return an object to us (and if you follow this function call back
eventually you will get to a point where somewhere a constructor is getting invoked).

The important thing to realize, though, is that every object that is returned to us is of a certain
type. Type theory is beyond the scope of this guide, but in Kotlin it's important to realize there
can be a potentially infinite number of types that are possible from a given generic type definition
. For instance `class List<T>` could produce Types of `List<Boolean>`, but also `List<List<Boolean>>
` and so on. Each of these Types has a different signature, and therefore is a distinct entity as
far as dagger is concerned.

With this knowledge of object creation and type signatures, we can now give a good definition of
what dagger does under the hood. *Dagger maps class constructors to type signatures*, so if a
developer requests an object of a certain type through dagger, dagger will invoke the constructor
mapped to the type signature and the correct object will be returned. We will learn later how the
developer lets dagger know how to create objects. The below diagram illustrates this relationship.

<img src="https://github.com/user-attachments/assets/22b372e7-8545-4861-a10c-1cffd8b969f5" width="400">

Now this seems all simple enough, but this is where things get complicated. When we said that
invoking a class constructor is the way most objects get made, this left out one detail: the
constructor itself may have arguments of its own that are required to be passed in when the
constructor is invoked. So we need to update our definition of what dagger does slightly. *Dagger
maps class constructors to type signatures, and if the class constructor itself requires object
parameters of certain types dagger will provide those objects as well - recursively, which will
always end with a class constructor with no parameters.* To do this dagger will keep a list of types
for each class constructor so it can lookup the types needed all the way up the chain of constructor
calls. The below diagram illustrates this new relationship:

<img src="https://github.com/user-attachments/assets/5a581aca-db66-4578-a9d0-28c9efe68960" width="450">

Dagger now has all it needs to generate an object of any type. For example if an object of type
`Animal` was now requested by the dagger user, dagger would have generated code similar to this:
`Animal(plant: Plant(water: Water(), sun: Sun()))`. Of course the actual code dagger generates is
much more complicated than this (for one thing dagger wraps all object creation in a `Provider`
class), but for the sake of illustrative purposes this let's us understand what is happening by
dagger internally.




