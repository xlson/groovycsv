package com.xlson.groovycsv

class AutoDetectHandler {

    def autoDetectSeparators = [",", ";", ":", "|"]
    
    def autoDetectQuoteChars = ['"', "'", "%"]
        
    String linesToInspect
    
    
    String autoDetectQuoteChar() {
        return mostFrequentChar(linesToInspect, autoDetectQuoteChars)
    }
    
    String autoDetectSeparator() {
        return mostFrequentChar(linesToInspect, autoDetectSeparators)
    }
    
    /**
    * Run autodetection for separator.
    * @param text  The full CSV as a String.
    * @return the detected character.
    */
   private autoDetectSeparator(String text) {
       def firstLines = getFirstLines(text)
       return mostFrequentChar(firstLines, autoDetectedSeparators)
   }

   /**
   * Run autodetection for quote character.
   * @param text The full CSV as a String.
   * @return the detected character.
   */
   private autoDetectQuoteChar(String text) {
       def firstLines = getFirstLines(text)
       return mostFrequentChar(firstLines, autoDetectedQuoteChars)
   }
   
   /**
    * Find the most frequent character in a string among a list of characters.
    * Falls back on the first character in the list if no character is found.
    *
    * @param sequence      The string to search.
    * @param characters    The list of characters to search.
    * @return  The most frequent character.
    */
   private mostFrequentChar(String sequence, List characters) {
       characters.max{ sequence.count(it) }
   }

    
}
