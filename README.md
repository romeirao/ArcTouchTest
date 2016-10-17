# ArcTouch Test #

This app consists in a simple example of routes list. It was thought to be very clean but useful to the final user.

### LIBRARIES ###

#### GOOGLE APIs ####

These libraries were used because I needed to use material design in older Android versions.

They were: AppCompat, Design, RecyclerView, Play Services Location and Maps.

#### ANDROID ANNOTATIONS ####

This library was used because I wanted to focus on business logic, not in boilerplate code.

#### GSON ####

This library was used because it handles the data from and to json format.

#### SQUARE LIBRARIES ####

These libraries were used because to get data from web is a task that needs to be well done. Handling network connections, caching data, complex http communication are embedded in these libs.

OkHttp: this lib is responsible for handling http connections. Through it we can get data from network in a easy way.
Retrofit: this lib is responsible for the REST communication used between the app and the server.

#### EVENT BUS ####

I used this lib to promote a low coupling among the objects in scene.

### How is the code working? ###

Basically, I have three views namely:

1 ListActivity

This view has a RecyclerView to handle the route list.

2 DetailActivity

This view shows the details of a specific route - stops and schedules.

3 MapActivity

This view permits to select a route just clicking over a map.

* * *

Inside the code, the better thing to explain more in details is the architecture used in this project. I decided to use MVP - Model, View, Presenter - to improve my tests and to remove the business flow of the data from the Activities/Fragments. I have a main classe called BasePresenter where I can handle the errors coming from the server in a handly way.

Any doubts can be asked to me at any time.

### More? ###

If I'd more time, some things I'd like to do are:

1. More Tests - It is mandatory to all kind of app to maintain its quality.

2. Design - I'd think about a better design.

3. Improve this documentation - It is always good to have a better documentation.
