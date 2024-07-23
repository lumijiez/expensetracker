# Expense Tracker App

## Description

Expense Tracker is a web application that helps you keep track of your expenses and incomes. It provides a user-friendly interface to enter and visualize your financial data, making it easier to manage your finances.

![image](https://github.com/lumijiez/ExpenseTrackerFAF/assets/59575049/c81c16fe-10aa-4198-81e3-864578f73575)


## Features

- Single-page application for a smooth and responsive user experience.
- Reactive graph updates to visualize your financial data.
- Ability to add incomes and expenses to your account.
- Full-fledged authorization system to secure your data.
- Hosted database for seamless data storage and retrieval.

## Technological Stack

### Frontend

- Svelte
- Chart.js
- Axios

### Backend

- Spring
- Spring Boot
- Spring Security

### Database

- MySQL
- phpMyAdmin

## Installation Instructions

To run the Expense Tracker application, follow these steps:

1. Clone the project repository to your local machine.

2. Install the required Maven dependencies for the backend. In the project directory, run:
   
3. Run the backend Spring application to start the server.

4. For the frontend, navigate to the `web` directory and run:

```bash
npm install
```

```bash
npm run dev
```

This will start the frontend development server and open the application in your web browser.

Now you can access the Expense Tracker application at http://localhost:5173/auth/login and start tracking your expenses and incomes visually.

Please note that you need to configure the database connection details and other environment-specific settings in the application properties before running the backend.

### Configuration

Make sure to update the configuration files with your specific database settings, security configurations, and other environment variables as needed. You can find these configuration files in the backend project.

Feel free to customize the application further and adapt it to your specific use case.

Happy expense tracking!


