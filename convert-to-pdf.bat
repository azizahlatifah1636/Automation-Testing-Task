@echo off
REM Script untuk mengkonversi Markdown ke PDF
REM Requires: pandoc dan wkhtmltopdf

echo ========================================
echo   KONVERSI MARKDOWN KE PDF
echo   QA Engineer Guide Converter
echo ========================================
echo.

REM Check if pandoc is installed
where pandoc >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Pandoc tidak ditemukan!
    echo Silakan install pandoc dari: https://pandoc.org/installing.html
    echo.
    echo Alternatif: Gunakan online converter seperti:
    echo - https://www.markdowntopdf.com/
    echo - https://md2pdf.netlify.app/
    echo - https://dillinger.io/
    pause
    exit /b 1
)

echo ✅ Pandoc ditemukan, memulai konversi...
echo.

REM Convert Markdown to PDF
pandoc "QA_ENGINEER_GUIDE.md" ^
    --pdf-engine=wkhtmltopdf ^
    --css="pdf-style.css" ^
    --toc ^
    --toc-depth=3 ^
    --number-sections ^
    --highlight-style=github ^
    --metadata title="QA Engineer API Automation Guide" ^
    --metadata author="QA Engineering Team" ^
    --metadata date="%date%" ^
    -o "QA_ENGINEER_GUIDE.pdf"

if %errorlevel% equ 0 (
    echo.
    echo ✅ SUKSES! PDF berhasil dibuat: QA_ENGINEER_GUIDE.pdf
    echo.
    echo 📖 File PDF siap untuk:
    echo    - Dibagikan ke tim
    echo    - Dicetak untuk referensi
    echo    - Disimpan sebagai dokumentasi
    echo.
    
    REM Ask if user wants to open the PDF
    set /p openPdf="Buka PDF sekarang? (y/n): "
    if /i "%openPdf%"=="y" (
        start QA_ENGINEER_GUIDE.pdf
    )
) else (
    echo.
    echo ❌ ERROR: Gagal mengkonversi ke PDF
    echo.
    echo 💡 SOLUSI ALTERNATIF:
    echo 1. Buka QA_ENGINEER_GUIDE.md di VS Code
    echo 2. Install extension "Markdown PDF"
    echo 3. Ctrl+Shift+P → "Markdown PDF: Export (pdf)"
    echo.
    echo Atau gunakan online converter:
    echo - Upload QA_ENGINEER_GUIDE.md ke https://www.markdowntopdf.com/
    echo - Copy-paste content ke https://md2pdf.netlify.app/
)

echo.
echo ========================================
pause
