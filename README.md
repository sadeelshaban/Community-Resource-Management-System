# Community Resource Management System (CRMS)

A comprehensive Java-based application designed to manage community aid resources, beneficiaries, volunteers, and aid distributions for nonprofit organizations.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Architecture](#architecture)
- [Key Classes](#key-classes)
- [Usage](#usage)
- [Screenshots](#screenshots)

## 🎯 Overview

The Community Resource Management System (CRMS) is a desktop application that helps nonprofit organizations efficiently manage:
- **Beneficiaries**: People in need who can request aid
- **Volunteers & Staff**: Organization members who manage and distribute aid
- **Aid Items**: Various types of aid including food, clothing, medicine, and hygiene packages
- **Distributions**: Tracking of aid distribution to beneficiaries

The system is built in two stages:
- **Stage 1**: Core business logic and data models
- **Stage 2**: Graphical User Interface (GUI) using Java Swing

## ✨ Features

### User Management
- Register and manage beneficiaries
- Register volunteers and organization staff
- Unique ID validation for all users
- User authentication system

### Aid Item Management
- Add different types of aid items:
  - Food Packages (with expiry dates)
  - Clothing Packages
  - Medicine (with expiry dates)
  - Female Hygiene Packages
- Search aid items by keyword
- View available aid items (non-expired and in stock)
- Priority level assignment for aid items

### Request & Distribution System
- Beneficiaries can request aid items
- Staff can assign aid to beneficiaries
- Automatic quantity management
- Distribution tracking with timestamps
- Distribution reports generation

### User Interface
- Modern, user-friendly GUI built with Java Swing
- Welcome screen
- Login and registration screens
- Main menu with role-based access
- Screens for:
  - Adding aid items
  - Viewing available aid
  - Requesting aid (for beneficiaries)
  - Assigning aid (for staff)
  - Distributing aid
  - Viewing distribution reports

## 📁 Project Structure

```
CRMS/
├── src/
│   ├── Stage1/              # Core business logic
│   │   ├── AidItem.java     # Abstract base class for aid items
│   │   ├── AidManagement.java  # Main management class
│   │   ├── Beneficiary.java    # Beneficiary user class
│   │   ├── Person.java         # Base class for all users
│   │   ├── Volunteer.java      # Volunteer user class
│   │   ├── OrganizationStaff.java  # Staff user class
│   │   ├── Distribution.java   # Distribution record class
│   │   ├── Request.java        # Request record class
│   │   ├── FoodPackage.java    # Food aid item
│   │   ├── ClothingPackage.java  # Clothing aid item
│   │   ├── Medicine.java       # Medicine aid item
│   │   ├── FemaleHygienePackage.java  # Hygiene aid item
│   │   └── Distributable.java  # Interface for distribution
│   │
│   └── Stage2/              # GUI components
│       ├── Main.java         # Application entry point
│       ├── WelcomeScreen.java
│       ├── LoginScreen.java
│       ├── RegistrationScreen.java
│       ├── MainMenuScreen.java
│       ├── AddAidItemScreen.java
│       ├── ViewAidScreen.java
│       ├── RequestAidScreen.java
│       ├── AssignAidScreen.java
│       ├── DistributeAidScreen.java
│       └── DistributionReportScreen.java
│
├── resources/
│   └── icons/               # Application icons
│
└── README.md               # This file
```

## 🛠 Technologies Used

- **Java**: Core programming language
- **Java Swing**: GUI framework
- **Java Collections Framework**: ArrayList for dynamic data management
- **Java Streams API**: For efficient data filtering and processing
- **Object-Oriented Programming**: Inheritance, polymorphism, abstraction

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE (Eclipse, IntelliJ IDEA, or NetBeans) or command line compiler

### Running the Application

1. **Clone or download the project**
   ```bash
   cd CRMS
   ```

2. **Compile the project**
   - If using Eclipse: Import the project and let Eclipse compile automatically
   - If using command line:
     ```bash
     javac -d bin src/Stage1/*.java src/Stage2/*.java
     ```

3. **Run the application**
   - From Eclipse: Right-click `Main.java` in `Stage2` package → Run As → Java Application
   - From command line:
     ```bash
     java -cp bin Stage2.Main
     ```

4. **First Steps**
   - The application will start with a Welcome Screen
   - Register a new user or login with existing credentials
   - Navigate through the menu based on your user role

## 🏗 Architecture

### Design Patterns

- **Inheritance**: `Person` is the base class for `Beneficiary`, `Volunteer`, and `OrganizationStaff`
- **Polymorphism**: `AidItem` is abstract with concrete implementations (`FoodPackage`, `ClothingPackage`, etc.)
- **Interface Implementation**: `AidManagement` implements `Distributable` interface

### Data Management

The system uses **ArrayList** for dynamic data management:
- No fixed-size limitations
- Automatic memory management
- Efficient search and filtering using Java Streams API
- Type-safe collections

### Key Components

1. **AidManagement**: Central management class that handles:
   - User registration and authentication
   - Aid item management
   - Request processing
   - Distribution tracking

2. **Person Hierarchy**: 
   - `Person` (abstract base)
     - `Beneficiary` (can request aid)
     - `Volunteer` (can deliver aid)
     - `OrganizationStaff` (can manage system)

3. **AidItem Hierarchy**:
   - `AidItem` (abstract base)
     - `FoodPackage` (expirable)
     - `ClothingPackage` (non-expirable)
     - `Medicine` (expirable)
     - `FemaleHygienePackage` (non-expirable)

## 📚 Key Classes

### Stage1 Package

#### `AidManagement`
- Manages all system operations
- Uses ArrayList for people, aid items, and distributions
- Provides methods for:
  - User registration and lookup
  - Aid item management
  - Request and distribution processing
  - Report generation

#### `Person` (Abstract)
- Base class for all users
- Contains: ID, name, password, address, phone

#### `Beneficiary extends Person`
- Represents people in need
- Has family size information
- Can make multiple aid requests
- Uses ArrayList for request management

#### `AidItem` (Abstract)
- Base class for all aid items
- Contains: ID, name, quantity, priority level
- Abstract methods: `getInfo()`, `getExpiryDate()`

### Stage2 Package

#### `Main`
- Application entry point
- Initializes `AidManagement` and shows `WelcomeScreen`

#### GUI Screens
- **WelcomeScreen**: Initial welcome and navigation
- **LoginScreen**: User authentication
- **RegistrationScreen**: New user registration
- **MainMenuScreen**: Role-based main menu
- **AddAidItemScreen**: Add new aid items
- **ViewAidScreen**: Browse available aid
- **RequestAidScreen**: Beneficiaries request aid
- **AssignAidScreen**: Staff assign aid to beneficiaries
- **DistributeAidScreen**: Distribute specific items
- **DistributionReportScreen**: View distribution history

## 💻 Usage

### For Beneficiaries

1. **Register/Login**: Create an account or login
2. **View Available Aid**: Browse available aid items
3. **Request Aid**: Select items and specify quantities
4. **Track Requests**: View your request status

### For Staff/Volunteers

1. **Login**: Access with staff credentials
2. **Add Aid Items**: Register new aid items in the system
3. **View Requests**: See beneficiary requests
4. **Assign Aid**: Process and assign aid to beneficiaries
5. **Distribute Aid**: Record aid distributions
6. **Generate Reports**: View distribution reports

### System Features

- **Automatic Expiry Checking**: Only non-expired items are shown as available
- **Quantity Management**: Automatic deduction when aid is distributed
- **Request Processing**: Multiple requests can be processed at once
- **Search Functionality**: Search aid items by name/keyword
- **Priority Levels**: Aid items have priority levels (1-10)

## 🎨 Screenshots

The application features a modern, clean interface with:
- Intuitive navigation
- Color-coded buttons with hover effects
- Responsive tables for data display
- Clear visual feedback for user actions

## 🔧 Recent Improvements

### Refactoring (Latest Update)
- ✅ Converted all arrays to **ArrayList** for dynamic data management
- ✅ Implemented **Java Streams API** for efficient data processing
- ✅ Added null-safety checks throughout the codebase
- ✅ Improved `assignAidToBeneficiary()` to handle multiple requests
- ✅ Enhanced code quality with modern Java features
- ✅ Updated all return types from arrays to `List<T>`

## 📝 Notes

- The system uses in-memory storage (data is lost when application closes)
- For production use, consider adding database persistence
- All dates use `java.util.Date` for compatibility
- The system supports multiple user roles with different access levels

## 👥 User Roles

1. **Beneficiary**: Can view and request aid
2. **Volunteer**: Can deliver aid and view distributions
3. **Organization Staff**: Full access to all features

## 🔐 Security Notes

- Passwords are stored in plain text (for educational purposes)
- In production, implement proper password hashing
- Consider adding role-based access control (RBAC)

## 📄 License

This project is for educational purposes.

## 🤝 Contributing

This is an educational project. Feel free to fork and enhance!

---

**Built with ❤️ using Java and Java Swing**
