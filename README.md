# AI Survey Program ![Header](https://img.shields.io/badge/-Project-ff6600)
**AI/ML Political Affiliation Prediction System**

**Written in:** Java  
**Author:** Aaron Ballesteros  
**Course:** Computer Science 311 - Artificial Intelligence  
**Date:** March 15, 2023

## Table of Contents
- [About](#about)
- [Features](#features)
- [Dependencies](#dependencies)
- [Implementation Details](#implementation-details)
- [Visuals](#visuals)
- [Version History](#version-history)

## About
Data mining is pivotal in understanding customer/member behaviors. This Java program leverages machine learning to predict a user's political leaning based on a survey. The core objective is for the program to guess a user's political party before they complete the survey, enhancing user experience and data accuracy.

## Features
- **Dynamic Survey System**: Presents questions with answer options varying based on political beliefs.
- **Data Collection & Storage**: Collects and stores user responses efficiently.
- **Advanced Prediction**: Uses a trained machine learning model for accurate political affiliation prediction.
- **Data Visualization**: Visual representations of the collected data for better insights.

## Dependencies
The program harnesses the power of the `weka` library for machine learning and data classification.
```java
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
```

## Implementation Details

### **Data Collection**

The program likely incorporates standard Java I/O methods to interactively gather user input. After collecting responses, they might be stored in separate CSV files corresponding to each political party.

```java
public void collectUserData() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter your response: ");
    String response = scanner.nextLine();
    // Additional logic to process and store the response
    appendToCSV(determinePartyFile(response), response);
}
```

### **Machine Learning Model**

With the help of the `weka` library, a classifier (e.g., J48) is trained on the collected data to build a prediction model.

```java
public void trainClassifier() {
    // Load data
    Instances trainingData = new Instances(new BufferedReader(new FileReader("data.csv")));
    trainingData.setClassIndex(trainingData.numAttributes() - 1);

    // Build classifier
    Classifier classifier = new J48();
    classifier.buildClassifier(trainingData);
}
```

### **Prediction Mechanism**

Once the model is trained, it can be used to predict a user's political affiliation based on their survey responses.

```java
public String predictAffiliation(String userInput) {
    // Convert userInput into an Instance format
    Instance userInstance = ...;

    // Predict
    double predictedClass = classifier.classifyInstance(userInstance);
    String predictedAffiliation = trainingData.classAttribute().value((int) predictedClass);

    return predictedAffiliation;
}
```

### **Data Handling**

The program will have functions dedicated to reading and writing data, especially for interacting with CSV files.

```java
public void readFromCSV(String fileName) {
    // Logic to read from CSV
    ...
}

public void appendToCSV(String fileName, String data) {
    // Logic to append data to CSV
    ...
}
```

## Visuals
### Program Preview
![](https://github.com/opratrx/ev6_partyprediction/blob/master/images/CleanShot%202023-03-22%20at%2005.05.09.gif)

### Data Visualization
![](https://github.com/opratrx/ev6_partyprediction/blob/master/images/CleanShot%202023-10-08%20at%2021.57.23.gif)

## Version History
- **Version 1 (3/20/23)**: Initial Commit.
- **Version 2 (3/22/23)**: UI Design, Dataset Data addition, Datawaste removal, Bonus question, Code cleanup.
- **Version 3 (3/22/23)**: Data storages, CSV Reading/Writing enhancements.
