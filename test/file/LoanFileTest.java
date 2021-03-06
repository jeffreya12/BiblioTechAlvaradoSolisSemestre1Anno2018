/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import domain.Loan;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import resources.DefaultValues;

/**
 *
 * @author jefal
 */
public class LoanFileTest {
    
    private static LoanFile loanFile;
    
    public LoanFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        File fileLoan = new File(DefaultValues.LOAN_FILE_PATH);
        loanFile = new LoanFile(fileLoan);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testGetAllRecords() throws Exception {
        List<Loan> loans = loanFile.getAllRecords();
        
        for(Loan currentLoan : loans){
            System.out.println(currentLoan.toString());
        }
    }
    
}
