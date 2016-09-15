metab scalajs play version
====================

ScalaJS version of small react app I wrote with a Play backend. 

Running
=======
sbt run...then navigate to localhost:9000 
 
 
Differences from [ScalaJSReact](https://github.com/bskern/metab_scalajs) version
=====================================
By adding a Play backend and sharing domain objects between the two I was able to simplify the front end.
Most of the processing logic is in the backend and the data is cached to help performance. The react components
only talk to the server and know the result their getting but they lost their knowledge of all the data processing 
and manipulation. 

Technologies
============
* [ScalaJS](https://github.com/scala-js/scala-js)
* [Scalajs-react](https://github.com/japgolly/scalajs-react)
* [ScalaCSS](https://github.com/japgolly/scalacss)
* [uPickle](https://github.com/lihaoyi/upickle-pprint)
* [sbt-web-scalajs](https://github.com/vmunier/sbt-web-scalajs)
* [scalajs-scripts](https://github.com/vmunier/scalajs-scripts)
* [Play](https://github.com/playframework/playframework)

No routing or redux like technologies for now everything is locally managed in state

App pulls in my local weather, subreddits I am interested in, and HN data into one easy to read page  on desktop and mobile

![Imgur](http://i.imgur.com/4vi4hjA.png)

Deployed [here](goo.gl/zOh68L)