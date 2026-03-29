# Behavioral Marketing

## Overview
This project aims to develop an application capable of simulating a product sales system, integrating behavioral marketing techniques to provide a personalized experience for both end users and administrators.
The application enables dynamic and efficient management of product catalogs and promotional offers, while ensuring an intuitive user interface.

## Objectives
-	Simulate a digital product sales environment
-	Provide personalized product recommendations
-	Implement basic behavioral marketing techniques
-	Ensure efficient product and offer management

## User Roles

The system supports two main access modes:

### Administrator
- Manages the product catalog
- Creates and customizes targeted offers
- Monitors system behavior

### User
- Browses the product catalog
- Selects desired products
- Completes purchases

## Personalization Approach

To personalize offers, the system implements a simplified version of the Bag of Words model combined with the TF-IDF (Term Frequency–Inverse Document Frequency).

This approach allows the application to:
- Analyze user purchasing behavior
- Generate targeted and personalized offers

## Design Patterns Used

The system architecture leverages several well-known design patterns to improve modularity, scalability, and maintainability.

- Strategy
- Factory
- Facade
- Observer
- Singleton
