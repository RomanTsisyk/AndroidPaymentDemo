# AndroidPaymentDemo

A demonstration app showcasing integration with Stripe Payment SDK and Google Play Billing within a modern Android application architecture.

## Overview

This repository will contains a sample Android application that demonstrates how to implement both Stripe payments and Google Play Billing using industry best practices. 
The app will be built with Jetpack Compose UI, follows MVVM architecture with Clean Architecture principles, and SOLID design principles.

## Features

  - Stripe Payment SDK for credit card processing
  - Google Play Billing for in-app purchases or subscriptions
  - Clean Architecture with domain, data, and presentation layers
  - MVVM design pattern
  - Repository pattern for data management
  - Declarative UI built with Compose
  - Material Design components
  - Simulates backend responses for demonstration purposes
  - No actual server required to run the demo
  - Realistic payment flow simulation

## Project Structure

```
com.example.androidpaymentdemo/
├── data/
│   ├── mock/
│   ├── repository/
│   └── remote/
├── domain/
│   ├── model/
│   ├── repository/
│   ├── usecase/
│   └── util/
├── di/
├── payment/
│   ├── stripe/
│   └── billing/
└── presentation/
    ├── ui/
    ├── viewmodel/
    ├── screens/
    └── navigation/
```

## Stripe Integration

The app demonstrates:
- Creating payment intents
- Setting up Stripe SDK
- Displaying payment UI
- Processing payments
- Handling payment results


## Acknowledgments

- [Stripe Android SDK](https://github.com/stripe/stripe-android)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
