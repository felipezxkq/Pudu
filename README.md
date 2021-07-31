![Banner](https://i.imgur.com/zUNbpdy.png)


## Contents

- [Pudu](#pudu)
  - [Contents](#contents)
  - [Short description](#short-description)
    - [What's the problem?](#whats-the-problem)
    - [How can technology help?](#how-can-technology-help)
    - [The idea](#the-idea)
  - [Demo video](#demo-video)
  - [The architecture](#the-architecture)
  - [Project roadmap](#project-roadmap)
  - [Getting started](#getting-started)
  - [Built with](#built-with)
  - [Authors](#authors)
  - [Separate repositories](#separate-repositories)
  - [Acknowledgments](#acknowledgments)

## Short description

### What's the problem?

Food production greenhouse emissions is one of the most important causes of climate change. In order to make the best choices, concious consumers need a tool to get information on food environment impact, nutritional data and health concerns. The Nutritional App Market is growing rapidly, but far from helping the planet it is unexpectedly incetivizing the consumption of foods with higher carbon footprints.
. 

### How can technology help?

Fitness enthusiasts that want to contribute towards fighting climate change will have access to an easy-to-use platform with nutritional and environmental information to help them make the best choices.

### The idea

We collect data from public databases and user contributions, then we make classifications based on general to more specific foods. This way we give general estimations 
of product environment impact such as CO2 emission, water usage, energy usage and land usage in the making of a product. The goal is that the users can easily share and compare
environment impact vs nutritional value in foods, so they themselves can make better choices for the planet, while also helping them to achieve their personal goals.

## Demo video

[![](https://i.imgur.com/k5TpZ3X.jpg)](https://www.youtube.com/watch?v=yvB5EgetAxU)

## The architecture

![Video transcription/translation app](https://i.imgur.com/74PlgqM.png)

1. Data is added to Firebase through user input and open databases such as OpenFoodFacts.
2. Data is cleaned and processesed using Python and IBM Watson Studio
3. The Android application gives user access to the data and all the functionalities.
4. Users can search products using IBM's Speech to Text.
5. Users can search products using the scanner or the search bar.
6. Product information is displayed.
7. Users can share the product data in social media together with inputs/comments of their own.
8. Users can track their fitness/environmental goals.
9. Users can track their diets, log their daily intakes.

## Project roadmap

The project currently does the following things.

- Users can search or scan barcodes to get nutritional/environmental information.
- Users can add product data from packages information.
- Product data is regularly processed and classified.

Currently we only have an Android application (not released), we plan on having a web application hosted on IBM Cloud.

See below for our proposed schedule on next steps after Call for Code 2021 submission.

![Roadmap](https://i.imgur.com/0wzKLhe.png)

## Getting started

Send us a email to puduapp@gmail.com so we can add you to Android's internal testing.


## Built with

- [Firebase](https://firebase.google.com) - The NoSQL database used
- [IBM Watson Studio](https://www.ibm.com/cloud/watson-studio) - The software platform to make data analisis
- [Kotlin](https://kotlinlang.org) - The language used for Android development
- [IBM Watson Speech to Text](https://www.ibm.com/cloud/watson-speech-to-text) - The service used to translate speech to text
- [Facebook SDK](https://www.ibm.com/cloud/watson-speech-to-text) - SDK used to share through facebook apps
- [ZXing Android Embedded](https://github.com/journeyapps/zxing-android-embedded) - Barcode scanning library for Android


## Authors

* Diego Jiménez
* Cristina Pradenas
* Gabriel Pradenas
* Felipe Jiménez


## Separate repositories
* [Pudu helpers](https://github.com/felipezxkq/Pudu_helpers)


## Acknowledgments

* OpenFoodFacts: Food products database made by everyone, for everyone.




