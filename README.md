# Excel formula parser

## Supported Operators

- Parentheses
- Binary operators (`+`, `-`, `*`, `/`)
- Unary operator `-` (but refer to point 2 in "Important Notes")
- Functions: `max`, `min`, `sin`, `cos`, `pow`
- Cell references (but refer to point 3 in "Important Notes")

## Important Notes

1. To calculate the result of a formula in a cell, double-click on the cell and press the Enter key.
2. Two or more operators should not be placed consecutively (e.g., `- -2` will not work; in this case, enclose the expression in parentheses like `-(-2)`).
3. In cell references, the column coordinate must be an uppercase letter (`A1` is valid, `a1` is not). Functions, however, can be in any case (`max`, `Max`, `MAX` are all valid).
4. A formula in a cell is evaluated only once, and its value does not update automatically if the cells it depends on are changed. 

## How to Run

1. Clone the project.
2. Run the `Main` class.
3. Execute the tests to ensure everything is working correctly.
