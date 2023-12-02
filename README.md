# CSC207-Project-Group-262
## Problem Domain: HR System
Our Team is currently looking to develop a software that alleviates the hassle of maintaining Human Resource system within the administrative body of the University of Toronto.

## Description of Application
- Ability to create courses, Events (Lectures, Tutorials) and Sessions and add Instructors and TAs.
- Request for leave button that notifies others with the same role (Prof, TA) and asks if there is anyone willing to fill for them by sending it to an announcements page.
- A feature which allows the user to see whether a particular day is a public holiday, for which leaves would not be necessary.

## Link to API Documentation used
- https://www.mongodb.com/docs/atlas/

## Screen shot of tool for trying API

Screenshot #1: Public Holiday API Call
https://i.postimg.cc/zBJ4bL6q/Screenshot-2023-09-29-at-7-52-06-PM.png

Screenshot #2: Email Verification API
![Screen Shot 2023-09-29 at 8 11 33 PM](https://github.com/xubosen/CSC207-Project-Group-262/assets/97214246/f92e0c99-6d98-4711-9bbf-9896d56865cc)

Screenshot #1: MongoDB API
[![access-To-Mongo-DB.png](https://i.postimg.cc/cHk2R54Q/access-To-Mongo-DB.png)](https://postimg.cc/47tWZPcd)

## Minimal Example of Calling APIs from Java
Email Verification API: [Email Verification API](https://github.com/xubosen/CSC207-Project-Group-262/blob/main/src/apiDocuments/emailValidationAPI.java)

Holiday API: [src/CalendarificAPIAccessExample.java](https://github.com/xubosen/CSC207-Project-Group-262/blob/0caa7469006427fdb4fb71c89daffad34a697c88/src/CalendarificAPIAccessExample.java)

## Example ouput of Running Java Program


## List of Technical Problems
- Max amount allowed for data storage in mongo db is 512MB but it's not a major concern because it only takes up 100KB with the current amount of data stored.
