# CSC207-Project-Group-262
## Problem Domain: HR System
Our Team is currently looking to develop a software that alleviates the hassle of maintaining Human Resource system within the administrative body of the University of Toronto.

## Description of Application
- Access to a calendar which shows which roles are missing from lectures and labs.
- Request for leave button that notifies others with the same role (Prof, TA) and asks if there is anyone willing to fill for them by sending it to an announcements page.
- A feature which allows the user to see whether a particular day is a public holiday, for which leaves would not be necessary.

## Link to API Documentation used
- https://calendarific.com/api-documentation

## Screen shot of tool for trying API

Screenshot #1: Public Holiday API Call
https://i.postimg.cc/zBJ4bL6q/Screenshot-2023-09-29-at-7-52-06-PM.png

Screenshot #2: Email Verification API
![Screen Shot 2023-09-29 at 8 11 33 PM](https://github.com/xubosen/CSC207-Project-Group-262/assets/97214246/f92e0c99-6d98-4711-9bbf-9896d56865cc)


## Example of running our code
`joe.requestLeave('10/13/2023')`

Email Verification API: https://github.com/xubosen/CSC207-Project-Group-262/blob/main/apiDocuments/emailValidationAPI.java

## Minimal Example of Calling Calendarific API from Java
See ""src/CalendarificAPIAccessExample.java""

## List of Technical Problems
- Calendarify API only allows 500 API calls per month. Our team does not foresee this being a problem, but it is something to keep in mind.
