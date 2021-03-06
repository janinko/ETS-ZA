breed [humans human]
breed [zombies zombie]
breed [halos halo]

extensions [ gbui ]

globals [
  human-population
  zombie-population
  see-cone
  base-TTL
  food-TTL
  h-current-food-count
  damage-to-zombie
  damage-to-human
  healed-zombie
  healed-human
  time
  total-time
  zombies-count-at-end
  humans-count-at-end
  iterations
  base-seed
  seed
  
  gvalue
  stayU
  saveU
  fearU
  attkG
  shotG
  kinfG
  heatG
  avodG
  pckaG
]

humans-own [  
  TTL ; represents health of a human, number of tick after which he dies
  infection-timeout ; if the value is < 0 the human is not infected
  ammo
]

zombies-own [  
  TTL ; number of ticks after which the zombie dies
]

patches-own [
  z-food
  h-food
  ammo-boxes
]

; ===== Run =====
to runSetup
  clear-all
  
  ; set stayU 20
  ; set saveU 3
  ; set fearU 0.7
  ; set attkG 0.8
  ; set shotG 0.6
  ; set kinfG 0.3
  ; set heatG 1
  ; set avodG 0.7
  ; set pckaG 0.7
  
  set stayU 5 ; done
  set saveU 0 ; done
  set fearU 0.9 ; done
  set attkG 0.5 ; done
  set shotG 1.0 ; done
  set kinfG 0.2 ; done
  set heatG 0.25 ; done
  set avodG 0.7 ; done
  set pckaG 0.3 ; done
  
  setup
  reset-ticks
end

; ===== SETUP =====
to setup
  clear-turtles
  clear-patches
  clear-drawing
  clear-output
  gbui:clear

; ==== set globals ====
  set see-cone 90

  set base-TTL 1000
  set food-TTL 500

  set human-population round (total-population * (1 - zombie-population-percent / 100))
  set zombie-population round (total-population * (zombie-population-percent / 100))

  set-default-shape turtles "person" ;; default = arrow (other person)
  set-default-shape halos "60cone"

  set h-current-food-count 0
  set time 0
  set damage-to-zombie 0
  set damage-to-human 0
  set healed-zombie 0
  set healed-human 0
  

; ==== TASKS actuators ====
  let tmove task [
    ifelse breed = zombies
      [ move-z ]
      [ move-h ]
  ]
  let trotate task [
     right ?1
  ]
  let teat task [
    ifelse breed = zombies
      [ z-eat ]
      [ h-eat ]
  ]
  let tattack task [
    attack (turtle ?1) ( TTL / 10)
  ]
  let tshoot task [
    attack (turtle ?1) ( TTL / 3)
  ]

; ==== TASKS sensors ====
  let tcount-z task [
    count-z turtle ?1
  ]
  let tcount-h task [
    count-h turtle ?1
  ]
  let tsee task [
    see turtle ?1
  ]
  let tsee-patches task [
    see-patches turtle ?1
  ]

; ==== GBUI setting ====
  gbui:set-settings see-distance see-cone sense-distance zombie-speed human-speed world-width world-height attack-distance shoot-distance base-TTL
  gbui:select-brains human-brain zombie-brain
  gbui:set-sensors tcount-z tcount-h tsee tsee-patches
  gbui:set-actuators tmove trotate tattack tshoot teat
  gbui:set-weights attkG heatG kinfG pckaG shotG avodG fearU saveU stayU


; ==== generate world ====
  setup-h-food
  setup-ammo
  create-humans human-population [ setup-human ]
  create-zombies zombie-population [ setup-zombie ]
  ; ask turtle 1 [ make-halo ] ;;  zvyrazneni cloveka c. 1
  ; ask turtle 99 [ make-halo ] ;;  zvyrazneni zombie c. 99

end

; ===== STEP =====
to runStep
  step
  tick
end

to step
  gbui:tick
  if random 20 = 1 [setup-h-food]
  
  ask humans [
    pickup-ammo
    check-turn-into-zombie
  ]
  gbui:ai-think (turtle-set zombies humans)
  ask (turtle-set zombies humans) [
    gbui:ai-perform
  ]
  decay  
  reap
  set time (time + 1)
