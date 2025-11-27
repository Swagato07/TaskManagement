# Task Management System

## Overview
A practical Java console application for managing tasks and boosting productivity. This is a fully functional task management system that demonstrates core Java programming concepts through real-world application.

**Purpose**: Help users organize work tasks, personal errands, and shopping lists while demonstrating Java core concepts  
**Current State**: Fully functional task management system with statistics and reporting  
**Last Updated**: November 18, 2025

## Project Architecture

### Structure
```
src/
├── TaskManagementApp.java        # Main application with interactive menu
├── TaskManager.java              # Core task management logic (ArrayList, LinkedList)
├── Task.java                     # Abstract base class for tasks
├── WorkTask.java                 # Work-specific tasks (Inheritance)
├── PersonalTask.java             # Personal tasks (Inheritance)
├── ShoppingTask.java             # Shopping tasks with item lists (Inheritance)
├── TaskStatistics.java           # Statistical calculations (Math functions)
├── ReportGenerator.java          # Report generation (StringBuilder)
├── Priority.java                 # Priority levels enum
├── TaskStatus.java               # Task status enum
├── TaskCategory.java             # Task categories enum
├── TaskException.java            # Base custom exception
└── InvalidTaskException.java     # Validation exception
```

### Key Features

**Task Management:**
- Create tasks (Work, Personal, Shopping)
- Update task status and priority
- Mark tasks complete
- Delete tasks
- Search and filter tasks

**Task Types (Polymorphism):**
- **Work Tasks**: Projects, assignments, estimated hours
- **Personal Tasks**: Appointments, recurring tasks, locations
- **Shopping Tasks**: Item lists, budgets, cost tracking

**Organization:**
- Filter by status, category, or priority
- Search by title
- View overdue and upcoming tasks
- Recently completed tasks tracking

**Analytics & Reports:**
- Detailed statistics dashboard
- Completion rate tracking
- Productivity analysis
- Summary and detailed reports

### Java Concepts Demonstrated

1. **Loops**: for, while, enhanced for loops throughout application
2. **Collections**: ArrayList (tasks), LinkedList (recently completed)
3. **StringBuilder**: Report generation with efficient string building
4. **OOP - Inheritance**: Task → WorkTask, PersonalTask, ShoppingTask
5. **OOP - Polymorphism**: Tasks stored and processed polymorphically
6. **Method Overloading**: calculateTotal() with different parameters
7. **Math Functions**: Averages, percentages, min/max, rounding
8. **Type Casting**: Implicit casting in calculations
9. **Date/Time API**: java.time package for due dates, creation dates, time tracking
10. **Enums**: Priority, TaskStatus, TaskCategory with methods
11. **Exception Handling**: Try-catch blocks, custom exceptions for validation
12. **Arrays**: Internal array usage in enum values()

## Recent Changes
- **Nov 18, 2025**: Complete redesign into practical task management system
- Created hierarchical task structure with inheritance
- Implemented comprehensive task management operations
- Added statistics and analytics features
- Created report generation with StringBuilder
- Integrated all Java core concepts into practical application
- Pre-loaded sample data for immediate testing

## How to Use

The application provides an interactive menu with 11 main features:

1. **Create Task**: Add new work, personal, or shopping tasks
2. **View All**: See all tasks with full details
3. **Filter by Status**: View tasks by completion status
4. **Filter by Category**: View tasks by type
5. **Search**: Find tasks by title
6. **Update**: Modify task details
7. **Complete**: Mark tasks as done
8. **Delete**: Remove tasks
9. **Statistics**: View comprehensive analytics
10. **Reports**: Generate summary and detailed reports
11. **Alerts**: Check overdue and upcoming tasks

## Sample Data
The application includes 5 sample tasks demonstrating different features:
- Work tasks with project tracking
- Personal appointments
- Shopping lists with items
- Completed tasks for statistics

## Technical Details
- **Language**: Java (GraalVM 22.3)
- **No external dependencies**: Uses only Java Core API
- **Packages Used**: java.util, java.time, java.lang
- **Design Pattern**: Object-oriented with inheritance hierarchy
