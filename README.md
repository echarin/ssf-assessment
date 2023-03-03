# ssf-assessment

- This is the assessment done for SSF

## Views

- View1
  - Add items to an order; when `Next` is pressed, View2 is displayed
- View2
  - Lets customer enter the order's shipping address
  - When pressing `Checkout`, completes processing and displays invoice in View3
- View3
  - Displays order invoice

## Controllers

- PurchaseOrderController
  - Use `HttpSession` to persist cart
  - Adding items to cart
  - Checking out
    - Use `QuotationService.getQuotations()` to calculate total price of cart
    - Display info in View 3 and clear contents of cart
    - If error is given, then display error in View 2 along with previously filled name and address

## Services

- CartService
  - Performs cart validation
  - Returns `List<String>` on items in cart (akin to set)
  - Use `Quotation` to calculate total price of the cart
- QuotationService
  - public Quotation getQuotations(List<String> items)
    - Takes in `List<String> items` and returns `Quotation`
    - Makes HTTP call to QSys with the items in a JSON array as a payload to the Qsys REST endpoint
    - [Endpoint]<https://quotation.chuklee.com>
      - Response `200 OK`
      - Content-Type `application/json`
      - Payload
        - Object containing `quoteId`, `quotations` containing items and their corresponding prices
      - Error
        - `{ "error": "error message" }`
    - Marshal JSON into Quotation object and return Quotation to caller

## Repos

## Models

- Item
  - `String` name
  - `Integer` quantity
- Cart
  - `Map<String, Integer>` list
- Quotation
  - asdf

