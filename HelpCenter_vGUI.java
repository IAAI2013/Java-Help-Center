import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class HelpCenter_vGUI extends JFrame {

    private DefaultListModel<String> listModel;
    private JList<String> topicList;
    private JTextArea helpArea;
    private JTextField searchField;

    private final String[] allTopics = {
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

    private final Color BG_DARK      = new Color(30, 30, 30);
    private final Color BG_PANEL     = new Color(45, 45, 45);
    private final Color BG_LIST      = new Color(40, 40, 40);
    private final Color FG_TEXT      = new Color(220, 220, 220);
    private final Color FG_ACCENT    = new Color(100, 180, 255);
    private final Color SELECTION_BG = new Color(0, 120, 215);
    private final Color BUTTON_BG    = Color.BLACK;

    public HelpCenter_vGUI() {
        setTitle("Java Help System - Dark Edition");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        // ========== TOP PANEL (Title + Search) ==========
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BG_DARK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JLabel titleLabel = new JLabel("JAVA HELP CENTER");
        titleLabel.setForeground(FG_ACCENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BG_DARK);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(FG_TEXT);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        searchField = new JTextField();
        searchField.setBackground(BG_PANEL);
        searchField.setForeground(FG_TEXT);
        searchField.setCaretColor(FG_TEXT);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTopics(); }
            public void removeUpdate(DocumentEvent e) { filterTopics(); }
            public void changedUpdate(DocumentEvent e) { filterTopics(); }
        });

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        topPanel.add(titleLabel);
        topPanel.add(searchPanel);

        // ========== LEFT: Topic list ==========
        listModel = new DefaultListModel<>();
        for (String t : allTopics) listModel.addElement(t);

        topicList = new JList<>(listModel);
        topicList.setBackground(BG_LIST);
        topicList.setForeground(FG_TEXT);
        topicList.setSelectionBackground(SELECTION_BG);
        topicList.setSelectionForeground(Color.WHITE);
        topicList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topicList.setFixedCellHeight(32);
        topicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        topicList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 8));
                label.setOpaque(true);

                if (isSelected) {
                    label.setBackground(SELECTION_BG);
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(index % 2 == 0 ? BG_LIST : new Color(48, 48, 48));
                    label.setForeground(FG_TEXT);
                }

                label.setText("<html><font color='#64B4FF'>●</font> &nbsp;" + value + "</html>");
                return label;
            }
        });

        JScrollPane listScroll = new JScrollPane(topicList);
        listScroll.setPreferredSize(new Dimension(280, 0));
        listScroll.setBorder(BorderFactory.createEmptyBorder());
        listScroll.getViewport().setBackground(BG_LIST);

        // ========== CENTER: Help text ==========
        helpArea = new JTextArea();
        helpArea.setEditable(false);
        helpArea.setBackground(BG_PANEL);
        helpArea.setForeground(FG_TEXT);
        helpArea.setCaretColor(FG_TEXT);
        helpArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        helpArea.setLineWrap(true);
        helpArea.setWrapStyleWord(true);
        helpArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane textScroll = new JScrollPane(helpArea);
        textScroll.setBorder(BorderFactory.createEmptyBorder());
        textScroll.getViewport().setBackground(BG_PANEL);

        // ========== BOTTOM: Buttons ==========
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBackground(BG_DARK);

        JButton copyButton = createDarkButton("Copy Explanation");
        copyButton.addActionListener(e -> {
            String text = helpArea.getText();
            if (!text.isEmpty() && !text.startsWith("Select a topic")) {
                Toolkit.getDefaultToolkit()
                       .getSystemClipboard()
                       .setContents(new StringSelection(text), null);
                JOptionPane.showMessageDialog(this, "Explanation copied to clipboard!",
                        "Copied", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton quitButton = createDarkButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        bottomPanel.add(copyButton);
        bottomPanel.add(quitButton);

        // ========== Layout ==========
        add(topPanel, BorderLayout.NORTH);
        add(listScroll, BorderLayout.WEST);
        add(textScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        topicList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = topicList.getSelectedValue();
                if (selected != null) {
                    showHelp(selected);
                }
            }
        });

        helpArea.setText("Select a topic from the list on the left\nor type in the search box to filter topics.");
    }

    private JButton createDarkButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BUTTON_BG);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(40, 40, 40));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.BLACK);
            }
        });
        return btn;
    }

    private void filterTopics() {
        String filter = searchField.getText().toLowerCase().trim();
        listModel.clear();

        for (String topic : allTopics) {
            if (filter.isEmpty() || topic.toLowerCase().contains(filter)) {
                listModel.addElement(topic);
            }
        }
    }

    private void showHelp(String topic) {
        String text = switch (topic) {

            case "if / else" -> """
                if / else
                ---------
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
                switch
                ------
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
                for loop
                --------
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
                while loop
                ----------
                The while loop repeats a block of code as long as a condition
                remains true. It is useful when you do not know in advance
                how many times the loop should run.
                
                Syntax:
                while (condition) {
                    // code
                }
                """;

            case "do-while loop" -> """
                do-while loop
                -------------
                The do-while loop is similar to the while loop, but it always
                executes the code block at least once before checking the condition.
                
                Syntax:
                do {
                    // code
                } while (condition);
                """;

            case "enhanced for (for-each)" -> """
                Enhanced for (for-each)
                -----------------------
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
                break
                -----
                The break statement is used to exit a loop or switch statement
                immediately. It is useful when you want to stop the loop early.
                
                Syntax:
                break;            // exits the nearest loop or switch
                break label;      // exits a labeled block
                """;

            case "continue" -> """
                continue
                --------
                The continue statement skips the rest of the current loop
                iteration and jumps to the next one.
                
                Syntax:
                continue;         // skips current iteration
                continue label;   // continues a labeled loop
                """;

            case "return" -> """
                return
                ------
                The return statement is used to exit a method immediately.
                It can also send a value back to the code that called the method.
                
                Syntax:
                return;           // exits the method
                return value;     // exits and returns a value
                """;

            case "try-catch-finally" -> """
                try-catch-finally
                -----------------
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
                throw / throws
                --------------
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
                assert
                ------
                The assert statement is used for debugging. It checks if a
                condition is true. If the condition is false, it throws an
                AssertionError. Assertions are usually disabled by default.
                
                Syntax:
                assert condition;
                assert condition : "error message";
                
                Enable with: java -ea YourProgram
                """;

            case "class & object" -> """
                class & object
                --------------
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
                methods
                -------
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
                constructors
                ------------
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
                this keyword
                ------------
                The 'this' keyword refers to the current object.
                It is commonly used to distinguish between instance variables
                and parameters with the same name.
                
                Common uses:
                this.field = value;
                this.method();
                this(args);            // call another constructor
                """;

            case "static" -> """
                static
                ------
                The static keyword means that a variable or method belongs to
                the class itself, not to individual objects.
                Static members can be accessed without creating an object.
                
                Examples:
                static int count = 0;
                static void show() { ... }
                
                Called with: ClassName.method()
                """;

            case "final" -> """
                final
                -----
                The final keyword is used to make something unchangeable:
                
                • final variable  → cannot be changed (constant)
                • final method    → cannot be overridden
                • final class     → cannot be extended
                """;

            case "inheritance (extends)" -> """
                inheritance (extends)
                ---------------------
                Inheritance allows a class to inherit fields and methods
                from another class. It promotes code reuse.
                
                Syntax:
                class Child extends Parent {
                    // Child inherits Parent's members
                }
                
                Child is-a Parent.
                """;

            case "interfaces (implements)" -> """
                interfaces (implements)
                -----------------------
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
                super keyword
                -------------
                The 'super' keyword refers to the parent class.
                It is used to access parent fields, methods, or constructors.
                
                Common uses:
                super.field;
                super.method();
                super(args);           // call parent constructor
                """;

            case "abstract class / method" -> """
                abstract class / method
                -----------------------
                An abstract class cannot be instantiated directly.
                An abstract method has no body and must be implemented
                by subclasses.
                
                Syntax:
                abstract class Shape {
                    abstract void draw();   // no body
                }
                """;

            case "arrays" -> """
                arrays
                ------
                An array is a container that holds a fixed number of values
                of a single type. Arrays are useful for storing lists of data.
                
                Syntax:
                int[] nums = new int[5];
                int[] nums = {1, 2, 3, 4, 5};
                
                Access: nums[0]
                Length: nums.length
                """;

            case "String" -> """
                String
                ------
                String is a class used to store and manipulate text.
                Strings in Java are immutable (cannot be changed once created).
                
                Common methods:
                s.length()
                s.toUpperCase()
                s.substring(0, 3)
                s.equals("Hello")
                """;

            case "packages & import" -> """
                packages & import
                -----------------
                Packages are used to organize classes into folders/namespaces.
                The import statement allows you to use classes from other packages.
                
                Examples:
                package mypackage;
                
                import java.util.Scanner;
                import java.util.*;        // all classes in the package
                """;

            case "synchronized" -> """
                synchronized
                ------------
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
                enum
                ----
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
                lambda expressions
                ------------------
                Lambda expressions provide a short way to write anonymous
                functions. They are mainly used with functional interfaces
                (for example with streams or event handlers).
                
                Syntax:
                (parameters) -> expression
                
                Example:
                list.forEach(n -> System.out.println(n));
                """;

            case "records (Java 16+)" -> """
                records (Java 16+)
                ------------------
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

        helpArea.setText(text);
        helpArea.setCaretPosition(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            HelpCenter_vGUI frame = new HelpCenter_vGUI();
            frame.setVisible(true);
        });
    }
}