end


; ===== ITERATE =====

to itSetup
  clear-all
  
  set base-seed new-seed
  
  set stayU 5 ; 5
  set saveU 0 ; 0
  set fearU 0.9 ; 0.9
  set attkG 0.5 ; 0.5
  set shotG 0.95 ; 0.95
  set kinfG 0.2 ; 0.2
  set heatG 0.5 ; 0.5
  set avodG 0.7 ; 0.7
  set pckaG 0.3 ; 0.3
  
  reset-ticks
end

to iteration
  random-seed seed
  gbui:set-seed seed
  setup
  while [count zombies > 0 and count humans > 0] [
    step
  ]
  set zombies-count-at-end ( zombies-count-at-end + count zombies )
  set humans-count-at-end ( humans-count-at-end + count humans )
  set total-time ( total-time + time )
  set iterations ( iterations + 1 )
  print (word date-and-time " iterations " iterations " zombies avg: " zombies-avg " humans avg: " humans-avg " avg ticks: " time-avg)
end

to iterate
  print (word "seed " base-seed)
  set zombies-count-at-end 0
  set humans-count-at-end 0
  set total-time 0
  set iterations 0
  
  set gvalue (ticks / 20)
  set stayU gvalue
  print (word "goals: " attkG " " heatG " " kinfG " " pckaG " " shotG " " avodG " utilities: " fearU " " saveU " " stayU)
  while [ iterations < 50 ] [
    set seed (base-seed + iterations)
    iteration
  ]
  tick
end

to-report humans-avg
  ifelse iterations = 0 [
    report 0
  ][
    report (humans-count-at-end / iterations)
  ]
end

to-report zombies-avg
  ifelse iterations = 0 [
    report 0
  ][
    report (zombies-count-at-end / iterations)
  ]
end

to-report time-avg
  ifelse iterations = 0 [
    report 0
  ][
    report (total-time / iterations)
  ]
end

; ===== FOOD =====

to setup-h-food
  while [h-current-food-count < h-food-count] [
    ask one-of patches [
      patch-set-h-food 2 
    ]
  ]
end

to patch-set-h-food [ amount ]
  set h-food (h-food + amount)
  set h-current-food-count (h-current-food-count + amount)
  color-patch
end

to-report patch-is-h-food
  report h-food > 0
end

to-report patch-is-z-food
  report z-food > 0
end

to patch-set-z-food [ amount ]
  set z-food (z-food + amount)
  color-patch
end

; ===== EAT =====

; eat human food
to h-eat
  if is-human? self and patch-is-h-food [
    eat
    set h-food (h-food - 1)
    set h-current-food-count (h-current-food-count - 1)
    color-patch
  ]
end

; eat zombie food
to z-eat
  if is-zombie? self and patch-is-z-food [
    eat
    set z-food (z-food - 1)
    color-patch
  ]
end

to eat
  let maxheal base-TTL - TTL
  let toheal food-TTL
  if toheal > maxheal [ set toheal maxheal ]
  set TTL (TTL + toheal)
  ifelse is-zombie? self [
    set healed-zombie (healed-zombie + toheal)
  ][
    set healed-human (healed-human + toheal)
  ]
end

; ===== ATTACK =====

to-report can-attack [ x ]
  let dist attack-distance
  if is-human? self and ammo > 0[
    set dist shoot-distance
  ]
  report attack-enabled and is-turtle? x and (distance x) <= dist
end

; inflict damage equal to the agents TTL, representing its strength
; target = agent being attacked
; damage = damage
to attack [ target damage ]
  if can-attack target [
    ask target [
      set TTL (TTL - damage)
      ifelse is-zombie? self [
        set damage-to-zombie ( damage-to-zombie + damage )
      ][
        set damage-to-human ( damage-to-human + damage )
        try-infect-when-attacking
      ]
    ]
    if distance target > attack-distance [
      set ammo (ammo - 1)
    ]
  ]    
