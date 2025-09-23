This is an utility mod I made in order to make some armor sets for my modpack using CTSetBonus. It adds some Zen Methods to create some customizable effects easier:

1. A stack tracker that can be used to add effects that "stacks" without using potion effects, with a GUI displaying the number of stacks to the client.
2. A cooldown tracker, which can be used to add a cooldown to some effect for a player, also with a GUI that displays the time remaining.
3. An entity marker, this can be used to "link" an entity to a player, which can be used for some particular effects that should only happen to that entity for the linked player.

Additionally, some general utility methods are also provided. 

## 1. Stack tracker

IPlayer and IEntity extensions that allows managing stack effects, useful when wanting to give a entity a "stack" of something without requiring to create a new potion effect and use some logic with potion effect levels. 

The amount of stacks the player have will be displayed together with an icon, a bar indicating the remaining duration. What is shown, the position, and the size, can be tweaked in the config file. Zen methods are provided to configure what icon should be displayed for a given stack and the colors. 

### addStacks

```
addStacks(String stackName, long durationTicks, String refreshRule, int quantity)
```

This would give the player {quantity} stacks of the type {StackName} that would last {durationTicks} ticks following a specific refresh rule, which can be that when adding new stacks it resets the duration (refreshRule = "reset"), adds to the duration (refreshRule = "add"), or preserve the initial duration (refreshRule = "preserve"). Can be used to just refresh the duration if you add zero stacks.

```
// Example 1: Add a stack of "Beserking" to the player with the duration of 200 ticks (10 seconds), following the refresh rule of "reset".

iPlayer.addStacks("Beserking", 200, "reset", 1);

// Example 2: Add a stack of "Corruption" to the entity with the duration of 300 ticks (15 seconds), following the refresh rule of "preserve".

iEntity.addStacks("Corruption", 200, "preserve", 1);
```

### removeStacks

```
removeStacks(String stackName, int quantity);
```

Remove {quantity} stacks of {stackName} that the player have.


```
// Example : Remove 1 stack of "Beserking" from a player.

iPlayer.removeStacks("Beserking", 1);

// Example : Remove 3 stacks of "Corruption" from an entity.

iEntity.removeStacks("Corruption", 3);
```

### clearStacks

```
clearStacks(String stackName);
```

Similar to removeStacks, just clear all the stacks of {stackName}.

```
// Example : Clears all stacks of "Beserking" from the player.

iPlayer.clearStacks("Beserking");

// Example : Clears all stacks of "Beserking" from the entity. 

iEntity.clearStacks("Corruption");
```

### getStacks

```
getStacks(String stackName);
```

Get the number of stacks a player have of {stackName}. Returns zero if there are no stacks.

```
// Example : Get how many stacks of "Beserking" a player has.

iPlayer.getStacks("Beserking");

// Example : Get how many stacks of "Corruption" an entity have.

iPlayer.getStacks("Corruption");
```

## 2. Cooldown Tracker

IPlayer extension that add a cooldown timer for a player. This can be useful when adding effects that have a cooldown

### startCooldown

```
startCooldown(String cooldownId, int duration);
```

Start a cooldown for a player given its id.

```
// Example : Start a cooldown for a bonus damage that is added to an attack. The cooldown expires in 10 seconds.

iPlayer.startCooldown("Bonus Damage", 200);
```

### onCooldown

Return true if the cooldown is not yet finished, return false when the cooldown finishes.

```
onCooldown(String cooldownId);
```

```
// Example : Returns false only when the effect is not on cooldown.

iPlayer.onCooldown("Bonus Damage");
```

## 3. Entity Marker

IPlayer extension that marks an entity to a player. This can be used in effects that requires to remember an interaction between a player and specific entities, such as dealing more damage to entities already hit by the player.

### markEntity

Register a entity as marked for a player for a certain duration.

```
markEntity(String markId, IEntity iEntity, long duration)
```

```
// Example : Register a entity as recently attacked by the player for 10 seconds.

iPlayer.markEntity("Recently Attacked", iEntity, 200);
```

### getMarkedEntities

Return an array of IEntities that are marked for the player. 

```
getMarkedEntities(String markId)
```

```
// Example : Returns the entities that were recently attacked by the player.

iPlayer.getMarkedEntities("Recently Attacked");
```

## 4. General Utilities

### IEntity Extensions

```
// Return a IPlayer if the entity is a player, returns null otherwise
asIPlayer()

// Return a list of IPotionEffects that are the potion effects currently active on the entity
getActivePotionEffects()

// Return how many potion effects are currently active on the entity
getNumberOfActivePotionEffects()

```
