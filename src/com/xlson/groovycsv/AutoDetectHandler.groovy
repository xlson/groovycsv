package com.xlson.groovycsv

class AutoDetectHandler {

    List autoDetectSeparators = [",", ";", ":", "|"]
    
    List autoDetectQuoteChars = ['"', "'", "%"]
        
    String linesToInspect
    
    
    String autoDetectQuoteChar() {
        return mostFrequentChar(linesToInspect, autoDetectQuoteChars)
    }
    
    String autoDetectSeparator() {
        return mostFrequentChar(linesToInspect, autoDetectSeparators)
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
