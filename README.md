![Banner](https://i.imgur.com/zUNbpdy.png)


## Contents

- [Submission or project name](#submission-or-project-name)
  - [Contents](#contents)
  - [Short description](#short-description)
    - [What's the problem?](#whats-the-problem)
    - [How can technology help?](#how-can-technology-help)
    - [The idea](#the-idea)
  - [Demo video](#demo-video)
  - [The architecture](#the-architecture)
  - [Long description](#long-description)
  - [Project roadmap](#project-roadmap)
  - [Getting started](#getting-started)
  - [Live demo](#live-demo)
  - [Built with](#built-with)
  - [Contributing](#contributing)
  - [Versioning](#versioning)
  - [Authors](#authors)
  - [License](#license)
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

1. The user navigates to the site and uploads a video file.
2. Watson Speech to Text processes the audio and extracts the text.
3. Watson Translation (optionally) can translate the text to the desired language.
4. The app stores the translated text as a document within Object Storage.
5. 

## Long description

[More detail is available here](./docs/DESCRIPTION.md)

## Project roadmap

The project currently does the following things.

- Feature 1
- Feature 2
- Feature 3

It's in a free tier IBM Cloud Kubernetes cluster. In the future we plan to run on Red Hat OpenShift, for example.

See below for our proposed schedule on next steps after Call for Code 2021 submission.

![Roadmap](./images/roadmap.jpg)

## Getting started

In this section you add the instructions to run your project on your local machine for development and testing purposes. You can also add instructions on how to deploy the project in production.

- [sample-react-app](./sample-react-app/)
- [sample-angular-app](./sample-angular-app/)
- [Explore other projects](https://github.com/upkarlidder/ibmhacks)

## Live demo

You can find a running system to test at [callforcode.mybluemix.net](http://callforcode.mybluemix.net/).

## Built with

- [IBM Cloudant](https://cloud.ibm.com/catalog?search=cloudant#search_results) - The NoSQL database used
- [IBM Cloud Functions](https://cloud.ibm.com/catalog?search=cloud%20functions#search_results) - The compute platform for handing logic
- [IBM API Connect](https://cloud.ibm.com/catalog?search=api%20connect#search_results) - The web framework used
- [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency management
- [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

## Authors

<a href="https://github.com/Call-for-Code/Project-Sample/graphs/contributors">
  <img src="https://contributors-img.web.app/image?repo=Call-for-Code/Project-Sample" />
</a>

- **Billie Thompson** - _Initial work_ - [PurpleBooth](https://github.com/PurpleBooth)

## License

This project is licensed under the Apache 2 License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments





Separate repositories:
* [Pudu helpers](https://github.com/felipezxkq/Pudu_helpers)



# The solution
## 1. Users can either search a product or scan its barcode

![Search](https://i.imgur.com/PQI7q07.png?2)
![Scan](https://i.imgur.com/uLBjfZB.png?1)

## 2. Nutritional information, environment impact and health concerns are displayed

![Nutritional](https://i.imgur.com/3SVD1vb.png?1)
![Enviroment](https://i.imgur.com/PSbak5g.jpg?1)

## 3. Share food nutritional and environment data with others. Make it a social experience!

![Social](https://i.imgur.com/udX7iLk.png?1))


# What we are working on

## 1. Daily lists or diets
Users can log their foods, know their macros, vitamins and minerals consumption while also knowing the environment impact of their diet

## 2. Generating relevant knowledge through IBM's Watson Knowledge Studio
Currently we have a very small number of products (~1000) registered, and an even smaller number 
of users. However, using this small dataset we are already trying to get some valuable insights

## 3. Use IBM Food Trust in order to get valuable knowledge on supply chains and distribution 


## Pudu video pitch (spanish)

[![](https://i.imgur.com/k5TpZ3X.jpg)](https://www.youtube.com/watch?v=yvB5EgetAxU)

## Authors

* Diego Jiménez
* Cristina Pradenas
* Gabriel Pradenas
* Felipe Jiménez
