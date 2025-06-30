# QUICK START GUIDE - API Automation Framework

## 🚀 Getting Started (5 Minutes)

### Step 1: Verify Setup
```bash
# Check Java
java -version

# Build project
./gradlew build

# Run tests
./gradlew test
```

### Step 2: Generate Report
```bash
# Create Allure report
./gradlew allureReport

# Open report in browser
./gradlew allureServe
```

### Step 3: Convert Documentation to PDF
```bash
# Run the conversion script
./convert-docs-to-pdf.bat
```

## 📋 Daily Commands for QA Engineer

### Testing
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests UserApiTest

# Run with detailed output
./gradlew test --info
```

### Reporting
```bash
# Generate Allure report
./gradlew allureReport

# Serve report (opens browser)
./gradlew allureServe
```

### Development
```bash
# Clean and rebuild
./gradlew clean build

# Check for compilation errors
./gradlew compileTestJava
```

## 📁 Key Files for QA Engineer

| File | Purpose | What to Modify |
|------|---------|----------------|
| `ApiConfig.java` | API endpoints | Add new endpoints |
| `BaseApiUtils.java` | REST Assured config | Modify timeouts, headers |
| `TestDataGenerator.java` | Test data creation | Add new data generators |
| `*ApiTest.java` | Test classes | Add new test methods |

## ✍️ Writing Your First Test

1. **Create test class** in `src/test/java/com/api/automation/tests/`
2. **Extend BaseApiUtils**
3. **Add Allure annotations**
4. **Follow pattern**:
   ```java
   @Test
   public void testAction_Scenario() {
       // Given
       // When  
       // Then
   }
   ```

## 🔧 Troubleshooting

| Problem | Solution |
|---------|----------|
| Tests fail | Check API availability |
| Build fails | Run `./gradlew clean build` |
| Report not loading | Clear browser cache, regenerate report |
| Permission denied | Run as administrator |

## 📚 Documentation Files

- `README.md` - Project overview
- `QA_ENGINEER_GUIDE.md` - Detailed guide
- `QA_ENGINEER_SUMMARY.md` - Quick reference
- `QA_ENGINEER_COMPLETE_GUIDE.md` - Comprehensive guide

## 🎯 Success Criteria

✅ All tests pass  
✅ Allure report generates successfully  
✅ Can write new test cases  
✅ Can debug failing tests  
✅ Can generate PDF documentation  

---

**Ready to start testing! 🚀**
