# Introduction
COSC345 is a software engineering paper in which students form teams and develop an application based on a given theme. The given theme for us this year was diversity, with the task being to create an Android or iOS app based on it. When we think of diversity, we think of the difference from day-to-day; the difference between other people’s routines, moods, habits and experiences. We decided we want to create an app which encapsulates this and tracks it, and then can use this information to improve the day-to-day life of the user through simple things such as reminding them to drink water, to helping them improve their sleep patterns.

We plan to implement many different types of data that the user can input to later analyse, such as:
* Daily mood
* Daily sleep
* Water intake
* Exercise (in the form of steps)
* A diary function

Our application will include non-intrusive push notifications that, assuming the user opts in, can remind the user to do certain things at their benefit - such as drinking more water if their daily water intake is low,  getting out for some exercise if their step count is low, or even reminding them to have an early sleep on a week with not much sleep.

Our app will relate to the theme of diversity by collecting a diverse range of data from the user to regulate their well being and mental health. This is also one way it differs from other similar apps on the market: it encapsulates a wide variety of metrics. It is not targeted to any specific demographic either: anyone can use it; young, old, tech savvy, non tech savvy, people of all race and origin, any gender. Diversity appears in several aspects of our app, which is why we have chosen to go ahead with this idea. 

We plan to develop this application using Android Studio and Kotlin. We decided to use Android as our target platform because the Play Store submittance method tends to be much easier and take much less time, aside from the fact that Kotlin uses Java libraries of which we are already familiar with. Android is also a much wider-used platform than iOS worldwide, which also contributed to our decision.

We have three members on our development team, so we’d better introduce ourselves!

# Our Team:
### Damien:
Hi, my name is Damien, and I’m a 3rd-year student at the University of Otago where I’m studying a Bachelor of Science majoring in Computer Science. I’ve always been interested in computer science, specifically in computer programming. As young as primary school I can remember creating basic programs and scripts for Android so while I have no formal experience in Android app development, I feel this experience will aid in our group project. I’d like to think I am fairly organised and good at working in groups, which will obviously be fairly important. I’ve always wanted to design a mobile app, so I’m excited for this project!

### Oscar:
Hello, I’m Oscar. I am studying a Bachelor of Science, majoring in Computer Science at the University of Otago. I’m in my third and final year and have enjoyed my time here so far. I have been interested in computers and how they function from a young age. I enjoy low level computing, being able to see exactly what a computer does at the bare metal level interests me. I also enjoy tinkering with raspberry pi’s, having set up Pi-hole at my current flat. I feel I can bring some lower level knowledge to the group, along with embedded system experience. I like group work, and feel I can be a good team member. I have never developed an app for IOS or Android before, and am looking forward to the challenge!

### Marikit:
Hi, I’m Marikit, and I’m in my final year of my Bachelor of Science, majoring in Software Engineering with a minor in Computer Science. I’ve taken some previous INFO papers which brings a bit of diversity and different experience to the group, as INFO310 was very similar and involved group work to develop a website using Agile principles and teamwork skills.  I’ve enjoyed programming and teamwork so far throughout my time at uni and although I’ve never used Kotlin or developed a mobile app before, so I’m excited to be able to add this to my repertoire by the end of this course!

# Planning
We have created a simple plan of how we plan to develop our application in the form of a gantt chart using Trello. A screenshot of our plan is included below:
![Project plan](https://github.com/damienmiller51/cosc345project/blob/main/chart.png?raw=true)

# Risk Analysis
| Risk                                                                                                       | Likelihood | Impact | Risk value of exposure (likelihood * impact) | Mitigation                                                                                                                                                                                    |
|------------------------------------------------------------------------------------------------------------|------------|--------|----------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Team member gets sick or COVID                                                                             | 8          | 3      | 24                                           | Work online, not a huge risk impact as there are plenty of options for collaborating online                                                                                                   |
| Task takes longer to complete than expected                                                                | 7          | 7      | 49                                           | Omit low priority tasks to ensure high priority ones are finished first, or other team members can provide help and support to try and complete the task faster                               |
| Changes to requirements adds delay                                                                         | 6          | 7      | 42                                           | As requirements will likely change as we move along with the project.                                                                                                                         |
| Team members not experienced with new software/languages                                                   | 8          | 4      | 32                                           | We will have to teach ourselves Kotlin from scratch as none of us have used it before, but as we all have programming experience in other languages it should not be too difficult to pick up |
| Availability/timetable clashes between team members making it difficult to collaborate and discuss project | 6          | 3      | 18                                           | Discord calls when someone can’t make it to meet in person, and arranging alternative times to meet/talk                                                                                      |
| Overestimating team members’ abilities                                                                     | 4          | 4      | 16                                           | Adjust tasks accordingly, by either removing inessential ones or assigning them to someone else who may be better suited for the task                                                         |

# Market Info:
There are many other apps on the Apple App Store and Google Play Store that relate to wellbeing and mental health, such as:

* [Reflectly](https://play.google.com/store/apps/details?id=com.reflectlyApp)
* [Daylio](https://play.google.com/store/apps/details?id=net.daylio)
* [Moodflow](https://play.google.com/store/apps/details?id=com.moodpixel)
* [Pixels](https://play.google.com/store/apps/details?id=ar.teovogel.yip)
* [Symptom & Mood Tracker](https://play.google.com/store/apps/details?id=com.bearable)

We will differ from most of the apps above by not only tracking mood/mental health. We will track other features (as mentioned in the introduction) that could be affecting mood. Users will be able to discover data trends that were previously undiscovered. Using these trends they can make changes to their lifestyle that will positively benefit them. We will also differ from these apps by being completely transparent with their data. Because of the potentially sensitive information users could disclose, we will not store or sell any user data. Ideally we would store the data locally (and hopefully encrypted).

The target market for ‘health & wellbeing’ is large, for the sake of our current ethics approval we will only be targeting people eighteen and older. An estimated 11% of the global population are living with mental health conditions. This along with COVID-19’s observed negative effect on the general population’s mental health increases our target market to over a possible 800 million people. An app such as ours provides people who cannot or would not seek traditional care with methods that can improve their daily life. We can see from the graph below that the demand for these apps are strongly trending upwards. We partially attribute this to the points discussed above, and can therefore say that there is a demand for our app.