end


; ===== INFECT =====

to-report is-infected
  report infection-timeout >= 0
end

; infect with certain probability with each attack -- remove this honza if you want infect to be an action
; but even then the probability may remain
to try-infect-when-attacking
  if (random 100) < infection-probability [
    infect
  ]
end

; infect target human
to infect
  if is-human? self and not is-infected [
    set infection-timeout 200
  ]
end

to check-turn-into-zombie
  if is-human? self and is-infected [
    ifelse infection-timeout = 0 [
      ;print "Human turned into a zombie!"
      drop-ammo
      set breed zombies
      inform "zombifie"
    ][
      set infection-timeout (infection-timeout - 1)
    ]    
  ]
end

; ===== AMMO =====

to setup-ammo
  ask max-n-of ammo-count patches [random-float 1] [
    set ammo-boxes (ammo-boxes + 5)
    color-patch
  ]
end

to pickup-ammo
  if ammo-boxes > 0 [
    set ammo-boxes (ammo-boxes - 1)
    set ammo (ammo + 1)
    color-patch
  ]
end

to drop-ammo
  if ammo > 0 [
    set ammo-boxes (ammo-boxes + ammo)
    color-patch
  ]
end

; ===== SENSORS =====

to-report count-z [ x ]
   let ret 0
   ask x [ set ret count (zombies in-radius sense-distance) ]
   report ret
end

to-report count-h [ x ] 
   let ret 0
   ask x [ set ret (count humans in-radius sense-distance) ]
   report ret
end

to-report see [ x ]
   let ret 0
   ask x [ set ret ((turtle-set zombies humans) in-cone see-distance see-cone) ]
   report ret
end

to-report see-patches [ x ]
   let ret 0
   ask x [ set ret (patches in-cone see-distance see-cone) ]
   report ret
end

; =====

to setup-human
  setxy random-xcor random-ycor
  set TTL base-TTL
  set infection-timeout -1
  set ammo starting-ammo
  color-TTL 14
end


to setup-zombie
  setxy random-xcor random-ycor
  set TTL base-TTL
  color-TTL 54
end


to move-z
  forward zombie-speed
end

to move-h
  forward human-speed
end

; reap the dead
to reap
  ask zombies [
    ifelse (TTL <= 0) [
      inform "die"
      ;patch-set-z-food 1
      die
    ][
      color-TTL 54
    ]
  ]
  ask humans [
    ifelse (TTL <= 0) [      
      inform "die"
      patch-set-z-food 2
      drop-ammo
      die
    ][
      ifelse is-infected [
        color-TTL 44
      ][
        color-TTL 14
      ]
    ]
  ]
end

; color agent accorging to the TTL
; colors:
; a 100% health
to color-TTL [ a ]
  set color a + (4 - 4 * TTL / base-TTL)
end

to decay
  ask zombies [ set TTL (TTL - 1) ]
end

to inform [ info ]
  let rx xcor
  let ry ycor
  let dying who
  ask ( turtle-set zombies humans ) in-radius see-distance [
    if who != dying [
      let absangl atan (rx - xcor) (ry - ycor)
      let relangl abs subtract-headings heading absangl
      if relangl < see-cone [
        gbui:inform info turtle dying
      ]
    ]
  ]
end

to color-patch
  let jidla (h-food + z-food)
  ifelse jidla = 0 [
    ifelse ammo-boxes > 0 [
      set pcolor 6
    ][
      set pcolor black
    ]
  ][ ; h-food and/or z-food > 0
    let base 0
    ifelse h-food > 0 and z-food > 0 [
      set base 110
    ][ ifelse h-food > 0 [
      set base 120
    ][ 
      set base 100
    ]]
    let posun 10 - ((7 * jidla + 20) / (3 * jidla))
    set pcolor base + posun
  ]
end

