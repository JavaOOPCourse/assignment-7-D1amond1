import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        String inputFile = "input/students.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        throw new NumberFormatException("Invalid line format");
                    }

                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Score must be between 0 and 100");
                    }

                    students.add(new Student(name, score));

                } catch (NumberFormatException | InvalidScoreException e) {
                    System.out.println("Invalid data: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + inputFile);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()) {
            averageScore = 0.0;
            highestStudent = null;
            return;
        }

        int sum = 0;
        highestStudent = students.get(0);

        for (Student student : students) {
            sum += student.getScore();

            if (student.getScore() > highestStudent.getScore()) {
                highestStudent = student;
            }
        }

        averageScore = (double) sum / students.size();

        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        String outputFile = "output/report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Average: " + averageScore);
            writer.newLine();

            if (highestStudent != null) {
                writer.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
            } else {
                writer.write("Highest: no valid student data");
            }

            writer.newLine();
            writer.newLine();

            writer.write("Sorted students:");
            writer.newLine();

            for (Student student : students) {
                writer.write(student.getName() + " - " + student.getScore());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}
// class Student (name, score)
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}