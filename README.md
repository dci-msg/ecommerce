# Java Project #4: E-Commerce Platform
Objectives

Develop a comprehensive platform for online shopping, allowing users to browse, search, and purchase products.

## Features & Capabilities

1. User Account Management (Gizem)
- Registration, login.
- Password recovery and user authentication.

2. User Profile Management (Terissa)
- Changing names.
- Changing date of birth.
- Adding/deleting/changing addresses.

3. Product Management (Shoghik)
- Add, edit, and remove products.
- Categorize products and manage inventory levels.

4. Shopping Cart and Checkout System (Muyang)
- Implement a shopping cart where users can add or remove products.
- Secure checkout process including payment integration.

5. Order Management (Shoghik)
- Track order status from placement to delivery.
- Allow users to view their order history and track shipments.

## Bonus Features

1. Product Recommendations: Implement an algorithm to suggest products based on user behavior and preferences. (AI)
2. Dynamic Pricing: Use market and user data to adjust product prices in real-time.



 1. **User**
    
    | userId           | Long          | @Id                |
    |------------------|---------------|--------------------|
    | email            | String        |                    |
    | password         | String        | @NotEmpty          |
    | isActive         | boolean       |                    |
    | registrationDate | LocalDateTime | @CreationTimeStamp |
    | userType         | UserType      | @ManyToOne         |


2. **UserType**

   | userTypeId   | Long       | @Id        |
   |--------------|------------|------------|
   | userTypeName | String     |            |
   | users        | List<User> | @OneToMany |
 

   
3. **Book**

   | id          | Long        | @Id        |
   |-------------|-------------|------------|
   | title       | String      |            |
   | author      | String      |            |
   | isbn        | String      | uniq       | 
   | description | String      |            |
   | price       | BigDecimal  |            |
   | category    | Category    | @ManyToOne |
   | pages       | Int         |            |
   | image       | String      |            |
   | language    | String      |            |
   | publishDate | LocalDate   |            |
      

4. **UserProfile**

   | id          | Long          | @Id                |
   |-------------|---------------|--------------------|
   | firstName   | String        |                    |
   | lastName    | String        |                    |
   | dateOfBirth | LocalDate     |                    |
   | addresses   | List<Address> | @OneToMany         |
   | user        | User          | @OneToOne          |
   

5. **Address**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | streetName   | String         |                    |
   | streetNumber | String         |                    |
   | cityCode     | String         |                    |
   | country      | String         |                    |
   | postNumber   | String         |                    |

6. **CartItem**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | book         | Book           | @ManyToOne         |
   | quantity     | Int            |                    |
   | user         | User           | @ManyToOne         |
   
   

7. **Order**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | user         | User           | @ManyToOne         |
   | cartItems    | List<CartItem> | @OneToMany         |
   | orderDate    | LocalDateTime  |                    |
   | status       | String         |                    |
   | totalPrice   | Double         |                    |

8. **ShoppingCart**

   | id        | Long           | @Id        |
   |-----------|----------------|------------|
   | cartItems | List<CartItem> | @OneToMany |
   | user      | User           | @OneToOne  |


8. **Inventory**

   | id         | Long          | @Id                |
   |------------|---------------|--------------------|
   | book       | Book          | @OneToOne          |
   | emails     | List<String>  | @ElementCollection |

9. **Category**

   | id          | Long       | @Id        |
   |-------------|------------|------------|
   | name        | String     |            |
   | description | String     |            |
   | book        | List<Book> | @OneToMany |





# 1. User Account Management

This module focuses on the **User Account Management** functionality for the E-Commerce Platform. The primary features include:

- Registration
- Login
- Logout
- Password recovery
- User authentication

This module differentiates between two types of users: **Admin** and **Customer**.

## Features and Capabilities

### 1.1. Registration

**Admin Registration**:

- **Admin** users are created by default using the `UserDataInitializer` class when the program is run.
- The `UserDataInitializer` class is used to add new admins.
- Current admin credentials:
    - Email address: `admin@example.com`
    - Password: `admin123`

**New User Registration**:

- Users can register by providing an email and password.
- Upon successful registration, users are assigned the role of **Customer** by default.
- A verification email containing a unique token is sent to the user's email address for email verification.
- These default users are automatically created at the application's startup and can be used for testing purposes on the system:
    - Customer1 Email address: `customer1@example.com`
    - Password: `customer123`
    - Customer2 Email address: `customer2@example.com`
    - Password: `customer456`

       - If the user does not click on this link within 24 hours, the token will expire.
       - If the user confirms the token by clicking the link, they will be directed to the login page.
       - If a user attempts to register again with the same email address without confirming the token, the old token is invalidated, and a new token is generated and sent to the email address.

### 1.2. Login

**Admin Login**:
   - Admin users are assigned by the DataInitializer class when the project is run. Admins are logged in with their assigned credentials.

**User Login**:
   - Registered users can log in to their accounts using their credentials (email and password).

   - Both **Admin** and **Customer** roles have different landing pages post-login:
      - **Customer**: Redirected to dashboard.html.
        - My Profile
        - My Orders
        - Explore Books
      - **Admin**: Redirected to admin.html.
        - Manage Books 
        - View Orders
        - Manage Users
   - Login attempts are secured to prevent unauthorized access.

### 1.3. Logout

- **User Logout**:
   - "Admin"s and "Customer"s can securely log out of their accounts, terminating their current session and ensuring their information is protected.
   - Upon logout, the user is redirected to the login page.

### 1.4. Password Recovery

- **Password Recovery and Reset**:
   - Users can request a password reset link by using the "Forgot your password?" option on the login page if they forget their password.
   - A unique token is generated and sent to the user’s registered email address.
   - Users can use this token to securely set a new password.

### 1.5. User Authentication

- **Authentication and Authorization**:
   - The system verifies user credentials during login and ensures secure access to user-specific pages.
   - Only verified and authenticated users can access the dashboard pages.
   - Users who have not verified their email addresses can attempt to register again with the same email and receive a new verification token.
   - The module uses role-based access control to ensure that **Administrators** and **Customers** can only access their designated pages.

## Usage

To use the User Account Management features:

**Registration**:
   - Go to the registration page and fill in the required information.
   - Check your email for the verification link and click it to activate your account.

**Login**:
   - Enter your registered email and password on the login page.
   - You will be redirected to the appropriate dashboard based on your role.

**Forgot Password**:
   - Click on the "Forgot Password" link on the login page.
   - Enter your email address to receive a password reset link.
   - Follow the instructions in the email to reset your password.

## Conclusion

This module provides a robust and secure user management system, enabling efficient registration, login, password management, and role-based access control. By distinguishing between **Administrator** and **Customer** roles, the system ensures that each user has the appropriate access and permissions.

# 2. User Profile Management

#TODO 


# 3. Product Management

### 3.1. Book Management:
The admins can 
- Add new books to the inventory. 
- Edit existing book details. 
- Delete books from the inventory.


### 3.2. Category Management:
The admins can Create and manage book categories.


### 3.3. Inventory Management:
-Update and monitor inventory levels for each book.
-When stock drops to 10, all administrators receive an email notification to order more books.
-When stock falls below 3, customers who have shown interest in the book receive an email notification.




# 4. Shopping Cart and Checkout System

#TODO

# 5. Order Management

#TODO 

