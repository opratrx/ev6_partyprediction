/*
 * Assignment for CS311 - Artificial Intelligence.
 * This program simply surveys a user and guesses their political party affiliation
 * and determines whether the user is a member of the party or not.
 * @author Aaron Ballesteros
 * Copmputer Science 311 - Study.com
 * AI Survey Program
 * Created: March 15, 2023
 */

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
    private static final String RED, REDBG, REDBU, GREEN, GREENBU, YELLOW, YELLOWBU, YELLOWBG, GRAY, BLUEBG, CYANBG, CYANBU, CYAN, RESET;
    static {
        RED = "\u001B[31m";
        REDBG = "\033[1;30m\u001B[41m";
        REDBU = "\033[1;31m\033[4;31m";
        GREEN = "\033[0;32m";
        GREENBU = "\033[1;32m\033[4;32m";
        YELLOW = "\033[0;33m";
        YELLOWBU = "\033[1;33m\033[4;33m";
        YELLOWBG = "\033[1;30m\033[43m";
        GRAY = "\u001b[27;1m";
        BLUEBG = "\033[1;30m\033[44m";
        CYAN = "\u001B[36m";
        CYANBG = "\u001B[4m\u001B[46m\u001B[30m";
        CYANBU = "\033[1;96m\033[4;36m";
        RESET = "\033[0m";
    }


    // Input Validation Function to Check Certain Inputs are Valid for each Respective Attribute
    private static boolean validateInput(int index, String input) {
        if (index == 16) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "education", "healthcare", "environment"
            ));
            return validAnswers.contains(input);
        } else if (index == 17) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "none", "first_trimester", "second_trimester", "third_trimester", "banned"
            ));
            return validAnswers.contains(input);
        } else if (index == 18) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "none", "some", "strict"
            ));
            return validAnswers.contains(input);
        } else if (index == 19) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "high", "medium", "low"
            ));
            return validAnswers.contains(input);
        } else if (index == 20) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "privacy", "security", "equality", "individual", "freedom"
            ));
            return validAnswers.contains(input);
        } else if (index == 21) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "open_borders", "amnesty", "merit_based", "family_based", "closed_borders"
            ));
            return validAnswers.contains(input);
        } else if (index == 22) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "capitalism", "socialism", "communism", "mixed", "federal_authority"
            ));
            return validAnswers.contains(input);
        }
        else if (index == 23) {
            Set<String> validAnswers = new HashSet<>(Arrays.asList(
                    "Republican", "Socialist", "Democrat", "Green", "Libertarian", "Independent", "None"
            ));
            return validAnswers.contains(input);
        } else {
            Set<String> validAnswers = new HashSet<>(Arrays.asList("yes", "no"));
            return validAnswers.contains(input);
        }
    }


    // Main Method to Run the Program
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




        /*  Survey Question Bank
         * -------------------------------------------------------------------------------------------------
         *  This is a question bank to store all the questions that the user will be asked.
         *  These questions are then used to determine if the user is a member of the party or not.
         *  Note: the questions match the attributes in the dataset and are indexed by the attribute number.
         */
        String[] questions = {
                "Do you support stricter gun control laws?",                                                                                           // Democratic       Question 1
                "Do you support the legalization of same-sex marriage?",                                                                               // Democratic       Question 2
                "Do you support a path to citizenship for undocumented immigrants?",                                                                   // Democratic       Question 3
                "Do you believe in the right to access abortion services?",                                                                            // Democratic       Question 4
                "Do you believe that the government should provide financial assistance to college students?",                                         // Neutral          Question 5
                "Do you support measures to protect consumer privacy and data security online?",                                                       // Neutral          Question 6
                "Do you think the government should invest more in public infrastructure?",                                                            // Neutral          Question 7
                "Do you think the government should invest more in scientific research and development?",                                              // Neutral          Question 8
                "Do you support the establishment of a single-payer healthcare system?",                                                               // Socialist        Question 9
                "Do you believe that the government should provide a universal basic income to all citizens?",                                         // Socialist        Question 10
                "Do you support stronger labor rights and protections?",                                                                               // Socialist        Question 11
                "Do you support progressive taxation? (wealthy pay a higher percentage of their income in taxes)",                                     // Socialist        Question 12
                "Do you support the legalization of recreational drugs, such as marijuana?",                                                           // Libertarian      Question 13
                "Do you oppose government regulation of the internet and online privacy?",                                                             // Libertarian      Question 14
                "Do you believe in a free-market economy with minimal government intervention?",                                                       // Libertarian      Question 15
                "Do you support reducing taxes and government spending?",                                                                              // Libertarian      Question 16
                "Which of the following issues is most important to you:",                                                                             // Multiple Choice  Question 17
                "Do you believe that there should be no limitations on abortion?",                                                                     // Multiple Choice  Question 18
                "Do you believe that there should be no regulations on companies to reduce pollution?",                                                // Multiple Choice  Question 19
                "Do you believe that the government should maintain current levels of military spending:",                                             // Multiple Choice  Question 20
                "What should the government prioritize: national security, individual privacy, equality?",                                             // Multiple Choice  Question 21
                "What is your stance on open borders as an immigration policy?",                                                                       // Multiple Choice  Question 22
                "What is your stance on capitalism as an economic system?",                                                                            // Multiple Choice  Question 23
        };




        /* Survey Answers Bank
         * -------------------------------------------------------------------------------
         * Use this answer bank to display the answers to the survey questions.
         * Note: this is not an answer key. It is just a list of answers for display.
         * so the use knows what choices they have when input is asked for each question.
         * If you want to add more answer choices, you need to add them to the dataset with
         * the corresponding attribute and question number. You will also need to add the
         * new answers to the input validateInput() function.
         */
        String[] answerKey = {
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"yes\", \"no\"]",
                "[\"education\", \"healthcare\", \"environment\"]",
                "[\"none\", \"first_trimester\", \"second_trimester\", \"third_trimester\", \"banned\"]",
                "[\"none\", \"some\", \"strict\"]",
                "[\"high\", \"medium\", \"low\"]",
                "[\"privacy\", \"security\", \"equality\", \"individual\", \"freedom\"]",
                "[\"open_borders\", \"amnesty\", \"merit_based\", \"family_based\", \"closed_borders\"]",
                "[\"capitalism\", \"socialism\", \"communism\", \"mixed\", \"federal_authority\"]",
                "[\"Republican\", \"Socialist\", \"Democrat\", \"Green\", \"Libertarian\", \"Independent\", \"None\"]"
        };



        // Display Main Menu + Instructions
        System.out.println(GREEN + "\n\n\t\t\t\t\t\tML Survey: Party Prediction \uD83E\uDD14" + YELLOW + " by Aaron Ballesteros");
        System.out.println(GREEN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n");
        System.out.println("\t\t◃ Simply type the answer to the questions with the choices provided." +
                "\n\t\t◃ The prediction is a " + YELLOW + "general idea" + GREEN + " of what party you belong to." +
                "\n\t\t◃ If the confidence reaches " + REDBU + "90% or more" + GREEN + ", the survey will end." +
                "\n\t\t◃ Left Wing:  " + BLUEBG + " Democratic, Green, Socialist " + GREEN +
                "\n\t\t◃ Right Wing: " + REDBG + " Republican, Libertarian Party " + GREEN +
                "\n\t\t◃ Middle: " + YELLOWBG + " Libertarian Party " + GREEN + "\n");

        System.out.println(GREEN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");


        //  Datatype declarations and initializing variables for the survey loop
        Scanner scanner = new Scanner(System.in);
        String predictedParty = null;
        double[] probabilities = new double[0];
        int prediction = 0;
        int currentQuestion = 0;


        /*  Survey Loop - Starts Here
         *  --------------------------------------------------------------
         *  While the user hasn't answered all the questions, keep looping
         */
        for (int i = 0; i < questions.length; i++) {

            //  Questions ticker
            currentQuestion++;

            // Survey Progress Bar
            int progress = (i * 100) / questions.length;
            System.out.print(YELLOW + "\n\t\tSurvey Progress [");
            int bars = (progress / 10);
            for (int p = 0; p < 10; p++) {
                if (p < bars) {
                    System.out.print(GREENBU + "█");
                } else {
                    System.out.print(REDBU + "▒");
                }
            }
            System.out.println(YELLOW + "] " + progress + "%" + RESET);

            //  Border
            System.out.println(GREEN + "------------------------------------------------------------------------------------------------------" + RESET);


            // Question Counter
            System.out.println("\n✳️ Question " + GREEN + (currentQuestion) + " of " + questions.length);


            /* Question Prompt + Answers
             * --------------------------
             * This is where the question prompt and answers are displayed.
             * The question prompt is displayed at the top of the survey.
             * The choices are displayed at the bottom of the survey.
             */
            System.out.println("\n\t" + YELLOW + "＞ " + questions[i] + RESET);
            System.out.println(GREEN + "\n------------------------------------------------------------------------------------------------------" + RESET);
            System.out.println("\n\t\t\t" + GREENBU + "{Choices}" + RESET + ": " + answerKey[currentQuestion - 1]);
            System.out.print("\t\t\t" + GREENBU + "[Answer]" + RESET + ": ");


            // User Input + Input Validation
            String answer = "";
            boolean isValidInput = false;
            while (!isValidInput) {
                answer = scanner.next().trim().toLowerCase();
                isValidInput = validateInput(i, answer);
                if (!isValidInput) {
                    System.out.println(RED + "\t\t\tInvalid input. Please enter one of the given options." + RESET);
                    System.out.print(GREENBU + "\t\t\t[Answer]" + RESET + ": ");
                }
            }
            userInput.setValue(attributes.get(i), answer);


            // Predict the political party
            probabilities = classifier.distributionForInstance(userInput);
            prediction = (int) classifier.classifyInstance(userInput);
            predictedParty = classAttribute.value(prediction);


            /* Survey Statistics + Current Prediction + Accuracy
             * -------------------------------------------------
             * Prints out the survey statistics after each question is answered.
             * Color codes the current prediction and accuracy based on the party prediction of the user.
             */
            System.out.print("\n\n" + GREEN + "\t\t\t\t🧪 Stats" + RESET + "\n");
            System.out.println(GRAY + "\t\t\t┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET);
            switch (predictedParty) {
                case "Democrat" ->
                        System.out.println(format("{0}\t\t\t\t Current Analysis: {1}{2}{3}\n\t\t\t\t Accuracy: {4}{5}%{6}", GREEN, CYANBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Republican", "Libertarian" ->
                        System.out.println(format("{0}\t\t\t\t Current Analysis: {1}{2}{3}\n\t\t\t\t Accuracy: {4}{5}%{6}", GREEN, REDBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Socialist" ->
                        System.out.println(format("{0}\t\t\t\t Current Analysis: {1}{2}{3}\n\t\t\t\t Accuracy: {4}{5}%{6}", GREEN, YELLOWBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Green" ->
                        System.out.println(format("{0}\t\t\t\t Current Analysis: {1}{2}{3}\n\t\t\t\t Accuracy: {4}{5}%{6}", GREEN, GREENBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                default ->
                        System.out.println(GREEN + "\t Current Analysis: " + YELLOW + predictedParty + GREEN + "\n\t Accuracy: " + REDBU + (probabilities[prediction] * 100) + "%" + RESET);
            }
            System.out.println(GRAY + "\t\t\t┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);


            //  Survey Conditional Statement(s)
            /* If the confidence is above 90%, stop the survey
             * -------------------------------------------------
             * Conditional statement that stops the survey if the confidence is above 90%.
             * Additional @param is commented out: if (probabilities[prediction] * 100) > 80
             * Uncomment the if statement to stop the survey if the confidence never reaches 90% or higher.
             */
            if (probabilities[prediction] > 0.9) {
                // Print final analysis determining the users party
                System.out.println("\n\n\t\t\t\t\t   " + CYANBG + "*** Analysis Finished! Your political party is " + predictedParty + " ***" + RESET);
                // Borderline
                System.out.println("\n" + GREEN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");
                break;
            }
/*          Additional Parameter if the confidence is above 80%, stop the survey.
            if (probabilities[prediction] > 0.8) {
                System.out.println("\n\nYour political party leans toward: " + YELLOW + predictedParty + RESET);
                System.out.println(YELLOW + "Thank you for taking the survey!" + RESET);
                break;
            }
*/
            // Borderline
            System.out.println("\n" + GREEN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");
        }
        // End of Survey Loop



        /* Bonus Question - After Survey is Finished
         * -----------------------------------------
         * After the analysis is complete, this is where the end of the survey bonus question is displayed.
         * Asks for the users party affiliation and determines if the survey was successful or not.
         */

        // Survey Progress Bar - 100%
        int progressDone = 100;
        System.out.print(YELLOW + "\n\t\tSurvey Progress [");
        for (int p = 0; p < 10; p++) {
            System.out.print(GREENBU + "█");
        }
        System.out.println(YELLOW + "] " + progressDone + "%" + RESET);




        // End of Survey - Bonus Question
        System.out.println(GREEN + "------------------------------------------------------------------------------------------------------" + RESET);
        System.out.println("\n✅ Bonus Question " + GREEN + "1 of 1" + RESET);
        System.out.println(YELLOW + "\n\t＞ What is your political party affiliation? " + RESET);
        System.out.println(GREEN + "\n------------------------------------------------------------------------------------------------------" + RESET);
        System.out.println(GREENBU + "\n{Choices}" + RESET + ": " + answerKey[23]);
        System.out.print(GREENBU + "[Answer]" + RESET + ": ");
        Scanner partyScanner = new Scanner(System.in);
        String userParty = "";




        // User Input + Input Validation
        boolean isValidInput = false;
        while (!isValidInput) {
            userParty = partyScanner.next().trim().toLowerCase();
            Set<String> validParties = new HashSet<>(Arrays.asList("democrat", "republican", "independent", "none" , "libertarian", "green", "socialist"));
            isValidInput = validParties.contains(userParty);
            if (!isValidInput) {
                System.out.println(RED + "Invalid input. Please enter one of the given options." + RESET);
                System.out.print(GREENBU + "[Answer]" + RESET + ": ");
            }
        }




        // End of Survey Message
        if (userParty.equals(predictedParty.toLowerCase())) {
            System.out.println("\n\n" + YELLOW + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("\n\t\tGlad to hear that it's a match! 🎉\n\n\t\tThank you for taking the survey!\n");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            System.out.println("\n\n\n" + YELLOW + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("\n\t\tThere could be several reasons why this did not match your political party:\n\n"
            + "\t\t👉 The model needs more data for learning.\n" + "\t\t👉 Data waste in the dataset resulted to bias.\n" + "\t\t👉 The data is a biased generalization based off of personal research. \n\t\t(this dataset is personally made by me and not by professional researchers.)\n" + "\t\t👉 Spamming answers to test out the program.\n\n\t\tThank you for taking the survey!\n" + RESET);
            System.out.println(YELLOW + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
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
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\nAccuracy: {3}{4}%{5}",  CYANBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Republican", "Libertarian" ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\nAccuracy: {3}{4}%{5}",  REDBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                case "Socialist", "Green" ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\nAccuracy: {3}{4}%{5}",  GREENBU, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));
                default ->
                        System.out.println(format("Last predicted party based on confidence: {0}{1}{2}\nAccuracy: {3}{4}%{5}",  CYAN, predictedParty, GREEN, GREENBU, probabilities[prediction] * 100, RESET));

            }
            System.out.println("\n=====================================================================================================");
        }

    }
}
