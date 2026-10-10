import java.util.Scanner;

public class HelpCenter_vBash {

    private static final String[] topics = {
        "if / else",
        "switch",
        "for loop",
        "while loop",
        "do-while loop",
        "enhanced for (for-each)",
        "break",
        "continue",
        "return",
        "try-catch-finally",
        "throw / throws",
        "assert",
        "class & object",
        "methods",
        "constructors",
        "this keyword",
        "static",
        "final",
        "inheritance (extends)",
        "interfaces (implements)",
        "super keyword",
        "abstract class / method",
        "arrays",
        "String",
        "packages & import",
        "synchronized",
        "enum",
        "lambda expressions",
        "records (Java 16+)"
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();

            System.out.print("\nEnter topic number (or 'q' to quit): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("\nGoodbye!");
                break;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= topics.length) {
                    showHelp(topics[choice - 1]);
                } else {
                    System.out.println("\nInvalid number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number or 'q'.");
            }

            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n====================================");
        System.out.println("         JAVA HELP CENTER");
        System.out.println("====================================");

        for (int i = 0; i < topics.length; i++) {
            System.out.printf("%2d. %s%n", i + 1, topics[i]);
        }

        System.out.println("------------------------------------");
        System.out.println(" q. Quit");
    }

    private static void showHelp(String topic) {
        System.out.println("\n====================================");
        System.out.println(topic.toUpperCase());
        System.out.println("====================================\n");

        String text = switch (topic) {

            case "if / else" -> """
                The if-else statement is used to make decisions in your program.
                It allows the program to execute different blocks of code depending
                on whether a condition is true or false.
                
                Syntax:
                if (condition) {
                    // code if true
                } else {
                    // code if false
                }
                
                You can also chain multiple conditions:
                if (...) { ... }
                else if (...) { ... }
                else { ... }
                """;

            case "switch" -> """
                The switch statement is used to select one of many code blocks
                to execute based on the value of a variable or expression.
                It is a cleaner alternative to many if-else statements.
                
                Syntax:
                switch (expression) {
                    case value1:
                        // code
                        break;
                    case value2:
                        // code
                        break;
                    default:
                        // optional
                }
                
                Modern switch (Java 14+):
                int result = switch(x) {
                    case 1 -> 10;
                    case 2 -> 20;
                    default -> 0;
                };
                """;

            case "for loop" -> """
                The for loop is used when you know in advance how many times
                you want to repeat a block of code. It is commonly used for counting
                or going through a range of values.
                
                Syntax:
                for (initialization; condition; update) {
                    // code
                }
                
                Example:
                for (int i = 0; i < 5; i++) {
                    System.out.println(i);
                }
                """;

            case "while loop" -> """
                The while loop repeats a block of code as long as a condition
                remains true. It is useful when you do not know in advance
                how many times the loop should run.
                
                Syntax:
                while (condition) {
                    // code
                }
                """;

            case "do-while loop" -> """
                The do-while loop is similar to the while loop, but it always
                executes the code block at least once before checking the condition.
                
                Syntax:
                do {
                    // code
                } while (condition);
                """;

            case "enhanced for (for-each)" -> """
                The enhanced for loop (also called for-each) is used to easily
                loop through arrays or collections without using an index.
                
                Syntax:
                for (type element : collection) {
                    // use element
                }
                
                Example:
                int[] nums = {10, 20, 30};
                for (int n : nums) {
                    System.out.println(n);
                }
                """;

            case "break" -> """
                The break statement is used to exit a loop or switch statement
                immediately. It is useful when you want to stop the loop early.
                
                Syntax:
                break;            // exits the nearest loop or switch
                break label;      // exits a labeled block
                """;

            case "continue" -> """
                The continue statement skips the rest of the current loop
                iteration and jumps to the next one.
                
                Syntax:
                continue;         // skips current iteration
                continue label;   // continues a labeled loop
                """;

            case "return" -> """
                The return statement is used to exit a method immediately.
                It can also send a value back to the code that called the method.
                
                Syntax:
                return;           // exits the method
                return value;     // exits and returns a value
                """;

            case "try-catch-finally" -> """
                The try-catch-finally block is used for exception handling.
                It allows your program to catch and handle errors gracefully
                instead of crashing.
                
                Syntax:
                try {
                    // risky code
                } catch (ExceptionType e) {
                    // handle the error
                } finally {
                    // always runs (cleanup)
                }
                """;

            case "throw / throws" -> """
                throw is used to manually create and throw an exception.
                throws is used in a method declaration to indicate that
                the method might throw an exception.
                
                Examples:
                throw new ExceptionType("message");
                
                void method() throws IOException {
                    // ...
                }
                """;

            case "assert" -> """
                The assert statement is used for debugging. It checks if a
                condition is true. If the condition is false, it throws an
                AssertionError. Assertions are usually disabled by default.
                
                Syntax:
                assert condition;
                assert condition : "error message";
                
                Enable with: java -ea YourProgram
                """;

            case "class & object" -> """
                A class is a blueprint for creating objects.
                An object is an instance of a class that contains data and methods.
                
                Syntax:
                class MyClass {
                    // fields and methods
                }
                
                Create an object:
                MyClass obj = new MyClass();
                """;

            case "methods" -> """
                A method is a block of code that performs a specific task.
                Methods help organize code and allow reuse.
                
                Syntax:
                returnType methodName(parameters) {
                    // body
                    return value; // if not void
                }
                
                Example:
                int add(int a, int b) {
                    return a + b;
                }
                """;

            case "constructors" -> """
                A constructor is a special method that is called automatically
                when you create an object using the 'new' keyword.
                It is mainly used to initialize the object.
                
                Example:
                class Person {
                    String name;
                
                    Person(String n) {   // constructor
                        name = n;
                    }
                }
                """;

            case "this keyword" -> """
                The 'this' keyword refers to the current object.
                It is commonly used to distinguish between instance variables
                and parameters with the same name.
                
                Common uses:
                this.field = value;
                this.method();
                this(args);            // call another constructor
                """;

            case "static" -> """
                The static keyword means that a variable or method belongs to
                the class itself, not to individual objects.
                Static members can be accessed without creating an object.
                
                Examples:
                static int count = 0;
                static void show() { ... }
                
                Called with: ClassName.method()
                """;

            case "final" -> """
                The final keyword is used to make something unchangeable:
                
                • final variable  → cannot be changed (constant)
                • final method    → cannot be overridden
                • final class     → cannot be extended
                """;

            case "inheritance (extends)" -> """
                Inheritance allows a class to inherit fields and methods
                from another class. It promotes code reuse.
                
                Syntax:
                class Child extends Parent {
                    // Child inherits Parent's members
                }
                
                Child is-a Parent.
                """;

            case "interfaces (implements)" -> """
                An interface defines a contract of methods that a class
                must implement. A class can implement multiple interfaces.
                
                Syntax:
                interface Drawable {
                    void draw();
                }
                
                class Circle implements Drawable {
                    public void draw() { ... }
                }
                """;

            case "super keyword" -> """
                The 'super' keyword refers to the parent class.
                It is used to access parent fields, methods, or constructors.
                
                Common uses:
                super.field;
                super.method();
                super(args);           // call parent constructor
                """;

            case "abstract class / method" -> """
                An abstract class cannot be instantiated directly.
                An abstract method has no body and must be implemented
                by subclasses.
                
                Syntax:
                abstract class Shape {
                    abstract void draw();   // no body
                }
                """;

            case "arrays" -> """
                An array is a container that holds a fixed number of values
                of a single type. Arrays are useful for storing lists of data.
                
                Syntax:
                int[] nums = new int[5];
                int[] nums = {1, 2, 3, 4, 5};
                
                Access: nums[0]
                Length: nums.length
                """;

            case "String" -> """
                String is a class used to store and manipulate text.
                Strings in Java are immutable (cannot be changed once created).
                
                Common methods:
                s.length()
                s.toUpperCase()
                s.substring(0, 3)
                s.equals("Hello")
                """;

            case "packages & import" -> """
                Packages are used to organize classes into folders/namespaces.
                The import statement allows you to use classes from other packages.
                
                Examples:
                package mypackage;
                
                import java.util.Scanner;
                import java.util.*;        // all classes in the package
                """;

            case "synchronized" -> """
                The synchronized keyword is used for thread safety.
                It ensures that only one thread can execute a block of code
                at a time, preventing data corruption.
                
                Syntax:
                synchronized (object) {
                    // only one thread at a time
                }
                
                Or on methods:
                synchronized void method() { ... }
                """;

            case "enum" -> """
                An enum (enumeration) is a special type used to define
                a fixed set of constants. It makes code more readable
                and type-safe.
                
                Example:
                enum Day {
                    MONDAY, TUESDAY, WEDNESDAY
                }
                
                Day today = Day.MONDAY;
                """;

            case "lambda expressions" -> """
                Lambda expressions provide a short way to write anonymous
                functions. They are mainly used with functional interfaces
                (for example with streams or event handlers).
                
                Syntax:
                (parameters) -> expression
                
                Example:
                list.forEach(n -> System.out.println(n));
                """;

            case "records (Java 16+)" -> """
                A record is a special kind of class designed to store data.
                It automatically generates the constructor, getters,
                equals(), hashCode(), and toString() methods.
                
                Syntax:
                record Point(int x, int y) { }
                
                This automatically creates:
                 - private final fields
                 - constructor
                 - getters (x(), y())
                 - equals, hashCode, toString
                """;

            default -> "No help available for this topic.";
        };

        System.out.println(text);
    }
}