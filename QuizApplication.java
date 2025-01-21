import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {

    private static final Scanner scanner = new Scanner(System.in);

    // Structure to store quiz questions, options, and correct answers
    static class Question {
        String questionText;
        String[] options;
        int correctAnswerIndex;

        public Question(String questionText, String[] options, int correctAnswerIndex) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }

    // Timer for each question
    static class QuestionTimer {
        Timer timer = new Timer();
        int timeLimit = 10;  // Time limit for each question (in seconds)
        boolean timeUp = false;

        public void startTimer() {
            timer.schedule(new TimerTask() {
                public void run() {
                    timeUp = true;
                    System.out.println("\nTime's up! Moving to next question...\n");
                }
            }, timeLimit * 1000);
        }

        public void cancelTimer() {
            timer.cancel();
        }
    }

    // List of questions for the quiz
    private static final Question[] questions = {
        new Question("What is the capital of France?", new String[] {"Berlin", "Madrid", "Paris", "Rome"}, 2),
        new Question("Which planet is known as the Red Planet?", new String[] {"Earth", "Mars", "Jupiter", "Saturn"}, 1),
        new Question("Who wrote 'Hamlet'?", new String[] {"Shakespeare", "Dickens", "Austen", "Tolkien"}, 0),
        new Question("What is the largest ocean on Earth?", new String[] {"Atlantic", "Indian", "Arctic", "Pacific"}, 3),
        new Question("What is 2 + 2?", new String[] {"3", "4", "5", "6"}, 1)
    };

    public static void main(String[] args) {
        int score = 0;
        int questionNumber = 0;

        // Loop through each question in the quiz
        for (Question question : questions) {
            System.out.println("Question " + (questionNumber + 1) + ": " + question.questionText);

            // Display options
            for (int i = 0; i < question.options.length; i++) {
                System.out.println((i + 1) + ". " + question.options[i]);
            }

            // Create a timer for this question
            QuestionTimer questionTimer = new QuestionTimer();
            questionTimer.startTimer();

            // Get user input for answer within the time limit
            int answer = -1;
            while (answer < 1 || answer > 4) {
                if (questionTimer.timeUp) {
                    break;
                }
                System.out.print("Select an option (1-4): ");
                if (scanner.hasNextInt()) {
                    answer = scanner.nextInt();
                    if (answer < 1 || answer > 4) {
                        System.out.println("Invalid option. Please select between 1 and 4.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    scanner.next(); // consume invalid input
                }
            }

            // Cancel timer when the user selects an answer or time is up
            questionTimer.cancelTimer();

            // Check if the answer is correct
            if (!questionTimer.timeUp && answer - 1 == question.correctAnswerIndex) {
                System.out.println("Correct!\n");
                score++;
            } else if (questionTimer.timeUp) {
                System.out.println("You ran out of time!\n");
            } else {
                System.out.println("Incorrect.\n");
            }

            questionNumber++;
        }

        // Display final score
        System.out.println("Quiz finished!");
        System.out.println("Your final score: " + score + "/" + questions.length);
        System.out.println("Thank you for playing!");
    }
}