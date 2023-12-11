# CSC207-Project-Group-262
## Problem Domain: HR System
Our Team is currently looking to develop a software that alleviates the hassle of maintaining Human Resource system within the administrative body of the University of Toronto.

## Description of Application
- Ability to create courses, Events (Lectures, Tutorials) and Sessions and add Instructors and TAs.
- Ability to view which courses/events/sessions they are a part of.
- Ability to remove other employees from any instance of course, event and/or sessions.

## Link to API Documentation used
- https://www.mongodb.com/docs/atlas/

## Screen shot of tool for trying API
Screenshot #1: MongoDB API
[![access-To-Mongo-DB.png](https://i.postimg.cc/cHk2R54Q/access-To-Mongo-DB.png)](https://postimg.cc/47tWZPcd)

## API Key
- employee file : (mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority,hrsystem_database,employees)
- courses file : (mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority,hrsystem_database,courses)
- events file : (mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority,hrsystem_database,event)
- sessions file : (mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority,hrsystem_database,sessions)
- 
## List of Technical Problems
- Max amount allowed for data storage in mongo db is 512MB but it's not a major concern because it only takes up 100KB with the current amount of data stored.
