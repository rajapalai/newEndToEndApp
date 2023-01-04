Authentication API
==================
![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_5.png](img_5.png)
![img_6.png](img_6.png)
![img_7.png](img_7.png)
![img_8.png](img_8.png)
![img_14.png](img_14.png)
![img_15.png](img_15.png)

App API with authentication (ROLE_USER or ROLE_ADMIN credential mandatory)
==========================================================================
![img_9.png](img_9.png)
![img_10.png](img_10.png)
![img_11.png](img_11.png)
![img_12.png](img_12.png)
![img_13.png](img_13.png)

Generate JWT Authentication
===========================
![img_19.png](img_19.png)

SWAGGER (OPEN API)
========
http://localhost:9090/swagger-ui/index.html
-------------------------------------------------
![img_18.png](img_18.png)


POST
====
http://localhost:9090/employee-service

body => {
"employeeFirstName": "string",
"employeeLastName": "string",
"employeeSalary": 300000,
"email": "string",
"contactNumber": "1409115369",
"designation": "string"
}
----------------------------------------

GET
===
http://localhost:9090/employee-service

------------------------------------

GET BY ID
==========
http://localhost:9090/employee-service/search/path/{employeeId}

---------------------------------------------------------------------

GET BY ID REQUEST PARAM
==========================
http://localhost:9090/employee-service/search/request?employeeId=1

----------------------------------------------------------------------

GET DATA BY DESIGNATION
========================
http://localhost:9090/employee-service/designationTypes

---------------------------------------------------------

DELETE DATA
==============

