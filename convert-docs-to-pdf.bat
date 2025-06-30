@echo off
echo ===========================================# Convert README
if exist "README.md" (
    echo Converting README.md...
    markdown-pdf README.md -o README.pdf
    if exist "README.pdf" (
        echo ✓ README.pdf created successfully!
    ) else (
        echo ✗ Failed to create README.pdf
    )
) else (
    echo Warning: README.md not found!
)

# Convert Complete Guide
if exist "QA_ENGINEER_COMPLETE_GUIDE.md" (
    echo Converting QA_ENGINEER_COMPLETE_GUIDE.md...
    markdown-pdf QA_ENGINEER_COMPLETE_GUIDE.md -o QA_ENGINEER_COMPLETE_GUIDE.pdf
    if exist "QA_ENGINEER_COMPLETE_GUIDE.pdf" (
        echo ✓ QA_ENGINEER_COMPLETE_GUIDE.pdf created successfully!
    ) else (
        echo ✗ Failed to create QA_ENGINEER_COMPLETE_GUIDE.pdf
    )
) else (
    echo Warning: QA_ENGINEER_COMPLETE_GUIDE.md not found!
)

# Convert Quick Start
if exist "QUICK_START.md" (
    echo Converting QUICK_START.md...
    markdown-pdf QUICK_START.md -o QUICK_START.pdf
    if exist "QUICK_START.pdf" (
        echo ✓ QUICK_START.pdf created successfully!
    ) else (
        echo ✗ Failed to create QUICK_START.pdf
    )
) else (
    echo Warning: QUICK_START.md not found!
)

# Convert Final Summary
if exist "FINAL_SUMMARY.md" (
    echo Converting FINAL_SUMMARY.md...
    markdown-pdf FINAL_SUMMARY.md -o FINAL_SUMMARY.pdf
    if exist "FINAL_SUMMARY.pdf" (
        echo ✓ FINAL_SUMMARY.pdf created successfully!
    ) else (
        echo ✗ Failed to create FINAL_SUMMARY.pdf
    )
) else (
    echo Warning: FINAL_SUMMARY.md not found!
)

# Convert Project Deliverables
if exist "PROJECT_DELIVERABLES.md" (
    echo Converting PROJECT_DELIVERABLES.md...
    markdown-pdf PROJECT_DELIVERABLES.md -o PROJECT_DELIVERABLES.pdf
    if exist "PROJECT_DELIVERABLES.pdf" (
        echo ✓ PROJECT_DELIVERABLES.pdf created successfully!
    ) else (
        echo ✗ Failed to create PROJECT_DELIVERABLES.pdf
    )
) else (
    echo Warning: PROJECT_DELIVERABLES.md not found!
)utomation Framework - PDF Converter
echo ===========================================
echo.

:: Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed!
    echo Please install Node.js from https://nodejs.org/
    echo Then run this script again.
    pause
    exit /b 1
)

:: Install markdown-pdf if not already installed
echo Installing markdown-pdf globally...
npm install -g markdown-pdf

:: Check if installation was successful
markdown-pdf --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Failed to install markdown-pdf!
    echo Please check your internet connection and try again.
    pause
    exit /b 1
)

echo.
echo Converting Markdown files to PDF...
echo.

:: Convert main guide
if exist "QA_ENGINEER_GUIDE.md" (
    echo Converting QA_ENGINEER_GUIDE.md...
    markdown-pdf QA_ENGINEER_GUIDE.md -o QA_ENGINEER_GUIDE.pdf
    if exist "QA_ENGINEER_GUIDE.pdf" (
        echo ✓ QA_ENGINEER_GUIDE.pdf created successfully!
    ) else (
        echo ✗ Failed to create QA_ENGINEER_GUIDE.pdf
    )
) else (
    echo Warning: QA_ENGINEER_GUIDE.md not found!
)

:: Convert summary
if exist "QA_ENGINEER_SUMMARY.md" (
    echo Converting QA_ENGINEER_SUMMARY.md...
    markdown-pdf QA_ENGINEER_SUMMARY.md -o QA_ENGINEER_SUMMARY.pdf
    if exist "QA_ENGINEER_SUMMARY.pdf" (
        echo ✓ QA_ENGINEER_SUMMARY.pdf created successfully!
    ) else (
        echo ✗ Failed to create QA_ENGINEER_SUMMARY.pdf
    )
) else (
    echo Warning: QA_ENGINEER_SUMMARY.md not found!
)

:: Convert README
if exist "README.md" (
    echo Converting README.md...
    markdown-pdf README.md -o README.pdf
    if exist "README.pdf" (
        echo ✓ README.pdf created successfully!
    ) else (
        echo ✗ Failed to create README.pdf
    )
) else (
    echo Warning: README.md not found!
)

echo.
echo ===========================================
echo Conversion completed!
echo ===========================================
echo.
echo Generated PDF files:
if exist "QA_ENGINEER_GUIDE.pdf" echo - QA_ENGINEER_GUIDE.pdf
if exist "QA_ENGINEER_SUMMARY.pdf" echo - QA_ENGINEER_SUMMARY.pdf
if exist "QA_ENGINEER_COMPLETE_GUIDE.pdf" echo - QA_ENGINEER_COMPLETE_GUIDE.pdf
if exist "QUICK_START.pdf" echo - QUICK_START.pdf
if exist "FINAL_SUMMARY.pdf" echo - FINAL_SUMMARY.pdf
if exist "PROJECT_DELIVERABLES.pdf" echo - PROJECT_DELIVERABLES.pdf
if exist "README.pdf" echo - README.pdf
echo.
echo You can now open these PDF files for viewing or sharing.
echo.
pause
