Online-Restaurant-Management
=======

REST API to manage restaurant with online orders and also inventory management.

## Problem Statement
				
● Surabi is a chain of restaurants Now wants to come online.
	
 ### Use Cases:
	
#### Users
- As a user I should be able to log in and log out and register in the application.
- As a user I should be able to see all the items available along with the price.
- As a user I should be able to select the item I want to order.
- As a user I should be able to order n number of plates per item,
- As a user I should be able to order more than one item.
- As a user I should be able to see my final bill on the screen.
- As a user I should be able to my cart and my order History

#### Admin
- As an admin I should be able to login and logout in the application
- As an admin I should be able to perform CRUD on Users
- As an admin I should be able to see all the bills getting generated today.
- As an admin I should be able to see the total sale from this month.
- As an admin I should be able to see all the orders done by a specific user.
- As an admin I should be able to perform CRUD on menu and menu items.

## Requirements

● Java 17

● MySQL

● Eclipse IDE/Spring ToolSuite

## Execution Instruction:
				

● Configure the application.properties file and change the Database configuration according to your MySQL workbench.
	
● Create a Database in the workbench as "surabhiRestaurant_db".
	
● Change the username and password accordingy:
	
	server.port=90
	spring.datasource.url=jdbc:mysql://localhost:3306/surabhiRestaurant_db?createIfNotExists=true
	
	#Change the Username
	spring.datasource.username="your username" 
		
	#Change the Password
	spring.datasource.password="your password"
	
● Run the Application.java file in the src/main/java --> com.hcl package

● After Starting the application open the database "surabhiRestaurant_db" and insert the "admin" manually using the following query.
		
   INSERT INTO user(email_id,password,type,user_name) VALUES ('Any Random Email Id', 'Your Random Password', 'admin', 'Your Name');
	
● After this If you want you can add some items manually in the database as:
			
		insert into item(price,item_name) values(50,'cdjcdc');
		insert into item(price,item_name) values(20,'vghfhvfv');
		insert into item(price,item_name) values(110,'sjhdc');
		insert into item(price,item_name) values(70,'ccjhdcj');
		insert into item(price,item_name) values(30,'hjxhjs');
		
	  Else you can also add items using the Item Controller in the Swagger UI after login as Admin.
	
	
● After the application is started --> Go to your browser and Enter the following url:
			
			"http://localhost:90/swagger-ui.html"
	
	
	
	
● The Swagger UI page will display the different Controllers and The Model objects.I have used Session Management to authorize access to the different URLs as per the    role:admin/user;  
	
● For Admin options Test the following URLs:
	
		o User Controller
				POST /user/login userLogin
				GET /user/logout userLogout
				PUT /user/register addUser
				GET /user/{userId} getUserDetails
				GET /user/allUsers getAllUsers
				DELETE /user/delete/{userId} deleteUserDetails
				GET /user/update updateUserDetails
				
		o Bill Controller
				GET /bill/todaysBills todaysBills
				GET /bill/totalMonthlySale totalMonthlySale
				
		o Item Controller
				POST /Items/add addItem
				DELETE /Items/delete/{itemId} deleteItem
				GET /Items/find/{itemId} getItem
				GET /Items/menu getAllItems
				POST /Items/update updateItem
				
		o Order Controller
				GET /{userId}/order/userOrders getUserSpecificOrder
				
● For User options Test the following URLs:
		
		o User Controller
				POST /user/login userLogin
				GET /user/logout userLogout
				PUT /user/register addUser
				GET /user/update updateUserDetails
		
		o Item Controller
				GET /Items/menu getAllItems
		
		o Order Controller
				DELETE /{userId}/order/{orderId} deleteItemsFromCart
				PUT /{userId}/order/cart addItemsToCart
				POST /{userId}/order/confirm confirmOrder
				GET /{userId}/order/orderHistory getorderHistory
				POST /{userId}/order/viewCart viewCart
				
### Models
				

● Bill
	
	billId	integer($int32)
	date	string($date)
	order	Order
					date	string($date)
					id	integer($int32)
					name	string
					price	number($double)
					quantity	integer($int32)
					status	string
					user	User
									emailId	string
									id	integer($int32)
									password	string
									type	string
									userName	string
									

			
	total	number($double)
	user	User
					emailId	string
					id	integer($int32)
					password	string
					type	string
					userName	string
				




● ItemDTO
		
		itemId	integer($int32)
		itemName	string
		price	number($double)
		
		
		
● Order
		
		date	string($date)
		id	integer($int32)
		name	string
		price	number($double)
		quantity	integer($int32)
		status	string
		user	User
						emailId	string
						id	integer($int32)
						password	string
						type	string
						userName	string
				
		
		
		
● OrderDTO
		
			date	string($date)
			id	integer($int32)
			name	string
			price	number($double)
			quantity	integer($int32)
			status	string
			userId	integer($int32)
		
		
● User
	
		emailId	string
		id	integer($int32)
		password	string
		type	string
		userName	string
	
	
● UserDTO
		
		emailId	string
		password	string
		type	string
		userId	integer($int32)
		userName	string
		

		
## Controllers
			
			
### Today's Bills
 
 **Example request:**

