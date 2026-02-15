package business;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import dal.TFIDFCalculator;

/**
 * Test Class: TFIDFCalculatorTest
 * Purpose: Test TF-IDF calculation algorithm with positive and negative paths
 */
public class TFIDFCalculatorTest extends TestCase {
    
    private TFIDFCalculator calculator;
    
    public TFIDFCalculatorTest(String name) {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(TFIDFCalculatorTest.class);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        calculator = new TFIDFCalculator();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        calculator = null;
    }
    
    /**
     * Test TF-IDF Positive Path: Known document should return positive score
     */
    public void testTFIDF_PositivePath_KnownDocument_ReturnsPositiveScore() {
        // Arrange
        calculator.addDocumentToCorpus("the cat sat on the mat");
        calculator.addDocumentToCorpus("the dog ran in the park");
        calculator.addDocumentToCorpus("cats and dogs are pets");
        
        String testDocument = "the cat";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(testDocument);
        
        // Assert
        assertTrue("TF-IDF score should be positive for known document", tfidfScore > 0);
        assertFalse("TF-IDF score should not be NaN", Double.isNaN(tfidfScore));
        assertFalse("TF-IDF score should not be infinite", Double.isInfinite(tfidfScore));
    }
    
    /**
     * Test TF-IDF Negative Path: Empty document should handle gracefully
     */
    public void testTFIDF_NegativePath_EmptyDocument_ReturnsZero() {
        // Arrange
        calculator.addDocumentToCorpus("some content");
        String emptyDocument = "";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(emptyDocument);
        
        // Assert
        assertEquals("Empty document should return zero TF-IDF score", 0.0, tfidfScore, 0.001);
        assertFalse("Empty document should not return NaN", Double.isNaN(tfidfScore));
    }
    
    /**
     * Test TF-IDF Negative Path: Null document should handle gracefully
     */
    public void testTFIDF_NegativePath_NullDocument_HandlesGracefully() {
        // Arrange
        calculator.addDocumentToCorpus("some content");
        String nullDocument = null;
        
        // Act & Assert
        try {
            double tfidfScore = calculator.calculateDocumentTfIdf(nullDocument);
            // If no exception, score should be valid
            assertFalse("Null document should not produce NaN", Double.isNaN(tfidfScore));
        } catch (Exception e) {
            // Exception is also acceptable for null input
            assertTrue("Exception handling for null input is acceptable", true);
        }
    }
    
    /**
     * Test TF-IDF with single word document
     */
    public void testTFIDF_SingleWordDocument_ReturnsValidScore() {
        // Arrange
        calculator.addDocumentToCorpus("word");
        calculator.addDocumentToCorpus("another");
        
        String singleWord = "word";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(singleWord);
        
        // Assert
        assertTrue("Single word should have positive TF-IDF score", tfidfScore > 0);
        assertFalse("Single word should not produce NaN", Double.isNaN(tfidfScore));
    }
    
    /**
     * Test TF-IDF with special characters
     */
    public void testTFIDF_SpecialCharacters_HandlesGracefully() {
        // Arrange
        calculator.addDocumentToCorpus("normal text content");
        String specialDocument = "@#$%^&*()";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(specialDocument);
        
        // Assert
        assertFalse("Special characters should not cause NaN", Double.isNaN(tfidfScore));
        assertTrue("Special characters should return valid score", tfidfScore >= 0);
    }
    
    /**
     * Test TF-IDF with repeated words
     */
    public void testTFIDF_RepeatedWords_CalculatesCorrectly() {
        // Arrange
        calculator.addDocumentToCorpus("cat dog bird");
        calculator.addDocumentToCorpus("dog bird fish");
        
        String repeatedDocument = "cat cat cat";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(repeatedDocument);
        
        // Assert
        assertTrue("Repeated words should have positive TF-IDF score", tfidfScore > 0);
        assertFalse("Repeated words should not produce NaN", Double.isNaN(tfidfScore));
    }
    
    /**
     * Test TF-IDF with empty corpus
     */
    public void testTFIDF_EmptyCorpus_ReturnsZero() {
        // Arrange - Empty corpus (no documents added)
        String testDocument = "test document";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(testDocument);
        
        // Assert
        assertEquals("Empty corpus should return zero TF-IDF", 0.0, tfidfScore, 0.001);
    }
    
    /**
     * Test TF-IDF with large corpus performance
     */
    public void testTFIDF_LargeCorpus_CompletesReasonably() {
        // Arrange
        long startTime = System.currentTimeMillis();
        
        // Add moderate-sized corpus
        for (int i = 0; i < 100; i++) {
            calculator.addDocumentToCorpus("Document " + i + " with various content words");
        }
        
        String testDocument = "test document for performance evaluation";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(testDocument);
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Assert
        assertTrue("Large corpus should complete in reasonable time (< 5 seconds)", 
                   executionTime < 5000);
        assertFalse("Large corpus TF-IDF should not be NaN", Double.isNaN(tfidfScore));
        assertTrue("Large corpus TF-IDF should be non-negative", tfidfScore >= 0);
    }
    
    /**
     * Test TF-IDF consistency - same input should produce same output
     */
    public void testTFIDF_Consistency_SameInputSameOutput() {
        // Arrange
        calculator.addDocumentToCorpus("consistent test document");
        calculator.addDocumentToCorpus("another test document");
        
        String testDocument = "test";
        
        // Act
        double score1 = calculator.calculateDocumentTfIdf(testDocument);
        double score2 = calculator.calculateDocumentTfIdf(testDocument);
        
        // Assert
        assertEquals("Same input should produce same TF-IDF score", score1, score2, 0.00001);
    }
    
    /**
     * Test TF-IDF with Arabic text (if Arabic processing is available)
     */
    public void testTFIDF_ArabicText_HandlesCorrectly() {
        // Arrange
        calculator.addDocumentToCorpus("بسم الله الرحمن الرحيم");
        calculator.addDocumentToCorpus("الحمد لله رب العالمين");
        
        String arabicDocument = "الله الرحمن";
        
        // Act
        double tfidfScore = calculator.calculateDocumentTfIdf(arabicDocument);
        
        // Assert
        assertFalse("Arabic text should not produce NaN", Double.isNaN(tfidfScore));
        assertTrue("Arabic text should produce valid score", tfidfScore >= 0);
    }
}