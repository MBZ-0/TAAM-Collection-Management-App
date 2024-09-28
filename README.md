# TAAM Collection Management App

## Overview

This project is the Collection Management System for the Toronto Asian Art Museum (TAAM), developed as part of the CSCB07 course project. It includes various functionalities such as viewing, adding, searching, and removing items from the collection, as well as generating reports. The system supports both images and videos of items, enhancing the detailed representation of the collection. Additionally, the app is linked to a Firebase server, ensuring secure data storage and real-time synchronization across devices.

## Demo Video

<div>
  https://github.com/user-attachments/assets/de334da3-b7cb-4d24-b236-e136d505063d
</div>

## Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/282442f8-4719-478a-9b59-59446f2a62a3" alt="AdminLoginScreen" width="200"/>
  <img src="https://github.com/user-attachments/assets/c974371e-f2f8-4e62-905b-4b945a2d8ae1" alt="AdminHomeScreen" width="200"/>
  <img src="https://github.com/user-attachments/assets/924aeb56-2cc9-4849-8f1b-afa0ab62e45d" alt="AddItemFragment" width="200"/>
  <img src="https://github.com/user-attachments/assets/53f9cb4a-3069-4eaf-ae29-65a2ba7471c4" alt="ViewFragment" width="200"/>
  <img src="https://github.com/user-attachments/assets/6bf554ee-cda2-43ca-83b4-7611df611f65" alt="SearchFragment" width="200"/>
  <img src="https://github.com/user-attachments/assets/b89beaba-efcb-4c27-8b27-93fa537901c6" alt="CustomExpandableListView" width="200"/>
  <img src="https://github.com/user-attachments/assets/1ad1d6aa-ff1c-4331-96b1-d632239edfee" alt="ReportItem" width="200"/>
  <img src="https://github.com/user-attachments/assets/057e62af-25e6-4874-87be-b798a2fdf264" alt="RemoveItemFragment" width="200"/>
</p>

### PDF Report
[Report.pdf](https://github.com/user-attachments/files/16537191/Report.pdf)

## Features

1. **Main Screen**: Displays the collection items in a table format with options to view, search, and navigate through pages.
2. **Admin Login**: A security screen for admin login that redirects to the admin home screen upon successful authentication.
3. **Admin Home**: Provides options for viewing, searching, adding, removing, and generating reports for collection items.
4. **Add Function**: Allows admins to add new items with details like lot number, name, category, period, description, and media (pictures/videos).
5. **Search Function**: Enables searching for items based on various criteria and viewing detailed results.
6. **Remove Function**: Facilitates the removal of selected items from the collection with a confirmation prompt.
7. **Report Function**: Generates printable PDF reports based on various criteria such as lot number, name, category, and period.

## Usage

1. **Home Screen**: 
   - Navigate through the collection.
   - Click on "Admin" for the admin login screen.

2. **Admin Login**: 
   - Enter username and password.
   - Click "Log in" to access admin functionalities.

3. **Admin Home**: 
   - Use the "View", "Search", "Add", "Remove", and "Report" buttons to manage the collection.

4. **Adding Items**: 
   - Fill in the details and upload media.
   - Click "Submit" to add the item.

5. **Searching Items**: 
   - Enter search criteria and click "Submit".
   - View detailed results or go back to the search screen.

6. **Removing Items**: 
   - Select an item and click "Remove".
   - Confirm the removal by clicking "Yes" or cancel by clicking "Cancel".

7. **Generating Reports**: 
   - Select the report criteria and click "Submit" to generate a PDF report.