- **GET** [/bill/todaysBills](/bill/todaysBills) 
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
	Response
			
			Code	Description
			200			OK
			
			Example Value| Model
			[{
    			"billId": 0,
    			"date": "string",
    			"order": [
    					  {
        					"date": "string",
        					"id": 0,
        					"name": "string",
        					"price": 0,
        					"quantity": 0,
        					"status": "string",
        					"user": {
          								"emailId": "string",
          								"id": 0,
          								"password": "string",
          								"type": "string",
          								"userName": "string"
        							}
      					}
    				],
    				"total": 0,
    				"user": {
     							"emailId": "string",
      							"id": 0,
      							"password": "string",
      							"type": "string",
      							"userName": "string"
    						}}]
			
      
      
      
### Total Monthly Sale
 
 **Example request:**

- **GET** [/bill/totalMonthlySale](/bill/totalMonthlySale) 
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
			
    total double




### Add Item
- **POST** [/Items/add](/Items/add)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value |Model
			{   
			    "itemId": 0,
				"itemName": "string",
				"price": 0
				}


### Delete Item
- **DELETE** [/Items/delete/{itemId}](/Items/delete/{itemId})
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				"string"
				
        
### Get Item
- **GET** [/Items/find/{itemId}](/Items/find/{itemId})
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
				
		Example Value |Model
			{   
			    "itemId": 0,
				"itemName": "string",
				"price": 0
				}


### Get All Items
- **GET** [/Items/menu](/Items/menu)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value |Model
			[{   
			    "itemId": 0,
				"itemName": "string",
				"price": 0
				}
			]

### Update Item
- **POST** [/Items/update](/Items/update)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				"string"
				
### Delete Items From Cart        
- **DELETE** [/{userId}/order/{orderId}](/{userId}/order/{orderId})
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
		
			Code	Description
			200			OK
			
			Example Value|Model
				"string"
	
  
### Add Items To Cart
-**PUT** [/{userId}/order/cart](/{userId}/order/cart)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				"string"
				
        
        
 ### Confirm Order
- **POST** [/{userId}/order/confirm](/{userId}/order/confirm)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
			
			{	
				"billId": 0,
				"date": "string",
				"order": [
							{
								"date": "string",
								"id": 0,
								"name": "string",
								"price": 0,
								"quantity": 0,
								"status": "string",
								"user": {
										"emailId": "string",
										"id": 0,
										"password": "string",
										"type": "string",
										"userName": "string"
										}
							}
						],
				"total": 0,
				"user": {
						"emailId": "string",
						"id": 0,
						"password": "string",
						"type": "string",
						"userName": "string"
						}
				}
        
### Get Order History
- **GET** [/{userId}/order/orderHistory](/{userId}/order/orderHistory)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
				
		Example Value |Model
		[
			{
			"date": "string",
			"id": 0,
			"name": "string",
			"price": 0,
			"quantity": 0,
			"status": "string",
			"userId": 0
			}
		]

### Get Order of Particular User
- **GET** [/{userId}/order/userOrders](/{userId}/order/userOrders)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
				
		Example Value |Model
		[
			{
			"date": "string",
			"id": 0,
			"name": "string",
			"price": 0,
			"quantity": 0,
			"status": "string",
			"userId": 0
			}
		]
    
    
### View Cart

- **POST** [/{userId}/order/viewCart](/{userId}/order/userOrders)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
				
		Example Value |Model
		[
			{
			"date": "string",
			"id": 0,
			"name": "string",
			"price": 0,
			"quantity": 0,
			"status": "string",
			"userId": 0
			}
		]
	
### Get User Details
- **GET** [/user/{userId}](/user/{userId})
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
				
		Example Value |Model
		{
			"emailId": "string",
			"password": "string",
			"type": "string",
			"userId": 0,
			"userName": "string"
		}

### Get List of all Registered Users
- **GET** [/user/allUsers](/user/allUsers)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
				
		Example Value |Model
		[
			{
				"emailId": "string",
				"password": "string",
				"type": "string",
				"userId": 0,
				"userName": "string"
			}
		]


### Delete User Details
- **DELETE** [/user/delete/{userId}](/user/delete/{userId})
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				"string"



### User Login
- **POST** [/user/login userLogin](/user/login userLogin)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				"string"


### User Logout
- [GET /user/logout](/user/logout)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				"string"
				

### User Registration
- **PUT** [/user/register](/user/register)
- **Accept:** application/json
- **Content-Type:** application/json

**Example parameters:**
			
			Name	Description
			user *		user
			(body)	
			
					Example Value|Model
					{
						"emailId": "string",
						"password": "string",
						"type": "string",
						"userId": 0,
						"userName": "string"
					}
					
          
### Update User Details
- **GET** [/user/update](/user/update)
- **Accept:** application/json
- **Content-Type:** application/json

**Example response:**
	
			Code	Description
			200			OK
			
			Example Value|Model
				Integer
						
				
## Response Codes
		
		
| Code | Reason	     |
| ---- | ----------- |
| 200  |  OK	     |
| 401  | Unauthorized|
| 403  | Forbidden   |
| 404  | Not Found   |    
