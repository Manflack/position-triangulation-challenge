To run application: **mvn spring-boot:run**

By default it's run in **localhost:8080** with **local** profile

`It's required Java 1.8 version.`

Configuration
---------
No properties defined

Endpoints
---------
The swagger's definition is in `/swagger-ui.html`, for example: `http://localhost:8080/swagger-ui.html`

All services response errors with this structure:
```json
{
    "timestamp": "2020-10-21T00:18:22.110546",
    "status": "[HTTP STATUS CODE]",
    "code": "[ERROR CODE]",
    "message": "[ERROR MESSAGE]",
    "debugMessage": "[DEBUG MESSAGE]"
}
```

# Architecture of the project

![Architecture](src/main/resources/details/Architecture.svg)

- RestHandlerException layer
- Logger layer
- Controller layer
- Service layer
- Guava Cache
	- After 1 minute all entries received in path /topsecret_split will be deleted.
- Unit tests Jacoco + Junit
	- Models objects
	- Api objects
	- Controller tests
---

# Technical overview details about the implementation logic

Given two circles and his radius (provided by API, understanding a circle as an Satellite with Radius Zn),
It's computed the intersections points of that circles. Understanding a intersection between circles when
choke in almost one point in common in Cartesian plane.

Considering this case, the "Operacion fuego Quasar" can be saw as the Intersection between Circle's A, B and C, if exists.
If A and B have intersection's, that intersections it's checked between C. If coincide the distance between that point and
provided C Radii, then we got a solution.

# The process of solution develop

At the beginning, the problem was consider like a Intersection of three circles.
The first thing I though was construct the solution given two circles, in order to get the two possibles solutions.

If I want to calculate the **m** point, I can consider trigonometry to compute by angle, consider the follow proposition:

![Solution1](src/main/resources/details/Solution2.svg)

We can compute **β** because we know the static points (centers) of Circle **A** and Circle **B**.
And we can get **α** since we know the distance between point A and B, and the r1 and r2, that triangle it's a scalene triangle.
With this data, we can compute **Φ**, if the sum of **Φ + α + β = 90°**, exchange as **Φ = 90° - α - β**
Now, we know **Φ** and **r1**. Using trigonometry we can built the equation like:
**senΦ * r1 = x3** and **cosΦ * r1 = y3**

I will worked with this solution, after a few considers test cases I will discarded it, because it's required consider too variables to work a full solution.


The second one
---

![Solution2](src/main/resources/details/Solution1.svg)

Considering a triangle **A, P, m** and  **B, m, P**, we know that **h = sqrt{d^2+r^2}**.
My question was, how to get **d**? I know **d** (distance) between **A** and **B**, but not d1 or d2. Only know that **d = d1 + d2**.

At this point, was required a research about circle intersections to get a full solution.

I got that **d1 = (r1^2 - r2^2 + d^2 ) / (2 d)**.
If we know **d1**, now i can compute **h; h^2 = r1^2 - d1^2**
Using lineal functions, we got that:

**x3 = x2 +- h ( y1 - y0 ) / d**
**y3 = y2 -+ h ( x1 - x0 ) / d**

References:
- Intersection of two circles by Paul Bourke.
- Circle-Circle-Intersection by Lanchon

Comments:
- Drawio diagrams
