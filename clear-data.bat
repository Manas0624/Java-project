@echo off
echo Clearing CRM data...
if exist data\customers.dat del data\customers.dat
if exist data\products.dat del data\products.dat
if exist data\orders.dat del data\orders.dat
echo Data cleared successfully.
echo Run the application again to initialize with sample data.
pause 