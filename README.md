# Excel Formula Parser

## Overview

Welcome to the Excel Formula Parser project! This tool is designed to parse and evaluate formulas similar to those you might find in a spreadsheet application. Whether you're a developer aiming to integrate formula parsing into your project or just a curious enthusiast, this parser has you covered.

## Supported Features

Our formula parser supports a range of powerful operators and functions, ensuring you can work with complex calculations easily. Here's a closer look at the capabilities:

### Parentheses
- **What it does:** Use parentheses `()` to control the order of operations and group expressions.

### Binary Operators
- **Supported operators:** `+`, `-`, `*`, `/`
- **Details:**
  - `+` Combines two numbers. 
  - `-` Subtracts one number from another.
  - `*` Multiplies two numbers. 
  - `/` Divides one number by another.

### Unary Operator
- **Supported operator:** `-`
- **Details:** This operator negates a number, turning positive values into negative ones and vice versa. 

### Functions
- **Supported functions:** `max`, `min`, `sin`, `cos`, `pow`
- **Details:**
  - `max(a, b, ...)`: Returns the largest value among the arguments. Example: `max(3, 5, 2) = 5`.
  - `min(a, b, ...)`: Returns the smallest value among the arguments. Example: `min(3, 5, 2) = 2`.
  - `sin(x)`: Computes the sine of `x` (in radians). Example: `sin(π/2) = 1`.
  - `cos(x)`: Computes the cosine of `x` (in radians). Example: `cos(0) = 1`.
  - `pow(base, exponent)`: Raises `base` to the power of `exponent`. Example: `pow(2, 3) = 8`.

### Cell References
- **What it does:** Refers to data in spreadsheet-style cells, such as `A1`, `B2`, etc.
  
## How to Run

Getting started with the Excel Formula Parser is quick and easy! Just follow these steps:

1. **Clone the Project**
   - Begin by downloading the repository to your local machine. Use the following command in your terminal:
     ```bash
     git clone https://github.com/polinak1r/Excel_Formula_Parser.git
     ```
   - This will create a local copy of the project that you can work with.

2. **Open the Project**
   - Launch your favorite Java IDE (like IntelliJ IDEA or Eclipse) and open the cloned project. 
   - Ensure the dependencies are properly configured in your environment.

3. **Run the `Main` Class**
   - Locate the `Main` class in the project structure. This is the entry point of the application.
   - Execute the `Main` class to start parsing formulas. You’ll see the output directly in the console.

4. **Run the Tests**
   - Testing is crucial to confirm that everything is working as expected. Run the test suite included in the project:
     ```bash
     ./gradlew test
     ```
   - The tests will validate that all operators, functions, and features of the parser are functioning correctly. Any issues will be highlighted for you to address.

5. **Experiment and Explore**
   - Once the setup is complete, you can start experimenting with different formulas. Try a mix of operators, functions, and cell references to see the parser in action.

---

## Contributing

Want to make this project even better? We welcome contributions! Here’s how you can get involved:

1. Fork the repository.
2. Make your changes in a feature branch.
3. Test your changes to ensure stability.
4. Submit a pull request for review.

Let’s build something amazing together!

## Support and Feedback

If you encounter any issues or have suggestions, feel free to open an issue in the repository. We’re always happy to help and improve based on your feedback.

---

Happy formula parsing! 🚀✨