to make-halo  ;; runner procedure
  ;; when you use HATCH, the new turtle inherits the
  ;; characteristics of the parent.  so the halo will
  ;; be the same color as the turtle it encircles (unless
  ;; you add code to change it
  hatch-halos 1
  [ set size (see-distance * 2)
    ;; Use an RGB color to make halo three fourths transparent
    set color lput 64 extract-rgb color
    ;; set thickness of halo to half a patch
    __set-line-thickness 0.1
    ;; We create an invisible directed link from the runner
    ;; to the halo.  Using tie means that whenever the
    ;; runner moves, the halo moves with it.
    create-link-from myself
    [ tie
      hide-link ] ]
end
@#$#@#$#@
GRAPHICS-WINDOW
385
20
1275
591
-1
-1
20.0
1
10
1
1
1
0
1
1
1
0
43
0
26
1
1
1
ticks
30.0

BUTTON
40
20
114
53
Setup
runSetup
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

SLIDER
40
65
375
98
total-population
total-population
0
100
100
1
1
NIL
HORIZONTAL

SLIDER
40
105
375
138
zombie-population-percent
zombie-population-percent
0
100
50
1
1
% zombie/human
HORIZONTAL

BUTTON
125
20
190
53
Step
runStep
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

SLIDER
40
165
185
198
zombie-speed
zombie-speed
0.01
1
0.1
0.01
1
NIL
HORIZONTAL

SLIDER
40
205
185
238
human-speed
human-speed
0.01
1
0.1
0.01
1
NIL
HORIZONTAL

BUTTON
200
20
263
53
Run
runStep
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

PLOT
385
600
825
800
zombies / humans
Goal value
count
0.0
1.0
0.0
10.0
true
false
"" ""
PENS
"pen-2" 1.0 0 -2674135 true "" "plotxy gvalue humans-avg"
"pen-3" 1.0 0 -13345367 true "" "plotxy gvalue zombies-avg"

SWITCH
230
165
375
198
attack-enabled
attack-enabled
0
1
-1000

SLIDER
40
250
185
283
see-distance
see-distance
1
20
6
0.5
1
NIL
HORIZONTAL

SLIDER
40
285
185
318
sense-distance
sense-distance
1
20
3
0.5
1
NIL
HORIZONTAL

SLIDER
230
205
375
238
attack-distance
attack-distance
0
10
1
0.1
1
NIL
HORIZONTAL

SLIDER
230
285
375
318
h-food-count
h-food-count
0
100
5
1
1
NIL
HORIZONTAL

SLIDER
230
365
375
398
infection-probability
infection-probability
0
100
3
1
1
%
HORIZONTAL

CHOOSER
40
325
185
370
human-brain
human-brain
"BasicBrain" "MemoryBrain" "GoalBrain"
2

CHOOSER
40
375
185
420
zombie-brain
zombie-brain
"BasicBrain" "ChaseBrain" "GoalBrain"
2

SLIDER
230
245
375
278
shoot-distance
shoot-distance
0
20
3
0.1
1
NIL
HORIZONTAL

SLIDER
230
325
375
358
ammo-count
ammo-count
0
50
5
1
1
NIL
HORIZONTAL

SLIDER
230
405
375
438
starting-ammo
starting-ammo
0
20
3
1
1
NIL
HORIZONTAL

BUTTON
20
520
97
553
NIL
iterate
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

PLOT
835
600
1275
800
time
Goal value
ticks
0.0
1.0
0.0
10.0
true
false
"" ""
PENS
"pen-1" 1.0 0 -13791810 true "" "plotxy gvalue time-avg"

BUTTON
20
485
195
518
NIL
itSetup
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
100
520
222
553
iterate 0-1
while [gvalue < 1] [iterate]
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
275
20
347
53
RunAll
runSetup\nwhile [count zombies > 0 and count humans > 0] [\n    runStep\n]\nprint random 2000
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
225
520
317
553
NIL
iteration
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

@#$#@#$#@
## WHAT IS IT?

(a general understanding of what the model is trying to show or explain)

## HOW IT WORKS

(what rules the agents use to create the overall behavior of the model)

## HOW TO USE IT

(how to use the model, including a description of each of the items in the Interface tab)

## THINGS TO NOTICE

(suggested things for the user to notice while running the model)

## THINGS TO TRY

(suggested things for the user to try to do (move sliders, switches, etc.) with the model)

## EXTENDING THE MODEL

(suggested things to add or change in the Code tab to make the model more complicated, detailed, accurate, etc.)

## NETLOGO FEATURES

(interesting or unusual features of NetLogo that the model uses, particularly in the Code tab; or where workarounds were needed for missing features)

## RELATED MODELS

(models in the NetLogo Models Library and elsewhere which are of related interest)

## CREDITS AND REFERENCES

(a reference to the model's URL on the web if it has one, as well as any other necessary credits, citations, and links)
@#$#@#$#@
default
true
0
Polygon -7500403 true true 150 5 40 250 150 205 260 250

60cone
true
0
Circle -7500403 false true 0 0 300
Line -7500403 true 150 150 75 20
Line -7500403 true 150 150 225 20
Circle -7500403 false true 75 75 150

airplane
true
0
Polygon -7500403 true true 150 0 135 15 120 60 120 105 15 165 15 195 120 180 135 240 105 270 120 285 150 270 180 285 210 270 165 240 180 180 285 195 285 165 180 105 180 60 165 15

arrow
true
0
Polygon -7500403 true true 150 0 0 150 105 150 105 293 195 293 195 150 300 150

box
false
0
Polygon -7500403 true true 150 285 285 225 285 75 150 135
Polygon -7500403 true true 150 135 15 75 150 15 285 75
Polygon -7500403 true true 15 75 15 225 150 285 150 135
Line -16777216 false 150 285 150 135
Line -16777216 false 150 135 15 75
Line -16777216 false 150 135 285 75

bug
true
0
Circle -7500403 true true 96 182 108
Circle -7500403 true true 110 127 80
Circle -7500403 true true 110 75 80
Line -7500403 true 150 100 80 30
Line -7500403 true 150 100 220 30

butterfly
true
0
Polygon -7500403 true true 150 165 209 199 225 225 225 255 195 270 165 255 150 240
Polygon -7500403 true true 150 165 89 198 75 225 75 255 105 270 135 255 150 240
Polygon -7500403 true true 139 148 100 105 55 90 25 90 10 105 10 135 25 180 40 195 85 194 139 163
Polygon -7500403 true true 162 150 200 105 245 90 275 90 290 105 290 135 275 180 260 195 215 195 162 165
Polygon -16777216 true false 150 255 135 225 120 150 135 120 150 105 165 120 180 150 165 225
Circle -16777216 true false 135 90 30
Line -16777216 false 150 105 195 60
Line -16777216 false 150 105 105 60

car
false
0
Polygon -7500403 true true 300 180 279 164 261 144 240 135 226 132 213 106 203 84 185 63 159 50 135 50 75 60 0 150 0 165 0 225 300 225 300 180
Circle -16777216 true false 180 180 90
Circle -16777216 true false 30 180 90
Polygon -16777216 true false 162 80 132 78 134 135 209 135 194 105 189 96 180 89
Circle -7500403 true true 47 195 58
Circle -7500403 true true 195 195 58

circle
false
0
Circle -7500403 true true 0 0 300

circle 2
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240

cow
false
0
Polygon -7500403 true true 200 193 197 249 179 249 177 196 166 187 140 189 93 191 78 179 72 211 49 209 48 181 37 149 25 120 25 89 45 72 103 84 179 75 198 76 252 64 272 81 293 103 285 121 255 121 242 118 224 167
Polygon -7500403 true true 73 210 86 251 62 249 48 208
Polygon -7500403 true true 25 114 16 195 9 204 23 213 25 200 39 123

cylinder
false
0
Circle -7500403 true true 0 0 300

dot
false
0
Circle -7500403 true true 90 90 120

face happy
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 255 90 239 62 213 47 191 67 179 90 203 109 218 150 225 192 218 210 203 227 181 251 194 236 217 212 240

face neutral
false
0
Circle -7500403 true true 8 7 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Rectangle -16777216 true false 60 195 240 225

face sad
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 168 90 184 62 210 47 232 67 244 90 220 109 205 150 198 192 205 210 220 227 242 251 229 236 206 212 183

fish
false
0
Polygon -1 true false 44 131 21 87 15 86 0 120 15 150 0 180 13 214 20 212 45 166
Polygon -1 true false 135 195 119 235 95 218 76 210 46 204 60 165
Polygon -1 true false 75 45 83 77 71 103 86 114 166 78 135 60
Polygon -7500403 true true 30 136 151 77 226 81 280 119 292 146 292 160 287 170 270 195 195 210 151 212 30 166
Circle -16777216 true false 215 106 30

flag
false
0
Rectangle -7500403 true true 60 15 75 300
Polygon -7500403 true true 90 150 270 90 90 30
Line -7500403 true 75 135 90 135
Line -7500403 true 75 45 90 45

flower
false
0
Polygon -10899396 true false 135 120 165 165 180 210 180 240 150 300 165 300 195 240 195 195 165 135
Circle -7500403 true true 85 132 38
Circle -7500403 true true 130 147 38
Circle -7500403 true true 192 85 38
Circle -7500403 true true 85 40 38
Circle -7500403 true true 177 40 38
Circle -7500403 true true 177 132 38
Circle -7500403 true true 70 85 38
Circle -7500403 true true 130 25 38
Circle -7500403 true true 96 51 108
Circle -16777216 true false 113 68 74
Polygon -10899396 true false 189 233 219 188 249 173 279 188 234 218
Polygon -10899396 true false 180 255 150 210 105 210 75 240 135 240

house
false
0
Rectangle -7500403 true true 45 120 255 285
Rectangle -16777216 true false 120 210 180 285
Polygon -7500403 true true 15 120 150 15 285 120
Line -16777216 false 30 120 270 120

leaf
false
0
Polygon -7500403 true true 150 210 135 195 120 210 60 210 30 195 60 180 60 165 15 135 30 120 15 105 40 104 45 90 60 90 90 105 105 120 120 120 105 60 120 60 135 30 150 15 165 30 180 60 195 60 180 120 195 120 210 105 240 90 255 90 263 104 285 105 270 120 285 135 240 165 240 180 270 195 240 210 180 210 165 195
Polygon -7500403 true true 135 195 135 240 120 255 105 255 105 285 135 285 165 240 165 195

line
true
0
Line -7500403 true 150 0 150 300

line half
true
0
Line -7500403 true 150 0 150 150

pentagon
false
0
Polygon -7500403 true true 150 15 15 120 60 285 240 285 285 120

person
false
0
Circle -7500403 true true 110 5 80
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 195 90 240 150 225 180 165 105
Polygon -7500403 true true 105 90 60 150 75 180 135 105

plant
false
0
Rectangle -7500403 true true 135 90 165 300
Polygon -7500403 true true 135 255 90 210 45 195 75 255 135 285
Polygon -7500403 true true 165 255 210 210 255 195 225 255 165 285
Polygon -7500403 true true 135 180 90 135 45 120 75 180 135 210
Polygon -7500403 true true 165 180 165 210 225 180 255 120 210 135
Polygon -7500403 true true 135 105 90 60 45 45 75 105 135 135
Polygon -7500403 true true 165 105 165 135 225 105 255 45 210 60
Polygon -7500403 true true 135 90 120 45 150 15 180 45 165 90

sheep
false
15
Circle -1 true true 203 65 88
Circle -1 true true 70 65 162
Circle -1 true true 150 105 120
Polygon -7500403 true false 218 120 240 165 255 165 278 120
Circle -7500403 true false 214 72 67
Rectangle -1 true true 164 223 179 298
Polygon -1 true true 45 285 30 285 30 240 15 195 45 210
Circle -1 true true 3 83 150
Rectangle -1 true true 65 221 80 296
Polygon -1 true true 195 285 210 285 210 240 240 210 195 210
Polygon -7500403 true false 276 85 285 105 302 99 294 83
Polygon -7500403 true false 219 85 210 105 193 99 201 83

square
false
0
Rectangle -7500403 true true 30 30 270 270

square 2
false
0
Rectangle -7500403 true true 30 30 270 270
Rectangle -16777216 true false 60 60 240 240

star
false
0
Polygon -7500403 true true 151 1 185 108 298 108 207 175 242 282 151 216 59 282 94 175 3 108 116 108

target
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240
Circle -7500403 true true 60 60 180
Circle -16777216 true false 90 90 120
Circle -7500403 true true 120 120 60

thin ring
true
0
Circle -7500403 false true -2 -2 302

tree
false
0
Circle -7500403 true true 118 3 94
Rectangle -6459832 true false 120 195 180 300
Circle -7500403 true true 65 21 108
Circle -7500403 true true 116 41 127
Circle -7500403 true true 45 90 120
Circle -7500403 true true 104 74 152

triangle
false
0
Polygon -7500403 true true 150 30 15 255 285 255

triangle 2
false
0
Polygon -7500403 true true 150 30 15 255 285 255
Polygon -16777216 true false 151 99 225 223 75 224

truck
false
0
Rectangle -7500403 true true 4 45 195 187
Polygon -7500403 true true 296 193 296 150 259 134 244 104 208 104 207 194
Rectangle -1 true false 195 60 195 105
Polygon -16777216 true false 238 112 252 141 219 141 218 112
Circle -16777216 true false 234 174 42
Rectangle -7500403 true true 181 185 214 194
Circle -16777216 true false 144 174 42
Circle -16777216 true false 24 174 42
Circle -7500403 false true 24 174 42
Circle -7500403 false true 144 174 42
Circle -7500403 false true 234 174 42

turtle
true
0
Polygon -10899396 true false 215 204 240 233 246 254 228 266 215 252 193 210
Polygon -10899396 true false 195 90 225 75 245 75 260 89 269 108 261 124 240 105 225 105 210 105
Polygon -10899396 true false 105 90 75 75 55 75 40 89 31 108 39 124 60 105 75 105 90 105
Polygon -10899396 true false 132 85 134 64 107 51 108 17 150 2 192 18 192 52 169 65 172 87
Polygon -10899396 true false 85 204 60 233 54 254 72 266 85 252 107 210
Polygon -7500403 true true 119 75 179 75 209 101 224 135 220 225 175 261 128 261 81 224 74 135 88 99

wheel
false
0
Circle -7500403 true true 3 3 294
Circle -16777216 true false 30 30 240
Line -7500403 true 150 285 150 15
Line -7500403 true 15 150 285 150
Circle -7500403 true true 120 120 60
Line -7500403 true 216 40 79 269
Line -7500403 true 40 84 269 221
Line -7500403 true 40 216 269 79
Line -7500403 true 84 40 221 269

wolf
false
0
Polygon -16777216 true false 253 133 245 131 245 133
Polygon -7500403 true true 2 194 13 197 30 191 38 193 38 205 20 226 20 257 27 265 38 266 40 260 31 253 31 230 60 206 68 198 75 209 66 228 65 243 82 261 84 268 100 267 103 261 77 239 79 231 100 207 98 196 119 201 143 202 160 195 166 210 172 213 173 238 167 251 160 248 154 265 169 264 178 247 186 240 198 260 200 271 217 271 219 262 207 258 195 230 192 198 210 184 227 164 242 144 259 145 284 151 277 141 293 140 299 134 297 127 273 119 270 105
Polygon -7500403 true true -1 195 14 180 36 166 40 153 53 140 82 131 134 133 159 126 188 115 227 108 236 102 238 98 268 86 269 92 281 87 269 103 269 113

x
false
0
Polygon -7500403 true true 270 75 225 30 30 225 75 270
Polygon -7500403 true true 30 75 75 30 270 225 225 270

@#$#@#$#@
NetLogo 5.0.5
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
default
0.0
-0.2 0 0.0 1.0
0.0 1 1.0 0.0
0.2 0 0.0 1.0
link direction
true
0
Line -7500403 true 150 150 90 180
Line -7500403 true 150 150 210 180

@#$#@#$#@
1
@#$#@#$#@
