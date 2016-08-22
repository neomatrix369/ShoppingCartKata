# Exercise 10 - Shopping Cart (Outside-In TDD)

### Scenario 1 - Add items to shopping basket

Given I add 2 units of "The Hobbit" to my shopping basket
And I add 5 units of "Breaking Bad"
When I check the content of my shopping basket
Then it should contain the following information:
- Creation date (of the shopping basket)
- 2 x The Hobbit   // 2 x 5.00 = £10.00 
- 5 x Breaking Bad // 5 x 7.00 = £35.00
- Total: £45.00

**Acceptance criteria:**
- Shopping basket should be created when the first product is added.
- Shopping basket should be persisted (In-memory, DB later)
- Each user should have her own shopping basket.

Products available (in-memory repository):
- Books 
   - 10001: Lord of the Rings - £10.00
   - 10002: The Hobbit - £5.00     
- DVDs
   - 20001: Game of Thrones - £9.00 
   - 20110: Breaking Bad - £7.00
   
```    
    public class ShoppingBasketService {
    
        public void addItem(UserID userId, ProductID productId, int quantity) { }

        public <?> basketFor(UserID userId) { }
    
    }    
```

### Scenario 2 - Logging - Hiding state
- Log items added to shopping cart on the console: 
    [BASKET CREATED]: Created[<"YYYY-07-12">], User[<ID>]
    [ITEM ADDED TO SHOPPING CART]: Added[<"YYYY-07-12">], User[<ID>], Product[ID], Quantity[<N>, Price[<£12.00>]

### Scenario 3 - Stock restriction
- If there are not enough items available in stock for purchase, throw exception
- When items are added to shopping basket, items should be reserved, that means, 
if there were 10 items in stock, and one user adds 8 to her shopping cart, we 
should have only 2 available for being purchase by another user.

### Scenario 4 - Multi-buy discount
- Shopping cart should contain multi-buy discount: 
   - 10% discount if more than 3 books are in the cart
   - 20% discount if at least one book and a video are in the cart
- In case both discounts on shopping basket apply, the biggest discount is selected.
