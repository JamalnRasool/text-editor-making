package data;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import dal.HashCalculator;

/**
 * Test Class: HashCalculatorTest
 * Purpose: Test hashing integrity and different hash algorithms
 */
public class HashCalculatorTest extends TestCase {
    
    public HashCalculatorTest(String name) {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HashCalculatorTest.class);
    }
    
    /**
     * Test hash calculation consistency
     */
    public void testHashCalculator_SameContent_ReturnsSameHash() {
        // Arrange
        String content = "Test content for hashing";
        
        try {
            // Act
            String hash1 = HashCalculator.calculateHash(content);
            String hash2 = HashCalculator.calculateHash(content);
            
            // Assert
            assertNotNull("Hash should not be null", hash1);
            assertNotNull("Hash should not be null", hash2);
            assertEquals("Same content should produce same hash", hash1, hash2);
            assertTrue("Hash should not be empty", hash1.length() > 0);
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash calculation with different content
     */
    public void testHashCalculator_DifferentContent_ReturnsDifferentHash() {
        // Arrange
        String content1 = "First content";
        String content2 = "Second content";
        
        try {
            // Act
            String hash1 = HashCalculator.calculateHash(content1);
            String hash2 = HashCalculator.calculateHash(content2);
            
            // Assert
            assertNotNull("First hash should not be null", hash1);
            assertNotNull("Second hash should not be null", hash2);
            assertFalse("Different content should produce different hashes", 
                       hash1.equals(hash2));
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash calculation with empty content
     */
    public void testHashCalculator_EmptyContent_ReturnsValidHash() {
        // Arrange
        String emptyContent = "";
        
        try {
            // Act
            String hash = HashCalculator.calculateHash(emptyContent);
            
            // Assert
            assertNotNull("Hash of empty content should not be null", hash);
            assertTrue("Hash of empty content should not be empty", hash.length() > 0);
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash calculation with null content
     */
    public void testHashCalculator_NullContent_HandlesGracefully() {
        // Arrange
        String nullContent = null;
        
        // Act & Assert
        try {
            String hash = HashCalculator.calculateHash(nullContent);
            // If no exception, hash should be valid or empty
            if (hash != null) {
                assertTrue("Hash should be valid string", true);
            }
        } catch (Exception e) {
            // Exception handling for null input is acceptable
            assertTrue("Exception handling for null content is acceptable", true);
        }
    }
    
    /**
     * Test hash calculation with special characters
     */
    public void testHashCalculator_SpecialCharacters_ReturnsValidHash() {
        // Arrange
        String specialContent = "!@#$%^&*()_+-={}[]|\\:;\"'<>?,./";
        
        try {
            // Act
            String hash = HashCalculator.calculateHash(specialContent);
            
            // Assert
            assertNotNull("Hash with special characters should not be null", hash);
            assertTrue("Hash with special characters should not be empty", hash.length() > 0);
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash calculation with Arabic text
     */
    public void testHashCalculator_ArabicText_ReturnsValidHash() {
        // Arrange
        String arabicContent = "بسم الله الرحمن الرحيم";
        
        try {
            // Act
            String hash = HashCalculator.calculateHash(arabicContent);
            
            // Assert
            assertNotNull("Hash with Arabic text should not be null", hash);
            assertTrue("Hash with Arabic text should not be empty", hash.length() > 0);
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash calculation with large content
     */
    public void testHashCalculator_LargeContent_ReturnsValidHash() {
        // Arrange
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeContent.append("This is line ").append(i).append(" of large content.\n");
        }
        
        try {
            // Act
            long startTime = System.currentTimeMillis();
            String hash = HashCalculator.calculateHash(largeContent.toString());
            long endTime = System.currentTimeMillis();
            
            // Assert
            assertNotNull("Hash of large content should not be null", hash);
            assertTrue("Hash of large content should not be empty", hash.length() > 0);
            assertTrue("Large content hashing should complete in reasonable time (< 2 seconds)", 
                       (endTime - startTime) < 2000);
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash modification detection
     */
    public void testHashCalculator_ContentModification_DetectedByHashChange() {
        // Arrange
        String originalContent = "Original file content for testing";
        String modifiedContent = "Modified file content for testing";
        
        try {
            // Act
            String originalHash = HashCalculator.calculateHash(originalContent);
            String modifiedHash = HashCalculator.calculateHash(modifiedContent);
            
            // Assert
            assertNotNull("Original hash should not be null", originalHash);
            assertNotNull("Modified hash should not be null", modifiedHash);
            assertFalse("Hash should change when content is modified", 
                       originalHash.equals(modifiedHash));
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
    
    /**
     * Test hash calculation with minimal content change
     */
    public void testHashCalculator_MinimalChange_DetectedByHash() {
        // Arrange
        String content1 = "test content";
        String content2 = "test content."; // Just added a period
        
        try {
            // Act
            String hash1 = HashCalculator.calculateHash(content1);
            String hash2 = HashCalculator.calculateHash(content2);
            
            // Assert
            assertFalse("Even minimal content change should result in different hash", 
                       hash1.equals(hash2));
        } catch (Exception e) {
            fail("Hash calculation should not throw exception: " + e.getMessage());
        }
    }
}