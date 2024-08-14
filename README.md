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

2. Product Management (Shoghik)
- Add, edit, and remove products.
- Categorize products and manage inventory levels.

3. Shopping Cart and Checkout System (Muyang)
- Implement a shopping cart where users can add or remove products.
- Secure checkout process including payment integration.

4. Order Management (Shoghik)
- Track order status from placement to delivery.
- Allow users to view their order history and track shipments.

## Bonus Features

1. Product Recommendations: Implement an algorithm to suggest products based on user behavior and preferences. (AI)
2. Dynamic Pricing: Use market and user data to adjust product prices in real-time.





----------


1. **User**

   | id        | Long          | @Id                |
   |-----------|---------------|--------------------|
   | username  | String        |                    |
   | password  | String        |                    |
   | email     | String        |                    |
   | role      | String        |                    |

   
2. **Book**

   | id          | Long          | @Id                |
   |-------------|---------------|--------------------|
   | title       | String        |                    |
   | author      | String        |                    |
   | isbn        | String        |                    | 
   | description | String        |                    |
   | price       | Double        |                    |
   | category    | String        |                    |
   | inventory   | Int           |                    |

      

3. **UserProfile**

   | id          | Long          | @Id                |
   |-------------|---------------|--------------------|
   | firstName   | String        |                    |
   | lastName    | String        |                    |
   | dateOfBirth | LocalDate     |                    |
   | addresses   | List<Address> | @OneToMany         |
   | user        | User          | @OneToOne          |
   

4. **Address**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | streetName   | String         |                    |
   | streetNumber | String         |                    |
   | cityCode     | String         |                    |
   | country      | String         |                    |
   | postNumber   | String         |                    |

5. **CartItem**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | book         | Book           | @ManyToOne         |
   | quantity     | Int            |                    |
   | user         | User           | @ManyToOne         |
   
   

6. **Order**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | user         | User           | @ManyToOne         |
   | cartItems    | List<CartItem> | @OneToMany         |
   | orderDate    | LocalDateTime  |                    |
   | status       | String         |                    |
   | totalPrice   | Double         |                    |

7. **ShoppingCart**

   | id           | Long           | @Id                |
   |--------------|----------------|--------------------|
   | cartItems    | List<CartItem> | @OneToMany         |
   | user         | User           | @OneToOne          |




