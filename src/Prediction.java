import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.text.MessageFormat.format;

public class Prediction {



    // Defining color values for GUI
    private static final String RED, REDBG, REDU, GREEN, YELLOW, GREENBU, GRAY, BLUE, CYANBG, CYANBU, CYAN, RESET;
    static {
        RED = "\u001B[31m";
        REDBG = "\u001B[30m\u001B[41m";
        REDU = "\033[4;31m";
        GREEN = "\033[0;32m";
        GREENBU = "\033[1;32m\033[4;32m";
        YELLOW = "\033[0;33m";
        GRAY = "\u001b[27;1m";
        BLUE = "\u001B[30m\u001B[44m";
        CYAN = "\u001B[36m";
        CYANBG = "\u001B[46m\u001B[30m";
        CYANBU = "\033[1;96m\033[4;36m";
        RESET = "\033[0m";
    }



    public static void main(String[] args) throws Exception {


        // Load the dataset
        DataSource dataSource = new DataSource("political_party_data.arff");
        Instances data = dataSource.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);




        // Train the decision tree model
        Classifier classifier = new J48();
        classifier.buildClassifier(data);




        // Prepare attributes for user input
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            attributes.add(data.attribute(i));
        }
        ArrayList<String> politicalPartyValues = new ArrayList<>();
        Enumeration<Object> classValues = data.attribute(data.classIndex()).enumerateValues();
        while (classValues.hasMoreElements()) {
            politicalPartyValues.add((String) classValues.nextElement());
        }
        Attribute classAttribute = new Attribute("politicalParty", politicalPartyValues);




        // Create an instance for user input
        Instances userInputInstances = new Instances("UserInput", attributes, 1);
        userInputInstances.setClassIndex(userInputInstances.numAttributes() - 1);
        Instance userInput = new DenseInstance(userInputInstances.numAttributes());
        userInput.setDataset(userInputInstances);





        // Survey questions
        String[] questions = {
                "Do you support stricter gun control laws? " + GREENBU + "yes or no", // Democratic Question 1
                "Do you support the legalization of same-sex marriage? " + GREENBU + "yes or no", // Democratic Question 2
                "Do you support a path to citizenship for undocumented immigrants " + GREENBU + "yes or no", // Democratic Question 3
                "Do you believe in the right to access abortion services? " + GREENBU + "yes or no", // Democratic Question 4
                "Do you believe that the government should provide financial assistance to college students? " + GREENBU + "yes or no", // Neutral Question 1
                "Do you support measures to protect consumer privacy and data security online? " + GREENBU + "yes or no", // Neutral Question 2
                "Do you think the government should invest more in public infrastructure? " + GREENBU + "yes or no", // Neutral Question 3
                "Do you think the government should invest more in scientific research and development? " + GREENBU + "yes or no", // Neutral Question 4
                "Do you support the establishment of a single-payer healthcare system? " + GREENBU + "yes or no", // Socialist Question 1
                "Do you believe that the government should provide a universal basic income to all citizens? " + GREENBU + "yes or no", // Socialist Question 2
                "Do you support stronger labor rights and protections, such as raising the minimum wage and ensuring collective bargaining rights? " + GREENBU + "yes or no", // Socialist Question 3
                "Do you support progressive taxation, where the wealthy pay a higher percentage of their income in taxes? " + GREENBU + "yes or no", // Socialist Question 4
                "Do you support the legalization of recreational drugs, such as marijuana? " + GREENBU + "yes or no", // Libertarian Question 1
                "Do you oppose government regulation of the internet and online privacy? " + GREENBU + "yes or no", // Libertarian Question 2
                "Do you believe in a free-market economy with minimal government intervention? " + GREENBU + "yes or no", // Libertarian Question 3
                "Do you support reducing taxes and government spending? " + GREENBU + "yes or no", // Libertarian Question 4
                "Which of the following issues is most important to you: " + GREENBU + "education, healthcare, or the environment?", // Democratic Question 1
                "Do you believe that there should be no limitations on abortion? " + GREENBU + "none, first_trimester, second_trimester, banned?",
                "Do you believe that there should be no regulations on companies to reduce pollution? " + GREENBU + "none, some, strict",
                "Do you believe that the government should maintain current levels of military spending: " + GREENBU + "high, medium, low",
                "What should the government prioritize: national security, individual privacy, equality? " + GREENBU + "privacy, security, equality",
                "What is your stance on open borders as an immigration policy? " + GREENBU + "open_borders, amnesty, merit_based, family_based, closed_borders",
                "What is your stance on capitalism as an economic system? " + GREENBU + "capitalism, socialism, communism, mixed"
        };




        // Main Menu + Instructions
        System.out.println(GREEN + "\n\n\t\t\tML Survey: Party Prediction \uD83E\uDD14" + RESET + " by Aaron Ballesteros");
        System.out.println("▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁\n");
        System.out.println("◃ Simply answer the questions with the " + GREENBU + "underlined" + RESET + " choices provided." +
                "\n◃ The prediction is a " + YELLOW + "general idea" + RESET + " of what party you belong to." +
                "\n◃ If the confidence reaches " + REDU + "90% or more" + RESET + ", the survey will end." +
                "\n◃ Left Wing:  " + BLUE + " Democratic Party, Green, Socialist " + RESET +
                "\n◃ Right Wing: " + REDBG + " Republican Party, Constitutional Party " + RESET);
        System.out.println("\n▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁\n");


        Scanner scanner = new Scanner(System.in);
        String predictedParty = null;
        double[] probabilities = new double[0];
        int prediction = 0;
        int currentQuestion = 0;

        // Survey Progress Bar
        for (int i = 0; i < questions.length; i++) {
            currentQuestion++;
            int progress = (i * 100) / questions.length;
            System.out.print("\n\n" + YELLOW + "Survey Progress [");
            int bars = (progress / 10);
            for (int p = 0; p < 10; p++) {
                if (p < bars) {
                    System.out.print(GREENBU + "█");
                } else {
                    System.out.print(REDU + "▒");
                }
            }
            System.out.println(YELLOW + "] " + progress + "%" + RESET);





            // Question Counter, Output, User Input, Input Validation
            System.out.println("\nQuestion " + GREEN + (currentQuestion) + " of " + questions.length);
            System.out.println(YELLOW + questions[i] + RESET);
            String answer = "";
            boolean isValidInput = false;
            while (!isValidInput) {
                answer = scanner.next().trim().toLowerCase();
                if (i >= 16) { // Additional input validation for question 17
                    Set<String> validAnswers = new HashSet<>(Arrays.asList("education", "healthcare", "environment", "none", "first_trimester", "second_trimester", "banned", "some", "strict", "high", "medium", "low", "privacy", "security", "democratic", "republican", "constitutional", "socialist", "green", "open_borders", "amnesty", "merit_based", "family_based", "closed_borders", "capitalism", "socialism", "communism", "mixed", "equality"));
                    if (validAnswers.contains(answer)) {
                        isValidInput = true;
                    } else {
                        System.out.println(RED + "Invalid input. Please enter one of the given options." + RESET);
                    }
                } else { // Main input validation for all other questions
                    Set<String> validAnswers = new HashSet<>(Arrays.asList("yes", "no"));
                    if (validAnswers.contains(answer)) {
                        isValidInput = true;
                    } else {
                        System.out.println(RED + "Invalid input. Please enter one of the given options." + RESET);
                    }
                }
            }
            userInput.setValue(attributes.get(i), answer);






            // Predict the political party
            probabilities = classifier.distributionForInstance(userInput);
            prediction = (int) classifier.classifyInstance(userInput);
            predictedParty = classAttribute.value(prediction);






            // Print prediction for each question
            System.out.println(GRAY + "\n.--------------------------------------." + RESET);
            switch (predictedParty) {
                case "Democrat" ->
                        System.out.println(format("{0}\t Current Analysis: {1}{2}{3}\n\t Accuracy: {4}{5}%{6}", GREEN, CYANBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Republican", "Constitutional" ->
                        System.out.println(format("{0}\t Current Analysis: {1}{2}{3}\n\t Accuracy: {4}{5}%{6}", GREEN, REDU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Socialist", "Green" ->
                        System.out.println(format("{0}\t Current Analysis: {1}{2}{3}\n\t Accuracy: {4}{5}%{6}", GREEN, RED, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                default ->
                        System.out.println(GREEN + "\t Current Analysis: " + YELLOW + predictedParty + GREEN + "\n\t Accuracy: " + REDU + (probabilities[prediction] * 100) + "%" + RESET);
            }
            System.out.println(GRAY + "'--------------------------------------'" + RESET);






            // If the confidence is above 80%, stop the survey
            if (probabilities[prediction] > 0.9) {
                System.out.println("\n\nYour political party is: " + YELLOW + predictedParty + RESET);
                System.out.println(YELLOW + "Thank you for taking the survey!" + RESET);
                break;
            }
            if (probabilities[prediction] > 0.8) {
                System.out.println("\n\nYour political party leans toward: " + YELLOW + predictedParty + RESET);
                System.out.println(YELLOW + "Thank you for taking the survey!" + RESET);
                break;
            }
        }
        scanner.close();






        /*  --------------------------------------------------------------------
            The user should not reach this part of the program.
            This means the model has inaccuracies and needs to be optimized.
            This can be due to several factors: unbalanced dataset, insufficient
            instances, lack of attributes, etc.
            --------------------------------------------------------------------  */
        if (probabilities[prediction] < 0.8) {
            System.out.println("\n\n\n=====================================================================================================");
            System.out.println("\n" + CYANBG + "** The model could not perform a sufficient prediction due to the lack of data. **" + RESET);
            switch (predictedParty) {
                case "Democrat" ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\n Accuracy: {3}{4}%{5}",  CYANBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Republican", "Constitutional" ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\n Accuracy: {3}{4}%{5}",  REDU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Socialist", "Green" ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\n Accuracy: {3}{4}%{5}",  GREENBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                default ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\n Accuracy: {3}{4}%{5}",  CYAN, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));

            }
            System.out.println("=====================================================================================================");
//          System.out.println("Last predicted party based on confidence: " + CYAN + predictedParty + RESET);
        }

    }
}
