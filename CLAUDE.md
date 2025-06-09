# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Common Commands

### Building and Testing
- **Build:** `./gradlew jar` - Creates JAR in `./build/libs`
- **Test:** `./gradlew test` - Runs all Spock tests
- **Download Dependencies:** `./gradlew downloadDeps` - Downloads dependencies to `lib/` folder
- **Generate Documentation:** `./gradlew groovydoc` - Generates Groovydoc

### Development
- Tests use Spock framework and are located in `test/` directory
- Main source code is in `src/com/xlson/groovycsv/`
- Use `./gradlew test` to run a single test class: `./gradlew test --tests "com.xlson.groovycsv.CsvParserSpec"`

## Architecture

GroovyCSV is a Groovy library for parsing CSV files with column name access. Core components:

### Main Classes
- **CsvParser** (`src/com/xlson/groovycsv/CsvParser.groovy`) - Main entry point with static `parseCsv()` methods and instance `parse()` methods
- **CsvIterator** (`src/com/xlson/groovycsv/CsvIterator.groovy`) - Iterator implementation that provides column access by name or position
- **AutoDetectHandler** (`src/com/xlson/groovycsv/AutoDetectHandler.groovy`) - Handles automatic detection of separator and quote characters
- **PropertyMapper** (`src/com/xlson/groovycsv/PropertyMapper.groovy`) - Maps CSV row data to properties

### Key Features
- Value access by header name (e.g., `line.Name`) or position
- Support for custom separators, quote chars, and escape chars
- Auto-detection of CSV format parameters
- Support for reading CSV without headers using `columnNames` parameter
- Skipping initial lines with `skipLines` parameter
- Built on OpenCSV 4.x for underlying CSV parsing

### Testing
- Uses Spock framework for BDD-style testing
- Test files mirror source structure in `test/` directory
- Tests cover parser functionality, iterator behavior, auto-detection, and configuration options

### Dependencies
- OpenCSV 4.0 for CSV parsing
- Groovy 1.8.x+ (compile-only dependency)
- Spock framework for testing